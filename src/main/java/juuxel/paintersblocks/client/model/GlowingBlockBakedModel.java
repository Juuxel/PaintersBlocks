/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.client.model;

import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public final class GlowingBlockBakedModel implements BakedModel, FabricBakedModel {
    private static final Supplier<RenderMaterial> GLOWING_MATERIAL = Suppliers.memoize(() -> {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        return renderer.materialFinder()
            .blendMode(0, BlendMode.CUTOUT)
            .emissive(0, true)
            .find();
    });
    private static final int WHITE = 0xFF_FFFFFF;
    private final Mesh mesh;
    private final Sprite particle;
    private final ModelTransformation transformation;

    public GlowingBlockBakedModel(Sprite sideBaseTexture, Sprite sideGlowingTexture,
                                  Sprite endBaseTexture, Sprite endGlowingTexture, Sprite particle,
                                  ModelTransformation transformation, ModelBakeSettings bakeSettings) {
        this.transformation = transformation;
        this.mesh = build(sideBaseTexture, sideGlowingTexture, endBaseTexture, endGlowingTexture, bakeSettings);
        this.particle = particle;
    }

    private static Mesh build(Sprite sideBaseTexture, Sprite sideGlowingTexture,
                              Sprite endBaseTexture, Sprite endGlowingTexture,
                              ModelBakeSettings bakeSettings) {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        var builder = renderer.meshBuilder();
        var emitter = builder.getEmitter();
        var rotationMatrix = bakeSettings.getRotation().getMatrix();

        for (final Direction originalFace : Direction.values()) {
            final Direction face = Direction.transform(rotationMatrix, originalFace);
            final boolean vertical = originalFace.getAxis().isVertical();
            int spriteBakeFlags = MutableQuadView.BAKE_LOCK_UV;

            if (!vertical) {
                Direction whereUpLands = Direction.transform(rotationMatrix, Direction.UP);

                // Only rotate when UP goes somewhere and the original face texture isn't vertical (an end of a pillar).
                // The special case for Z and Y is to not rotate those textures since it would cause it to look broken.
                if (whereUpLands != Direction.UP &&
                    !(whereUpLands.getAxis() == Direction.Axis.Z && face.getAxis() == Direction.Axis.Y)) {
                    spriteBakeFlags |= MutableQuadView.BAKE_ROTATE_90;
                }
            }

            // Output base texture
            emitter.square(face, 0f, 0f, 1f, 1f, 0f);
            Sprite baseTexture = vertical ? endBaseTexture : sideBaseTexture;
            emitter.spriteBake(0, baseTexture, spriteBakeFlags);
            emitter.spriteColor(0, WHITE, WHITE, WHITE, WHITE);
            emitter.colorIndex(0);
            emitter.emit();

            // Output glowing texture
            emitter.square(face, 0f, 0f, 1f, 1f, 0f);
            Sprite glowingTexture = vertical ? endGlowingTexture : sideGlowingTexture;
            emitter.spriteBake(0, glowingTexture, spriteBakeFlags);
            emitter.spriteColor(0, WHITE, WHITE, WHITE, WHITE);
            emitter.material(GLOWING_MATERIAL.get());
            emitter.emit();
        }

        return builder.build();
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh);
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return true;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return particle;
    }

    @Override
    public ModelTransformation getTransformation() {
        return transformation;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
}
