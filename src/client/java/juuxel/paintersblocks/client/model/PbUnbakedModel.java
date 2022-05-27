package juuxel.paintersblocks.client.model;

import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

abstract class PbUnbakedModel implements UnbakedModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaintersBlocks.ID);
    protected static final Identifier PARENT_MODEL_ID = new Identifier("minecraft", "block/block");

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Set.of(PARENT_MODEL_ID);
    }

    protected final ModelTransformation getParentTransformation(ModelLoader loader) {
        var parent = loader.getOrLoadModel(PARENT_MODEL_ID);

        if (parent instanceof JsonUnbakedModel jum) {
            return jum.getTransformations();
        } else {
            LOGGER.error("Parent model {} was not JsonUnbakedModel, found: {}", PARENT_MODEL_ID, parent);
            return ModelTransformation.NONE;
        }
    }
}
