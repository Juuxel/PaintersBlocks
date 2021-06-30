/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import juuxel.paintersblocks.util.Colors;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PbDyeableItem extends DyeableItem {
    int DEFAULT_COLOR = 0xFFFFFF;

    default int getDefaultColor() {
        return DEFAULT_COLOR;
    }

    @Override
    default int getColor(ItemStack stack) {
        @Nullable NbtCompound nbt = stack.getSubTag(DISPLAY_KEY);
        return nbt != null && nbt.contains(COLOR_KEY, NbtElement.NUMBER_TYPE) ? nbt.getInt(COLOR_KEY) : getDefaultColor();
    }

    default void appendColorTooltip(ItemStack stack, List<Text> tooltip) {
        if (hasColor(stack)) {
            int color = getColor(stack);
            @Nullable Item dye = PaintersItem.DYES_BY_RGB.get(color);
            String colorStr = Integer.toHexString(color);
            MutableText text = dye != null
                ? new TranslatableText("tooltip.painters_blocks.dye_color", colorStr, dye.getName())
                : new TranslatableText("tooltip.painters_blocks.color", colorStr);
            tooltip.add(text.styled(style -> style.withColor(color)));
        } else {
            tooltip.add(new TranslatableText("tooltip.painters_blocks.undyed").styled(style -> style.withItalic(true).withColor(Formatting.DARK_GRAY)));
        }
    }

    static void setColor(ItemStack stack, DyeColor color) {
        ((DyeableItem) stack.getItem()).setColor(stack, Colors.DYE_COLOR_RGB_VALUES.getInt(color));
    }

    static void appendStacks(ItemConvertible item, DefaultedList<ItemStack> stacks) {
        stacks.add(new ItemStack(item));

        for (DyeColor color : DyeColor.values()) {
            ItemStack stack = new ItemStack(item);
            setColor(stack, color);
            stacks.add(stack);
        }
    }
}
