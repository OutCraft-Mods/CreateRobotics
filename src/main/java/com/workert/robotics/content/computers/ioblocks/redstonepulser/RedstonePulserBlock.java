package com.workert.robotics.content.computers.ioblocks.redstonepulser;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.gui.ScreenOpener;
import com.workert.robotics.base.registries.BlockEntityRegistry;
import com.workert.robotics.content.computers.ioblocks.IOSignalScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

public class RedstonePulserBlock extends Block implements EntityBlock, IBE<RedstonePulserBlockEntity> {

	public static final BooleanProperty LIT = BooleanProperty.create("lit");

	public RedstonePulserBlock(Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
	}

	@Override
	public Class<RedstonePulserBlockEntity> getBlockEntityClass() {
		return RedstonePulserBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends RedstonePulserBlockEntity> getBlockEntityType() {
		return BlockEntityRegistry.REDSTONE_PULSER.get();
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return BlockEntityRegistry.REDSTONE_PULSER.get().create(blockPos, blockState);
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack held = player.getMainHandItem();
		if (AllItems.WRENCH.isIn(held)) return InteractionResult.PASS;
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
				() -> () -> this.withBlockEntityDo(level, blockPos, te -> this.displayScreen(te, player)));


		return InteractionResult.SUCCESS;
	}

	@Override
	public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		super.tick(pState, pLevel, pPos, pRandom);
		pLevel.updateNeighborsAt(pPos, this);
	}

	@Override
	public boolean isSignalSource(BlockState pState) {
		return true;
	}

	@Override
	public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
		return this.getBlockEntityOptional(pLevel, pPos).map(al -> al.redstoneLevel).orElse(0);
	}

	@OnlyIn(value = Dist.CLIENT)
	protected void displayScreen(RedstonePulserBlockEntity redstonePulser, Player player) {
		if (player instanceof LocalPlayer) ScreenOpener.open(new IOSignalScreen(redstonePulser));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(LIT);
	}
}
