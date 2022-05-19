/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.item;

import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public final class PbItemTags {
    public static final TagKey<Item> GLOW_INGREDIENTS = register("glow_ingredients");

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registry.ITEM_KEY, PaintersBlocks.id(id));
    }

    public static void init() {
    }
}
