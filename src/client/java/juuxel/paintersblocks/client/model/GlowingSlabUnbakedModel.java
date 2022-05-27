package juuxel.paintersblocks.client.model;

import com.mojang.datafixers.util.Pair;
import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public final class GlowingSlabUnbakedModel extends PbUnbakedModel {
    private final BlockHalf half;
    private final SpriteIdentifier baseTexture;
    private final SpriteIdentifier glowingTexture;

    public GlowingSlabUnbakedModel(BlockHalf half, Identifier baseTexture, Identifier glowingTexture) {
        this.half = half;
        this.baseTexture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, baseTexture);
        this.glowingTexture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, glowingTexture);
    }

    public GlowingSlabUnbakedModel(BlockHalf half, String baseTexture, String glowingTexture) {
        this(half, PaintersBlocks.id("block/" + baseTexture), PaintersBlocks.id("block/" + glowingTexture));
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return List.of(baseTexture, glowingTexture);
    }

    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return new GlowingSlabBakedModel(
            half,
            textureGetter.apply(baseTexture),
            textureGetter.apply(glowingTexture),
            getParentTransformation(loader)
        );
    }
}
