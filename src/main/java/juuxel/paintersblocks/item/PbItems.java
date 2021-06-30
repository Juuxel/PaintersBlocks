package juuxel.paintersblocks.item;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.block.PbBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

public final class PbItems {
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(PaintersBlocks.id("group"), () -> {
        ItemStack stack = new ItemStack(PbBlocks.PAINTERS_BRICKS);
        PaintersItem.setColor(stack, DyeColor.LIME);
        return stack;
    });

    public static void init() {
    }
}
