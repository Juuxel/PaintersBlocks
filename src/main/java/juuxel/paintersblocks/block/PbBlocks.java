/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.block;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.item.PaintersItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.BiFunction;

public final class PbBlocks {
    public static final Block PAINTERS_BRICKS = register("painters_bricks", new PaintersBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)), PaintersItem::new);
    public static final Block PAINTERS_TILES = register("painters_tiles", new PaintersBlock(AbstractBlock.Settings.copy(PAINTERS_BRICKS)), PaintersItem::new);
    public static final Block GLOWING_PAINTERS_BRICKS = register("glowing_painters_bricks", new PaintersBlock(AbstractBlock.Settings.copy(PAINTERS_BRICKS).luminance(state -> 8)), PaintersItem::new);
    public static final Block GLOWING_PAINTERS_TILES = register("glowing_painters_tiles", new PaintersBlock(AbstractBlock.Settings.copy(GLOWING_PAINTERS_BRICKS)), PaintersItem::new);

    public static final List<Block> ALL_BLOCKS = List.of(PAINTERS_BRICKS, PAINTERS_TILES, GLOWING_PAINTERS_BRICKS, GLOWING_PAINTERS_TILES);

    private static Block register(String id, Block block, BiFunction<Block, Item.Settings, Item> item) {
        Registry.register(Registry.BLOCK, PaintersBlocks.id(id), block);
        Registry.register(Registry.ITEM, PaintersBlocks.id(id), item.apply(block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        return block;
    }

    public static Block[] allBlocks() {
        return ALL_BLOCKS.toArray(Block[]::new);
    }

    public static void init() {
    }
}
