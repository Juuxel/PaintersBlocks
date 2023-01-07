/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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
