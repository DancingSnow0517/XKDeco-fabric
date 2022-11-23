package cn.dancingsnow.xkdeco.mixin;

import cn.dancingsnow.xkdeco.setup.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {
    @Inject(
            method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z",
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0),
            cancellable = true
    )
    public void canPlaceAtMixin(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var blockState = world.getBlockState(pos.down());
        cir.setReturnValue((blockState.isOf(Blocks.CACTUS)
                || blockState.isOf(Blocks.SAND)
                || blockState.isOf(Blocks.RED_SAND)
                || blockState.isOf(ModBlocks.SANDY_COBBLESTONE.getLeft())
                || blockState.isOf(ModBlocks.SANDY_COBBLESTONE_PATH.getLeft())
                || (blockState.isOf(ModBlocks.SANDY_COBBLESTONE_SLAB.getLeft()) && (blockState.get(SlabBlock.TYPE) == SlabType.TOP || blockState.get(SlabBlock.TYPE) == SlabType.DOUBLE))
                || (blockState.isOf(ModBlocks.SANDY_COBBLESTONE_PATH_SLAB.getLeft()) && (blockState.get(SlabBlock.TYPE) == SlabType.TOP || blockState.get(SlabBlock.TYPE) == SlabType.DOUBLE))
                || (blockState.isOf(ModBlocks.SANDY_COBBLESTONE_STAIRS.getLeft()) && blockState.get(StairsBlock.HALF) == BlockHalf.TOP)
                || (blockState.isOf(ModBlocks.SANDY_COBBLESTONE_PATH_STAIRS.getLeft()) && blockState.get(StairsBlock.HALF) == BlockHalf.TOP))
                && !world.getBlockState(pos.up()).getMaterial().isLiquid());
    }
}
