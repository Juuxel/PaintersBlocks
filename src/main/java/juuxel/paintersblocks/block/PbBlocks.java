/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.block;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.item.PaintersItem;
import juuxel.paintersblocks.item.PbItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.BiFunction;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public final class PbBlocks {
    public static final Block PAINTERS_BRICKS = register("painters_bricks", new PaintersBlock(copy(Blocks.STONE_BRICKS)), PaintersItem::new);
    public static final Block PAINTERS_TILES = register("painters_tiles", new PaintersBlock(copy(Blocks.STONE_BRICKS)), PaintersItem::new);
    public static final Block POLISHED_PAINTERS_STONE = register("polished_painters_stone", new PaintersBlock(copy(Blocks.STONE_BRICKS)), PaintersItem::new);
    public static final Block CHISELED_PAINTERS_STONE = register("chiseled_painters_stone", new PaintersBlock(copy(Blocks.STONE_BRICKS)), PaintersItem::new);

    public static final List<Block> ALL_BLOCKS = List.of(PAINTERS_BRICKS, PAINTERS_TILES, POLISHED_PAINTERS_STONE, CHISELED_PAINTERS_STONE);

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
