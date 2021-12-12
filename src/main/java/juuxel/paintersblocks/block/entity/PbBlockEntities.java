/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.block.entity;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.block.PbBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public final class PbBlockEntities {
    public static final BlockEntityType<PaintableBlockEntity> PAINTERS_BLOCK = register("painters_block", FabricBlockEntityTypeBuilder.create(PaintableBlockEntity::new, PbBlocks.all()).build());

    private static <T extends BlockEntityType<?>> T register(String id, T type) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, PaintersBlocks.id(id), type);
    }

    public static void init() {
    }

    static void sync(BlockEntity entity) {
        World world = entity.getWorld();

        if (world instanceof ServerWorld sw) {
            sw.getChunkManager().markForUpdate(entity.getPos());
        } else {
            throw new UnsupportedOperationException("[Painter's Blocks] Trying to sync from the client thread. Please report thi");
        }
    }
}
