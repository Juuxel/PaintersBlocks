package juuxel.paintersblocks.client;

import juuxel.paintersblocks.block.PbBlocks;
import juuxel.paintersblocks.block.entity.PaintersBlockEntity;
import juuxel.paintersblocks.block.entity.PbBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.DyeableItem;
import org.jetbrains.annotations.Nullable;

public final class PaintersBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (tintIndex == 0 && world != null) {
                @Nullable PaintersBlockEntity entity = world.getBlockEntity(pos, PbBlockEntities.PAINTERS_BLOCK).orElse(null);

                if (entity != null) {
                    return entity.getColor();
                }
            }

            return -1;
        }, PbBlocks.PAINTERS_BRICKS, PbBlocks.PAINTERS_TILES);

        ColorProviderRegistry.ITEM.register(
            (stack, tintIndex) -> tintIndex == 0 ? ((DyeableItem) stack.getItem()).getColor(stack) : -1,
            PbBlocks.PAINTERS_BRICKS, PbBlocks.PAINTERS_TILES
        );
    }
}
