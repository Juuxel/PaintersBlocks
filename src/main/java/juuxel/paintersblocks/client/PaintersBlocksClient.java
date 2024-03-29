/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.client;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.block.PbBlocks;
import juuxel.paintersblocks.block.entity.PaintableBlockEntity;
import juuxel.paintersblocks.block.entity.PbBlockEntities;
import juuxel.paintersblocks.client.model.GlowingBlockModels;
import juuxel.paintersblocks.item.PbItems;
import juuxel.paintersblocks.item.SwatchItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemConvertible;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public final class PaintersBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (tintIndex == 0 && world != null) {
                @Nullable PaintableBlockEntity entity = world.getBlockEntity(pos, PbBlockEntities.PAINTERS_BLOCK).orElse(null);

                if (entity != null) {
                    return entity.getColor();
                }
            }

            return -1;
        }, PbBlocks.all());

        registerDyeableColorProvider(PbBlocks.ALL_BLOCKS);

        ColorProviderRegistry.ITEM.register(
            (stack, tintIndex) -> ((SwatchItem) stack.getItem()).getColor(stack, tintIndex),
            PbItems.SWATCH
        );

        FabricModelPredicateProviderRegistry.register(
            PbItems.SWATCH, PaintersBlocks.id("dyed"),
            (stack, world, entity, seed) -> ((DyeableItem) stack.getItem()).hasColor(stack) ? 1 : 0
        );

        GlowingBlockModels.init();
    }

    public static void registerDyeableColorProvider(Collection<? extends ItemConvertible> items) {
        ColorProviderRegistry.ITEM.register(
            (stack, tintIndex) -> tintIndex == 0 ? ((DyeableItem) stack.getItem()).getColor(stack) : -1,
            items.toArray(ItemConvertible[]::new)
        );
    }
}
