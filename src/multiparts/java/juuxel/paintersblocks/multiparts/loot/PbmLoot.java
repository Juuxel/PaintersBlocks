package juuxel.paintersblocks.multiparts.loot;

import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.loot.provider.nbt.LootNbtProviderType;
import net.minecraft.util.registry.Registry;

public final class PbmLoot {
    public static final LootNbtProviderType PART = register("part", new LootNbtProviderType(new PartLootNbtProvider.Serializer()));

    private static LootNbtProviderType register(String id, LootNbtProviderType type) {
        return Registry.register(Registry.LOOT_NBT_PROVIDER_TYPE, PaintersBlocks.id(id), type);
    }

    public static void init() {

    }
}
