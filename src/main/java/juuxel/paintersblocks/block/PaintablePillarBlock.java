package juuxel.paintersblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class PaintablePillarBlock extends PaintableBlock {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public PaintablePillarBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(AXIS, ctx.getSide().getAxis());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AXIS);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return PillarBlock.changeRotation(state, rotation);
    }
}
