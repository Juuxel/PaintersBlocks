/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.block.entity;

import juuxel.paintersblocks.item.PaintableItem;
import juuxel.paintersblocks.util.NbtKeys;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class PaintableBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    private int color = PaintableItem.DEFAULT_COLOR;

    public PaintableBlockEntity(BlockPos pos, BlockState state) {
        super(PbBlockEntities.PAINTERS_BLOCK, pos, state);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        color = nbt.getInt(NbtKeys.COLOR);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(NbtKeys.COLOR, color);
        return nbt;
    }

    @Override
    public void fromClientTag(NbtCompound nbt) {
        color = nbt.getInt(NbtKeys.COLOR);

        // rerender
        // TODO: this is concern, can I remove it?
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public NbtCompound toClientTag(NbtCompound nbt) {
        nbt.putInt(NbtKeys.COLOR, color);
        return nbt;
    }
}
