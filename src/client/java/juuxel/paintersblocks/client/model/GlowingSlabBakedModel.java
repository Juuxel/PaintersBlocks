package juuxel.paintersblocks.client.model;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;

import static juuxel.paintersblocks.client.model.GlowingBlockBakedModel.WHITE;

public final class GlowingSlabBakedModel extends PbBakedModel {
    private static final boolean[] BOOLEANS = { false, true };
    private final Sprite particle;
    private final ModelTransformation transformation;

    public GlowingSlabBakedModel(BlockHalf half, Sprite baseTexture, Sprite glowingTexture, ModelTransformation transformation) {
        super(buildMesh(half, baseTexture, glowingTexture));
        this.particle = baseTexture;
        this.transformation = transformation;
    }

    private static Mesh buildMesh(BlockHalf half, Sprite baseTexture, Sprite glowingTexture) {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        var builder = renderer.meshBuilder();
        var emitter = builder.getEmitter();

        for (Direction face : Direction.values()) {
            final boolean vertical = face.getAxis().isVertical();

            for (boolean glowing : BOOLEANS) {
                if (vertical) {
                    float depth = 0f;
                    if ((half == BlockHalf.TOP && face == Direction.DOWN) ||
                        (half == BlockHalf.BOTTOM && face == Direction.UP)) {
                        depth = 0.5f;
                    }

                    emitter.square(face, 0f, 0f, 1f, 1f, depth);
                } else if (half == BlockHalf.TOP) {
                    emitter.square(face, 0f, 0.5f, 1f, 1f, 0f);
                } else {
                    emitter.square(face, 0f, 0f, 1f, 0.5f, 0f);
                }

                emitter.spriteBake(0, glowing ? glowingTexture : baseTexture, MutableQuadView.BAKE_LOCK_UV);
                emitter.spriteColor(0, WHITE, WHITE, WHITE, WHITE);

                if (glowing) {
                    emitter.material(GlowingBlockBakedModel.GLOWING_MATERIAL.get());
                } else {
                    emitter.colorIndex(0);
                }

                emitter.emit();
            }
        }

        return builder.build();
    }

    public Sprite getParticleSprite() {
        return particle;
    }

    @Override
    public ModelTransformation getTransformation() {
        return transformation;
    }
}
