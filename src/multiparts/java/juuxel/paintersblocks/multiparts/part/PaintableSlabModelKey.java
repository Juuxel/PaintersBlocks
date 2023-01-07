/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.multiparts.part;

import alexiil.mc.lib.multipart.api.PartDefinition;
import alexiil.mc.lib.multipart.api.render.PartModelKey;
import net.minecraft.block.enums.BlockHalf;

import java.util.Objects;

public final class PaintableSlabModelKey extends PartModelKey {
    public final PartDefinition definition;
    public final BlockHalf half;
    public final int color;

    public PaintableSlabModelKey(PartDefinition definition, BlockHalf half, int color) {
        this.definition = definition;
        this.half = half;
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PaintableSlabModelKey key && definition.equals(key.definition) && half.equals(key.half) && color == key.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(definition, color);
    }
}
