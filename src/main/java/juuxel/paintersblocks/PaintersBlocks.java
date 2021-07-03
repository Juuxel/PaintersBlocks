/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks;

import juuxel.paintersblocks.block.PbBlocks;
import juuxel.paintersblocks.block.entity.PbBlockEntities;
import juuxel.paintersblocks.item.PbItemTags;
import juuxel.paintersblocks.item.PbItems;
import juuxel.paintersblocks.recipe.PbRecipes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public final class PaintersBlocks implements ModInitializer {
    // TODO: Painter's Stone?§
    public static final String ID = "painters_blocks";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        PbItems.init();
        PbItemTags.init();
        PbBlocks.init();
        PbBlockEntities.init();
        PbRecipes.init();
    }
}
