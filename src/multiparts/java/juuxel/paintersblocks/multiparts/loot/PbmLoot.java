/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.multiparts.loot;

import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.loot.provider.nbt.LootNbtProviderType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class PbmLoot {
    public static final LootNbtProviderType PART = register("part", new LootNbtProviderType(new PartLootNbtProvider.Serializer()));

    private static LootNbtProviderType register(String id, LootNbtProviderType type) {
        return Registry.register(Registries.LOOT_NBT_PROVIDER_TYPE, PaintersBlocks.id(id), type);
    }

    public static void init() {

    }
}
