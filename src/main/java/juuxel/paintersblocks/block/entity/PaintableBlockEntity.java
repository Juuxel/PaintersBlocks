/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.block.entity;

import juuxel.paintersblocks.item.SwatchItem;
import juuxel.paintersblocks.util.Colors;
import juuxel.paintersblocks.util.NbtKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PaintableBlockEntity extends BlockEntity implements SwatchItem.DyeTarget {
    private int color = Colors.STONE_DEFAULT_COLOR;

    public PaintableBlockEntity(BlockPos pos, BlockState state) {
        super(PbBlockEntities.PAINTERS_BLOCK, pos, state);
    }

    @Override
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        markDirty();
    }

    @Override
    public void setColorAndSync(int color) {
        setColor(color);
        PbBlockEntities.sync(this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        color = nbt.getInt(NbtKeys.COLOR);

        if (world != null && world.isClient) {
            // rerender
            // TODO: this is concern, can I remove it?
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(NbtKeys.COLOR, color);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
