/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import com.google.common.collect.ImmutableList;
import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.block.PbBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

import java.util.List;

public final class PbItems {
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(PaintersBlocks.id("group"), () -> {
        ItemStack stack = new ItemStack(PbBlocks.PAINTERS_BRICKS);
        PbDyeableItem.setColor(stack, DyeColor.LIME);
        return stack;
    });

    public static final Item SWATCH = register("swatch", new SwatchItem(new Item.Settings().group(GROUP)));

    public static final List<Item> ALL_DYEABLE_ITEMS = Util.make(ImmutableList.<Item>builder(), builder -> {
        for (Block block : PbBlocks.ALL_BLOCKS) {
            builder.add(block.asItem());
        }

        builder.add(SWATCH);
    }).build();

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, PaintersBlocks.id(id), item);
    }

    public static void init() {
        for (Item item : ALL_DYEABLE_ITEMS) {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(item, CauldronBehavior.CLEAN_DYEABLE_ITEM);
        }
    }
}
