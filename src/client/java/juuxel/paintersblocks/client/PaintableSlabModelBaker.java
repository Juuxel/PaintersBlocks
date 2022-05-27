/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.client;

import alexiil.mc.lib.multipart.api.PartDefinition;
import alexiil.mc.lib.multipart.api.render.PartModelBaker;
import alexiil.mc.lib.multipart.api.render.PartRenderContext;
import com.mojang.datafixers.util.Pair;
import juuxel.paintersblocks.part.PaintableSlabModelKey;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public final class PaintableSlabModelBaker implements PartModelBaker<PaintableSlabModelKey> {
    private static final Map<Pair<PartDefinition, BlockHalf>, Identifier> PARTS_TO_MODEL_IDS = new HashMap<>();

    private static Identifier getModelId(PaintableSlabModelKey key) {
        return getModelId(key.definition, key.half);
    }

    static Identifier getModelId(PartDefinition def, BlockHalf half) {
        return PARTS_TO_MODEL_IDS.computeIfAbsent(
            new Pair<>(def, half), pair -> new Identifier(def.identifier.getNamespace(), "part/" + def.identifier.getPath() + '_' + half.asString())
        );
    }

    @Override
    public void emitQuads(PaintableSlabModelKey key, PartRenderContext ctx) {
        BakedModel model = BakedModelManagerHelper.getModel(MinecraftClient.getInstance().getBakedModelManager(), getModelId(key));

        ctx.pushTransform(quad -> {
            int color = 0xFF_000000 | key.color;
            quad.spriteColor(0, color, color, color, color);
            return true;
        });
        ctx.fallbackConsumer().accept(model);
        ctx.popTransform();
    }
}
