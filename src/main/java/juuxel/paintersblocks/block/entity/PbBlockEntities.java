package juuxel.paintersblocks.block.entity;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.block.PbBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public final class PbBlockEntities {
    public static final BlockEntityType<PaintersBlockEntity> PAINTERS_BLOCK = register("painters_block", FabricBlockEntityTypeBuilder.create(PaintersBlockEntity::new, PbBlocks.PAINTERS_BRICKS, PbBlocks.PAINTERS_TILES).build());

    private static <T extends BlockEntityType<?>> T register(String id, T type) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, PaintersBlocks.id(id), type);
    }

    public static void init() {
    }
}
