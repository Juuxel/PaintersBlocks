/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import juuxel.paintersblocks.block.entity.PaintersBlockEntity;
import juuxel.paintersblocks.block.entity.PbBlockEntities;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwatchItem extends Item implements PbDyeableItem {
    public SwatchItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        @Nullable PaintersBlockEntity entity = context.getWorld().getBlockEntity(context.getBlockPos(), PbBlockEntities.PAINTERS_BLOCK).orElse(null);

        if (entity != null) {
            if (context.getWorld().isClient()) return ActionResult.SUCCESS;
            ItemStack stack = context.getStack();

            if (hasColor(stack)) {
                entity.setColor(getColor(stack));
                entity.sync();
                stack.decrement(1);
            } else {
                setColor(stack, entity.getColor());
            }

            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendColorTooltip(stack, tooltip);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (isIn(group)) {
            PbDyeableItem.appendStacks(this, stacks);
        }
    }

    public int getColor(ItemStack stack, int tintIndex) {
        if (hasColor(stack) && tintIndex == 1) {
            return getColor(stack);
        }

        return -1;
    }
}
