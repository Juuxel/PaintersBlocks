/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.client.model;

import com.mojang.datafixers.util.Pair;
import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public final class GlowingBlockUnbakedModel extends PbUnbakedModel {
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
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return List.of(this.sideBaseTexture, this.sideGlowingTexture, this.endBaseTexture, this.endGlowingTexture, this.particle);
    }

    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        ModelTransformation transformation = getParentTransformation(loader);

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
