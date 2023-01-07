/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import com.google.common.collect.ImmutableList;
import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.block.PbBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;

import java.util.List;

public final class PbItems {
    public static final Item SWATCH = register("swatch", new SwatchItem(new Item.Settings()));

    public static final List<Item> ALL_DYEABLE_ITEMS = Util.make(ImmutableList.<Item>builder(), builder -> {
        for (ItemConvertible item : PbBlocks.ALL_BLOCKS) {
            builder.add(item.asItem());
        }

        builder.add(SWATCH);
    }).build();

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(PaintersBlocks.id("group"))
        .entries((enabledFeatures, entries, operatorEnabled) -> {
            for (Item item : ALL_DYEABLE_ITEMS) {
                PbDyeableItem.appendStacks(item, entries::add);
            }
        })
        .icon(() -> {
            ItemStack stack = new ItemStack(PbBlocks.PAINTERS_BRICKS);
            PbDyeableItem.setColor(stack, DyeColor.LIME);
            return stack;
        })
        .build();

    public static <I extends Item> I register(String id, I item) {
        return Registry.register(Registries.ITEM, PaintersBlocks.id(id), item);
    }

    public static void init() {
        for (Item item : ALL_DYEABLE_ITEMS) {
            addCauldronBehavior(item);
        }
    }

    public static void addCauldronBehavior(Item item) {
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(item, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }
}
