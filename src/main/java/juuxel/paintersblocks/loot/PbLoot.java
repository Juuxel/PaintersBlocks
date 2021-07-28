/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.loot;

import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.loot.provider.nbt.LootNbtProviderType;
import net.minecraft.util.registry.Registry;

public final class PbLoot {
    public static final LootNbtProviderType PART = register("part", new LootNbtProviderType(new PartLootNbtProvider.Serializer()));

    private static LootNbtProviderType register(String id, LootNbtProviderType type) {
        return Registry.register(Registry.LOOT_NBT_PROVIDER_TYPE, PaintersBlocks.id(id), type);
    }

    public static void init() {
    }
}
