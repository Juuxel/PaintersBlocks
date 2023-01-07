/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.multiparts.item;

import alexiil.mc.lib.multipart.api.PartDefinition;
import net.minecraft.item.Item;

public abstract class PartItem extends Item {
    final PartDefinition definition;

    public PartItem(Settings settings, PartDefinition definition) {
        super(settings);
        this.definition = definition;
    }
}
