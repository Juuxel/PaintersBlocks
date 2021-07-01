/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import juuxel.paintersblocks.util.Colors;
import juuxel.paintersblocks.util.NbtKeys;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaintableItem extends BlockItem implements PbDyeableItem {
    public static final int DEFAULT_COLOR = 0x8F8F8F;
    static final Int2ObjectMap<Item> DYES_BY_RGB = Util.make(new Int2ObjectOpenHashMap<>(), map -> {
        for (DyeColor color : DyeColor.values()) {
            map.put(Colors.DYE_COLOR_RGB_VALUES.getInt(color), DyeItem.byColor(color));
        }
    });

    public PaintableItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendColorTooltip(stack, tooltip);
    }

    @Override
    public int getDefaultColor() {
        return DEFAULT_COLOR;
    }

    @Override
    public String getColorParentNbtKey() {
        return BLOCK_ENTITY_TAG_KEY;
    }

    @Override
    public String getColorNbtKey() {
        return NbtKeys.COLOR;
    }
}
