/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import juuxel.paintersblocks.block.entity.PaintableBlockEntity;
import juuxel.paintersblocks.block.entity.PbBlockEntities;
import juuxel.paintersblocks.item.PbDyeableItem;
import juuxel.paintersblocks.item.PbItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class PaintableBlock extends BlockWithEntity {
    // can't initialise eagerly since PbBlocks isn't loaded yet
    private static final Supplier<Map<Block, Block>> REGULAR_TO_GLOWING = Suppliers.memoize(
        () -> ImmutableMap.<Block, Block>builder()
            .put(PbBlocks.CHISELED_PAINTERS_STONE, PbBlocks.GLOWING_CHISELED_PAINTERS_STONE)
            .put(PbBlocks.PAINTERS_BRICKS, PbBlocks.GLOWING_PAINTERS_BRICKS)
            .put(PbBlocks.PAINTERS_STONE_PILLAR, PbBlocks.GLOWING_PAINTERS_STONE_PILLAR)
            .put(PbBlocks.PAINTERS_TILES, PbBlocks.GLOWING_PAINTERS_TILES)
            .build()
    );

    public PaintableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (REGULAR_TO_GLOWING.get().containsKey(this)) {
            ItemStack holding = player.getStackInHand(hand);

            if (holding.isIn(PbItemTags.GLOW_INGREDIENTS)) {
                if (world.isClient()) {
                    return ActionResult.SUCCESS;
                } else {
                    @Nullable BlockEntity oldBe = world.getBlockEntity(pos);
                    world.setBlockState(pos, REGULAR_TO_GLOWING.get().get(this).getStateWithProperties(state));

                    if (!player.getAbilities().creativeMode) {
                        holding.decrement(1);
                    }

                    if (oldBe != null) {
                        @Nullable BlockEntity newBe = world.getBlockEntity(pos);
                        if (newBe != null) newBe.readNbt(oldBe.writeNbt(new NbtCompound()));
                    }

                    return ActionResult.CONSUME;
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PaintableBlockEntity(pos, state);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        var stack = new ItemStack(this);
        world.getBlockEntity(pos, PbBlockEntities.PAINTERS_BLOCK)
            .ifPresent(entity -> ((DyeableItem) stack.getItem()).setColor(stack, entity.getColor()));
        return stack;
    }

    @Override
    public void addStacksForDisplay(ItemGroup group, DefaultedList<ItemStack> stacks) {
        PbDyeableItem.appendStacks(this, stacks);
    }
}
