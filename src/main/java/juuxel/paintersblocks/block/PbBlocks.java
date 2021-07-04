/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.block;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.item.PaintableItem;
import juuxel.paintersblocks.item.PbItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.BiFunction;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public final class PbBlocks {
    public static final Block PAINTERS_STONE = register("painters_stone", new PaintableBlock(copy(Blocks.STONE)), PaintableItem::new);
    public static final Block PAINTERS_BRICKS = register("painters_bricks", new PaintableBlock(copy(Blocks.STONE_BRICKS)), PaintableItem::new);
    public static final Block PAINTERS_TILES = register("painters_tiles", new PaintableBlock(copy(PAINTERS_BRICKS)), PaintableItem::new);
    public static final Block POLISHED_PAINTERS_STONE = register("polished_painters_stone", new PaintableBlock(copy(PAINTERS_BRICKS)), PaintableItem::new);
    public static final Block CHISELED_PAINTERS_STONE = register("chiseled_painters_stone", new PaintableBlock(copy(PAINTERS_BRICKS)), PaintableItem::new);

    public static final Block GLOWING_PAINTERS_BRICKS = register("glowing_painters_bricks", new PaintableBlock(copy(PAINTERS_BRICKS).nonOpaque().luminance(state -> 10)), PaintableItem::new);
    public static final Block GLOWING_PAINTERS_TILES = register("glowing_painters_tiles", new PaintableBlock(copy(GLOWING_PAINTERS_BRICKS)), PaintableItem::new);
    public static final Block GLOWING_CHISELED_PAINTERS_STONE = register("glowing_chiseled_painters_stone", new PaintableBlock(copy(GLOWING_PAINTERS_BRICKS)), PaintableItem::new);

    public static final List<Block> ALL_BLOCKS = List.of(
        PAINTERS_STONE,
        PAINTERS_BRICKS,
        PAINTERS_TILES,
        POLISHED_PAINTERS_STONE,
        CHISELED_PAINTERS_STONE,
        GLOWING_PAINTERS_BRICKS,
        GLOWING_PAINTERS_TILES,
        GLOWING_CHISELED_PAINTERS_STONE
    );

    private static Block register(String id, Block block, BiFunction<Block, Item.Settings, Item> item) {
        Registry.register(Registry.BLOCK, PaintersBlocks.id(id), block);
        Registry.register(Registry.ITEM, PaintersBlocks.id(id), item.apply(block, new Item.Settings().group(PbItems.GROUP)));
        return block;
    }

    public static Block[] all() {
        return ALL_BLOCKS.toArray(new Block[0]);
    }

    public static void init() {
    }
}
