/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import alexiil.mc.lib.multipart.api.MultipartContainer;
import alexiil.mc.lib.multipart.api.MultipartUtil;
import alexiil.mc.lib.multipart.api.PartDefinition;
import juuxel.paintersblocks.part.PaintableSlabPart;
import juuxel.paintersblocks.part.PbParts;
import juuxel.paintersblocks.util.Colors;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaintableSlabItem extends PartItem implements PbDyeableItem {
    public PaintableSlabItem(Settings settings, PartDefinition definition) {
        super(settings, definition);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        Vec3d fractPos = context.getHitPos().subtract(pos.getX(), pos.getY(), pos.getZ());
        double y = fractPos.y;

        if (MathHelper.approximatelyEquals(y, 0)) {
            y = 1;
        } else if (MathHelper.approximatelyEquals(y, 1)) {
            y = 0;
        }

        BlockHalf half = switch (context.getSide()) {
            case DOWN -> y <= 0.5f ? BlockHalf.BOTTOM : BlockHalf.TOP;
            default -> y >= 0.5f ? BlockHalf.TOP : BlockHalf.BOTTOM;
        };

        double axis = fractPos.getComponentAlongAxis(context.getSide().getAxis());
        boolean tryInSelf = !(MathHelper.approximatelyEquals(axis, 0) || MathHelper.approximatelyEquals(axis, 1));
        BlockPos[] positions = tryInSelf ? new BlockPos[] { pos, pos.offset(context.getSide()) } : new BlockPos[] { pos.offset(context.getSide()) };

        MultipartContainer.@Nullable PartOffer offer = null;

        for (BlockPos p : positions) {
            offer = MultipartUtil.offerNewPart(
                context.getWorld(), p,
                holder -> new PaintableSlabPart(definition, holder, half, getColor(context.getStack()))
            );

            if (offer != null) break;
        }

        if (offer != null) {
            BlockSoundGroup soundGroup = PbParts.PARTS_TO_BLOCKS.get().get(definition).getDefaultState().getSoundGroup();
            context.getWorld().playSound(context.getPlayer(), pos, soundGroup.getPlaceSound(), SoundCategory.BLOCKS, (soundGroup.getVolume() + 1f) / 2f, soundGroup.getPitch() * 0.8f);

            if (!context.getWorld().isClient()) {
                context.getStack().decrement(1);
                offer.apply();
                return ActionResult.CONSUME;
            } else {
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendColorTooltip(stack, tooltip);
    }

    @Override
    public int getDefaultColor() {
        return Colors.STONE_DEFAULT_COLOR;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (isIn(group)) {
            PbDyeableItem.appendStacks(this, stacks);
        }
    }
}
