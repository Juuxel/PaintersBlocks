/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.mixin;

import juuxel.paintersblocks.PaintersBlocks;
import juuxel.paintersblocks.item.PbItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Shadow
    private static native boolean isSectionVisible(int flags, ItemStack.TooltipSection tooltipSection);

    // would use an inject but isSectionVisible is static...
    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isSectionVisible(ILnet/minecraft/item/ItemStack$TooltipSection;)Z", ordinal = 2))
    private boolean hideDyeSectionForSwatches(int flags, ItemStack.TooltipSection section) {
        if (section != ItemStack.TooltipSection.DYE) {
            throw new AssertionError("[" + PaintersBlocks.ID + "] Injecting at an incorrect place!");
        }

        if (getItem() == PbItems.SWATCH) {
            return false;
        }

        return isSectionVisible(flags, section);
    }
}
