/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.part;

import alexiil.mc.lib.multipart.api.AbstractPart;
import alexiil.mc.lib.multipart.api.MultipartHolder;
import alexiil.mc.lib.multipart.api.PartDefinition;
import alexiil.mc.lib.multipart.api.render.PartModelKey;
import alexiil.mc.lib.net.IMsgReadCtx;
import alexiil.mc.lib.net.IMsgWriteCtx;
import alexiil.mc.lib.net.InvalidInputDataException;
import alexiil.mc.lib.net.NetByteBuf;
import juuxel.paintersblocks.item.PbItems;
import juuxel.paintersblocks.util.Colors;
import juuxel.paintersblocks.util.NbtKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

// TODO: Particles aren't colored correctly.
// TODO: No sprinting particles: https://github.com/AlexIIL/LibMultiPart/issues/30
public class PaintableSlabPart extends AbstractPart {
    private static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 8, 16);
    private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0, 8, 0, 16, 16, 16);

    private final BlockHalf half;
    private final VoxelShape shape;
    private int color;
    private PaintableSlabModelKey modelKey;

    public PaintableSlabPart(PartDefinition definition, MultipartHolder holder, BlockHalf half, @Nullable Integer color) {
        super(definition, holder);
        this.half = half;
        this.shape = half == BlockHalf.BOTTOM ? BOTTOM_SHAPE : TOP_SHAPE;
        this.color = color != null ? color : Colors.STONE_DEFAULT_COLOR;
        rebuildModelKey();
    }

    PaintableSlabPart(PartDefinition definition, MultipartHolder holder, NbtCompound nbt) {
        super(definition, holder);
        this.half = nbt.getBoolean(NbtKeys.IS_TOP) ? BlockHalf.TOP : BlockHalf.BOTTOM;
        this.shape = half == BlockHalf.BOTTOM ? BOTTOM_SHAPE : TOP_SHAPE;
        this.color = nbt.contains(NbtKeys.COLOR, NbtElement.NUMBER_TYPE) ? nbt.getInt(NbtKeys.COLOR) : Colors.STONE_DEFAULT_COLOR;
        rebuildModelKey();
    }

    PaintableSlabPart(PartDefinition definition, MultipartHolder holder, NetByteBuf buffer, IMsgReadCtx ctx) {
        super(definition, holder);
        this.half = buffer.readBoolean() ? BlockHalf.TOP : BlockHalf.BOTTOM;
        this.shape = half == BlockHalf.BOTTOM ? BOTTOM_SHAPE : TOP_SHAPE;
        this.color = buffer.readInt();
        rebuildModelKey();
    }

    private void rebuildModelKey() {
        modelKey = new PaintableSlabModelKey(definition, half, color);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        rebuildModelKey();
    }

    public void setColorAndSync(int color) {
        setColor(color);

        if (!holder.getContainer().getMultipartWorld().isClient()) {
            holder.getContainer().sendNetworkUpdate(this, NET_RENDER_DATA);
            holder.getContainer().redrawIfChanged();
            holder.getContainer().getMultipartBlockEntity().markDirty();
        }
    }

    @Override
    public ActionResult onUse(PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);

        // TODO: Remove this disgusting duplication
        if (stack.isOf(PbItems.SWATCH)) {
            if (player.world.isClient()) return ActionResult.SUCCESS;
            DyeableItem item = (DyeableItem) stack.getItem();

            if (item.hasColor(stack)) {
                int color = item.getColor(stack);

                if (color != getColor()) {
                    setColorAndSync(item.getColor(stack));
                    holder.getContainer().getMultipartBlockEntity().markDirty();

                    if (!player.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                }
            } else {
                if (stack.getCount() == 1) {
                    item.setColor(stack, getColor());
                } else {
                    stack.decrement(1);
                    ItemStack dyed = new ItemStack(stack.getItem());
                    item.setColor(dyed, getColor());
                    player.getInventory().offerOrDrop(dyed);
                }

                player.sendMessage(new TranslatableText("item.dyed").formatted(Formatting.ITALIC), true);
            }

            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }

    @Override
    public VoxelShape getShape() {
        return shape;
    }

    @Nullable
    @Override
    public PartModelKey getModelKey() {
        return modelKey;
    }

    @Override
    protected BlockState getClosestBlockState() {
        return PbParts.PARTS_TO_BLOCKS.get().get(definition).getDefaultState();
    }

    @Override
    public void addDrops(ItemDropTarget target, LootContext context) {
        Item item = PbItems.PART_ITEMS_BY_DEFINITION.get(definition);
        ItemStack stack = new ItemStack(item);
        ((DyeableItem) item).setColor(stack, color);
        target.drop(stack);
    }

    @Override
    public NbtCompound toTag() {
        var nbt = super.toTag();
        nbt.putInt(NbtKeys.COLOR, color);
        nbt.putBoolean(NbtKeys.IS_TOP, half == BlockHalf.TOP);
        return nbt;
    }

    @Override
    public void writeCreationData(NetByteBuf buffer, IMsgWriteCtx ctx) {
        buffer.writeBoolean(half == BlockHalf.TOP);
        buffer.writeInt(color);
    }

    @Override
    public void readRenderData(NetByteBuf buffer, IMsgReadCtx ctx) throws InvalidInputDataException {
        setColor(buffer.readInt());
    }

    @Override
    public void writeRenderData(NetByteBuf buffer, IMsgWriteCtx ctx) {
        buffer.writeInt(color);
    }
}
