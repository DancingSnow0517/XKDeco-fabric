package cn.dancingsnow.xkdeco.blockentity;

import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class WardrobeBlockEntity extends BlockEntity {
    public WardrobeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WARDROBE_BLOCK_ENTITY, pos, state);
    }
}
