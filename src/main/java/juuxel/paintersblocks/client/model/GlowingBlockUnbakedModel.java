/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.client.model;

import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public final class GlowingBlockUnbakedModel implements UnbakedModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaintersBlocks.ID);
    private static final Identifier PARENT_MODEL_ID = new Identifier("minecraft", "block/block");

    private final SpriteIdentifier sideBaseTexture;
    private final SpriteIdentifier sideGlowingTexture;
    private final SpriteIdentifier endBaseTexture;
    private final SpriteIdentifier endGlowingTexture;
    private final SpriteIdentifier particle;

    public GlowingBlockUnbakedModel(
        Identifier sideBaseTexture,
        Identifier sideGlowingTexture,
        Identifier endBaseTexture,
        Identifier endGlowingTexture,
        Identifier particle
    ) {
        this.sideBaseTexture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, sideBaseTexture);
        this.sideGlowingTexture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, sideGlowingTexture);
        this.endBaseTexture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, endBaseTexture);
        this.endGlowingTexture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, endGlowingTexture);
        this.particle = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, particle);
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Set.of(PARENT_MODEL_ID);
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {
    }

    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        var parent = baker.getOrLoadModel(PARENT_MODEL_ID);
        ModelTransformation transformation;

        if (parent instanceof JsonUnbakedModel jum) {
            transformation = jum.getTransformations();
        } else {
            LOGGER.error("{} was not JsonUnbakedModel, found: {}", PARENT_MODEL_ID, parent);
            transformation = ModelTransformation.NONE;
        }

        return new GlowingBlockBakedModel(
            textureGetter.apply(sideBaseTexture),
            textureGetter.apply(sideGlowingTexture),
            textureGetter.apply(endBaseTexture),
            textureGetter.apply(endGlowingTexture),
            textureGetter.apply(particle),
            transformation, rotationContainer
        );
    }

    public static final class Builder {
        private Identifier sideBaseTexture, sideGlowingTexture, endBaseTexture, endGlowingTexture, particle;

        private static Identifier getTextureId(String name) {
            return PaintersBlocks.id("block/" + name);
        }

        public Builder base(String side, String end) {
            sideBaseTexture = getTextureId(side);
            endBaseTexture = getTextureId(end);
            return this;
        }

        public Builder base(String all) {
            return base(all, all);
        }

        public Builder glowing(String side, String end) {
            sideGlowingTexture = getTextureId(side);
            endGlowingTexture = getTextureId(end);
            return this;
        }

        public Builder glowing(String all) {
            return glowing(all, all);
        }

        public Builder particle(String particle) {
            this.particle = getTextureId(particle);
            return this;
        }

        public GlowingBlockUnbakedModel build() {
            return new GlowingBlockUnbakedModel(
                Objects.requireNonNull(sideBaseTexture),
                Objects.requireNonNull(sideGlowingTexture),
                Objects.requireNonNull(endBaseTexture),
                Objects.requireNonNull(endGlowingTexture),
                Objects.requireNonNullElse(particle, sideBaseTexture)
            );
        }
    }
}
