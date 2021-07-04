/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.part;

import alexiil.mc.lib.multipart.api.PartDefinition;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.block.PbBlocks;
import net.minecraft.block.Block;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class PbParts {
    public static final PartDefinition PAINTERS_BRICK_SLAB = register("painters_brick_slab", PaintableSlabPart::new, PaintableSlabPart::new);

    public static final Supplier<Map<PartDefinition, Block>> PARTS_TO_BLOCKS = Suppliers.memoize(
        () -> ImmutableMap.<PartDefinition, Block>builder()
            .put(PAINTERS_BRICK_SLAB, PbBlocks.PAINTERS_BRICKS)
            .build()
    );

    public static final List<PartDefinition> ALL_SLABS = List.of(
        PAINTERS_BRICK_SLAB
    );

    private static PartDefinition register(String id, PartDefinition.IPartNbtReader nbtReader, PartDefinition.IPartNetLoader netLoader) {
        var definition = new PartDefinition(PaintersBlocks.id(id), nbtReader, netLoader);
        PartDefinition.PARTS.put(definition.identifier, definition);
        return definition;
    }

    public static void init() {
    }
}
