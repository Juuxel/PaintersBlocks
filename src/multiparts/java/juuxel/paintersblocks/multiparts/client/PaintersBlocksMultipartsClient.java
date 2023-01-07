/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.multiparts.client;

import alexiil.mc.lib.multipart.api.PartDefinition;
import alexiil.mc.lib.multipart.api.render.PartStaticModelRegisterEvent;
import juuxel.paintersblocks.client.PaintersBlocksClient;
import juuxel.paintersblocks.multiparts.item.PbmItems;
import juuxel.paintersblocks.multiparts.part.PaintableSlabModelKey;
import juuxel.paintersblocks.multiparts.part.PbmParts;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.block.enums.BlockHalf;

public final class PaintersBlocksMultipartsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PartStaticModelRegisterEvent.EVENT.register(
            renderer -> renderer.register(PaintableSlabModelKey.class, new PaintableSlabModelBaker())
        );

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            for (PartDefinition part : PbmParts.ALL_SLABS) {
                for (BlockHalf half : BlockHalf.values()) {
                    out.accept(PaintableSlabModelBaker.getModelId(part, half));
                }
            }
        });

        PaintersBlocksClient.registerDyeableColorProvider(PbmItems.PART_ITEMS_BY_DEFINITION.values());
    }
}
