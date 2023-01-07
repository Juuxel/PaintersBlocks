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
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public interface PbDyeableItem extends DyeableItem {
    default int getDefaultColor() {
        return Colors.GENERIC_DEFAULT_COLOR;
    }

    default String getColorParentNbtKey() {
        return DISPLAY_KEY;
    }

    default String getColorNbtKey() {
        return COLOR_KEY;
    }

    @Override
    default boolean hasColor(ItemStack stack) {
        @Nullable NbtCompound nbt = stack.getSubNbt(getColorParentNbtKey());
        return nbt != null && nbt.contains(getColorNbtKey(), NbtElement.NUMBER_TYPE);
    }

    @Override
    default int getColor(ItemStack stack) {
        @Nullable NbtCompound nbt = stack.getSubNbt(getColorParentNbtKey());
        return nbt != null && nbt.contains(getColorNbtKey(), NbtElement.NUMBER_TYPE) ? nbt.getInt(getColorNbtKey()) : getDefaultColor();
    }

    @Override
    default void removeColor(ItemStack stack) {
        @Nullable NbtCompound nbt = stack.getSubNbt(getColorParentNbtKey());

        if (nbt != null && nbt.contains(getColorNbtKey())) {
            nbt.remove(getColorNbtKey());

            // Remove empty parent NBT
            if (nbt.isEmpty()) {
                stack.getNbt().remove(getColorParentNbtKey());

                // Remove empty root NBT
                if (stack.getNbt().isEmpty()) {
                    stack.setNbt(null);
                }
            }
        }
    }

    @Override
    default void setColor(ItemStack stack, int color) {
        stack.getOrCreateSubNbt(getColorParentNbtKey()).putInt(getColorNbtKey(), color);
    }

    default void appendColorTooltip(ItemStack stack, List<Text> tooltip) {
        if (hasColor(stack)) {
            int color = getColor(stack);
            @Nullable Item dye = PaintableItem.DYES_BY_RGB.get(color);
            String colorStr = Integer.toHexString(color);
            MutableText text = dye != null
                ? Text.translatable("tooltip.painters_blocks.dye_color", colorStr, dye.getName())
                : Text.translatable("tooltip.painters_blocks.color", colorStr);
            tooltip.add(text.styled(style -> style.withColor(color)));
        } else {
            tooltip.add(Text.translatable("tooltip.painters_blocks.undyed").styled(style -> style.withItalic(true).withColor(Formatting.DARK_GRAY)));
        }
    }

    static void setColor(ItemStack stack, DyeColor color) {
        ((DyeableItem) stack.getItem()).setColor(stack, Colors.DYE_COLOR_RGB_VALUES.getInt(color));
    }

    static void copyColor(ItemStack from, ItemStack to) {
        ((DyeableItem) to.getItem()).setColor(to, ((DyeableItem) from.getItem()).getColor(from));
    }

    static void appendStacks(ItemConvertible item, Consumer<ItemStack> stacks) {
        stacks.accept(new ItemStack(item));

        for (DyeColor color : DyeColor.values()) {
            ItemStack stack = new ItemStack(item);
            setColor(stack, color);
            stacks.accept(stack);
        }
    }
}
