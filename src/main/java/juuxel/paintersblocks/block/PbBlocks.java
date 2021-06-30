package juuxel.paintersblocks.block;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.item.PaintersItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.util.function.BiFunction;

public final class PbBlocks {
    public static final Block PAINTERS_BRICKS = register("painters_bricks", new PaintersBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)), PaintersItem::new);
    public static final Block PAINTERS_TILES = register("painters_tiles", new PaintersBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)), PaintersItem::new);

    private static Block register(String id, Block block, BiFunction<Block, Item.Settings, Item> item) {
        Registry.register(Registry.BLOCK, PaintersBlocks.id(id), block);
        Registry.register(Registry.ITEM, PaintersBlocks.id(id), item.apply(block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        return block;
    }

    public static void init() {
    }
}
