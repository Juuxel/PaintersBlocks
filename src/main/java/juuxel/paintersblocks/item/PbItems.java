/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import alexiil.mc.lib.multipart.api.PartDefinition;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.block.PbBlocks;
import juuxel.paintersblocks.part.PbParts;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
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

    public static Item.Settings settings() {
        return new Item.Settings().group(GROUP);
    }

    public static final Item SWATCH = register("swatch", new SwatchItem(settings()));
    public static final PartItem PAINTERS_STONE_SLAB = register("painters_stone_slab", new PaintableSlabItem(settings(), PbParts.PAINTERS_STONE_SLAB));
    public static final PartItem PAINTERS_BRICK_SLAB = register("painters_brick_slab", new PaintableSlabItem(settings(), PbParts.PAINTERS_BRICK_SLAB));
    public static final PartItem PAINTERS_TILE_SLAB = register("painters_tile_slab", new PaintableSlabItem(settings(), PbParts.PAINTERS_TILE_SLAB));
    public static final PartItem POLISHED_PAINTERS_STONE_SLAB = register("polished_painters_stone_slab", new PaintableSlabItem(settings(), PbParts.POLISHED_PAINTERS_STONE_SLAB));
    public static final PartItem GLOWING_PAINTERS_BRICK_SLAB = register("glowing_painters_brick_slab", new PaintableSlabItem(settings(), PbParts.GLOWING_PAINTERS_BRICK_SLAB));
    public static final PartItem GLOWING_PAINTERS_TILE_SLAB = register("glowing_painters_tile_slab", new PaintableSlabItem(settings(), PbParts.GLOWING_PAINTERS_TILE_SLAB));

    private static final List<ItemConvertible> ALL_BLOCK_LIKE_ITEMS = ImmutableList.<ItemConvertible>builder()
        .addAll(PbBlocks.ALL_BLOCKS)
        .add(PAINTERS_STONE_SLAB)
        .add(PAINTERS_BRICK_SLAB)
        .add(PAINTERS_TILE_SLAB)
        .add(POLISHED_PAINTERS_STONE_SLAB)
        .add(GLOWING_PAINTERS_BRICK_SLAB)
        .add(GLOWING_PAINTERS_TILE_SLAB)
        .build();

    public static final List<Item> ALL_DYEABLE_ITEMS = Util.make(ImmutableList.<Item>builder(), builder -> {
        for (ItemConvertible item : ALL_BLOCK_LIKE_ITEMS) {
            builder.add(item.asItem());
        }

        builder.add(SWATCH);
    }).build();

    public static final BiMap<PartDefinition, Item> PART_ITEMS_BY_DEFINITION = Util.make(ImmutableBiMap.<PartDefinition, Item>builder(), builder -> {
        PartItem[] partItems = {
            PAINTERS_STONE_SLAB,
            PAINTERS_BRICK_SLAB,
            PAINTERS_TILE_SLAB,
            POLISHED_PAINTERS_STONE_SLAB,
            GLOWING_PAINTERS_BRICK_SLAB,
            GLOWING_PAINTERS_TILE_SLAB,
        };

        for (PartItem item : partItems) {
            builder.put(item.definition, item);
        }
    }).build();

    public static ItemConvertible[] blockLikeItems() {
        return ALL_BLOCK_LIKE_ITEMS.toArray(new ItemConvertible[0]);
    }

    private static <I extends Item> I register(String id, I item) {
        return Registry.register(Registry.ITEM, PaintersBlocks.id(id), item);
    }

    public static void init() {
        for (Item item : ALL_DYEABLE_ITEMS) {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(item, CauldronBehavior.CLEAN_DYEABLE_ITEM);
        }
    }
}
