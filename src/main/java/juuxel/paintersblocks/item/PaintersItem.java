/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import juuxel.paintersblocks.util.NbtKeys;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaintersItem extends BlockItem implements DyeableItem {
    public static final int DEFAULT_COLOR = 0x8F8F8F;

    public PaintersItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (hasColor(stack)) {
            tooltip.add(new TranslatableText("tooltip.painters_blocks.color", Integer.toHexString(getColor(stack))).formatted(Formatting.DARK_PURPLE));
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
