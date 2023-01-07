/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.multiparts.item;

import alexiil.mc.lib.multipart.api.PartDefinition;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import juuxel.paintersblocks.item.PbItems;
import juuxel.paintersblocks.multiparts.part.PbmParts;
import net.minecraft.util.Util;

import static juuxel.paintersblocks.item.PbItems.register;
import static juuxel.paintersblocks.item.PbItems.settings;

public final class PbmItems {
    public static final PartItem PAINTERS_STONE_SLAB = register("painters_stone_slab", new PaintableSlabItem(settings(), PbmParts.PAINTERS_STONE_SLAB));
    public static final PartItem PAINTERS_BRICK_SLAB = register("painters_brick_slab", new PaintableSlabItem(settings(), PbmParts.PAINTERS_BRICK_SLAB));
    public static final PartItem PAINTERS_TILE_SLAB = register("painters_tile_slab", new PaintableSlabItem(settings(), PbmParts.PAINTERS_TILE_SLAB));
    public static final PartItem POLISHED_PAINTERS_STONE_SLAB = register("polished_painters_stone_slab", new PaintableSlabItem(settings(), PbmParts.POLISHED_PAINTERS_STONE_SLAB));

    public static final BiMap<PartDefinition, PartItem> PART_ITEMS_BY_DEFINITION = Util.make(ImmutableBiMap.<PartDefinition, PartItem>builder(), builder -> {
        PartItem[] partItems = {
            PAINTERS_STONE_SLAB,
            PAINTERS_BRICK_SLAB,
            PAINTERS_TILE_SLAB,
            POLISHED_PAINTERS_STONE_SLAB
        };

        for (PartItem item : partItems) {
            builder.put(item.definition, item);
        }
    }).build();

    public static void init() {
        for (PartItem item : PART_ITEMS_BY_DEFINITION.values()) {
            PbItems.addCauldronBehavior(item);
        }
    }
}
