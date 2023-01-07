package juuxel.paintersblocks.multiparts;

import juuxel.paintersblocks.multiparts.item.PbmItems;
import juuxel.paintersblocks.multiparts.loot.PbmLoot;
import juuxel.paintersblocks.multiparts.part.PbmParts;
import net.fabricmc.api.ModInitializer;

public final class PaintersBlocksMultiparts implements ModInitializer {
    @Override
    public void onInitialize() {
        PbmParts.init();
        PbmItems.init();
        PbmLoot.init();
    }
}
