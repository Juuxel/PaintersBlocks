/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.client.model;

import com.google.common.collect.ImmutableMap;
import juuxel.paintersblocks.PaintersBlocks;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

public final class GlowingBlockModels implements ModelResourceProvider {
    private static final Map<Identifier, UnbakedModel> MODELS =
        Util.make(ImmutableMap.<Identifier, UnbakedModel>builder(), builder -> {
            BiConsumer<String, UnbakedModel> adder = (name, model) -> {
                builder.put(PaintersBlocks.id("block/" + name), model);
                builder.put(PaintersBlocks.id("item/" + name), model);
            };
            TriConsumer<BlockHalf, String, UnbakedModel> partAdder = (half, name, model) -> {
                builder.put(PaintersBlocks.id("part/" + name + '_' + half.asString()), model);

                if (half == BlockHalf.BOTTOM) {
                    builder.put(PaintersBlocks.id("item/" + name), model);
                }
            };

            adder.accept("glowing_painters_bricks",
                new GlowingBlockUnbakedModel.Builder()
                    .base("painters_bricks")
                    .glowing("painters_bricks_glow")
                    .build());
            adder.accept("glowing_painters_tiles",
                new GlowingBlockUnbakedModel.Builder()
                    .base("painters_tiles")
                    .glowing("painters_tiles_glow")
                    .build());
            adder.accept("glowing_chiseled_painters_stone",
                new GlowingBlockUnbakedModel.Builder()
                    .base("chiseled_painters_stone")
                    .glowing("chiseled_painters_stone_glow")
                    .build());
            adder.accept("glowing_painters_stone_pillar",
                new GlowingBlockUnbakedModel.Builder()
                    .base("painters_stone_pillar_side", "painters_stone_pillar_end")
                    .glowing("painters_stone_pillar_side_glow", "painters_stone_pillar_end_glow")
                    .build());

            for (BlockHalf half : BlockHalf.values()) {
                partAdder.accept(half, "glowing_painters_brick_slab",
                    new GlowingSlabUnbakedModel(half, "painters_bricks", "painters_bricks_glow"));
                partAdder.accept(half, "glowing_painters_tile_slab",
                    new GlowingSlabUnbakedModel(half, "painters_tiles", "painters_tiles_glow"));
            }
        }).build();

    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> new GlowingBlockModels());
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) {
        return MODELS.get(resourceId);
    }

    @FunctionalInterface
    private interface TriConsumer<A, B, C> {
        void accept(A a, B b, C c);
    }
}
