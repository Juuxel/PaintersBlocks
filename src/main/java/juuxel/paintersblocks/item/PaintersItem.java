/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import juuxel.paintersblocks.util.NbtKeys;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaintersItem extends BlockItem implements DyeableItem {
    public static final int DEFAULT_COLOR = 0x8F8F8F;
    private static final Int2ObjectMap<Item> DYES_BY_RGB = Util.make(new Int2ObjectOpenHashMap<>(), map -> {
        for (DyeColor color : DyeColor.values()) {
            float[] comps = color.getColorComponents();
            int r = (int) (comps[0] * 255f);
            int g = (int) (comps[1] * 255f);
            int b = (int) (comps[2] * 255f);
            int c = (r << 16) | (g << 8) | b;

            map.put(c, DyeItem.byColor(color));
        }
    });

    public PaintersItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (hasColor(stack)) {
            int color = getColor(stack);
            @Nullable Item dye = DYES_BY_RGB.get(color);
            String colorStr = Integer.toHexString(color);
            MutableText text = dye != null
                ? new TranslatableText("tooltip.painters_blocks.dye_color", colorStr, dye.getName())
                : new TranslatableText("tooltip.painters_blocks.color", colorStr);
            tooltip.add(text.styled(style -> style.withColor(color)));
        } else {
            tooltip.add(new TranslatableText("tooltip.painters_blocks.undyed").styled(style -> style.withItalic(true).withColor(Formatting.DARK_GRAY)));
        }
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        @Nullable NbtCompound nbt = stack.getSubTag(BLOCK_ENTITY_TAG_KEY);
        return nbt != null && nbt.contains(NbtKeys.COLOR, NbtElement.NUMBER_TYPE);
    }

    @Override
    public int getColor(ItemStack stack) {
        @Nullable NbtCompound nbt = stack.getSubTag(BLOCK_ENTITY_TAG_KEY);
        return nbt != null && nbt.contains(NbtKeys.COLOR, NbtElement.NUMBER_TYPE) ? nbt.getInt(NbtKeys.COLOR) : DEFAULT_COLOR;
    }

    @Override
    public void removeColor(ItemStack stack) {
        @Nullable NbtCompound nbt = stack.getSubTag(BLOCK_ENTITY_TAG_KEY);

        if (nbt != null) {
            nbt.remove(NbtKeys.COLOR);
        }
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateSubTag(BLOCK_ENTITY_TAG_KEY).putInt(NbtKeys.COLOR, color);
    }
}
