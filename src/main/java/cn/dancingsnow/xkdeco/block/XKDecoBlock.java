package cn.dancingsnow.xkdeco.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.shape.VoxelShape;

public sealed interface XKDecoBlock permits XKDecoBlock.Basic, XKDecoBlock.Isotropic, XKDecoBlock.Plant, XKDecoBlock.Special {
    /**
     * basic blocks which have directions
     */
    sealed interface Basic extends XKDecoBlock permits BasicBlock, BasicCubeBlock, BasicFullDirectionBlock {

    }

    /**
     * isotropic blocks which are directionless or uv locked (stairs, slabs, or pillars)
     */
    sealed interface Isotropic extends XKDecoBlock permits IsotropicCubeBlock, IsotropicHollowBlock, IsotropicPillarBlock, IsotropicRoofBlock, IsotropicRoofEaveBlock, IsotropicRoofFlatBlock, IsotropicFallingBlock, IsotropicSlabBlock, IsotropicStairBlock {

        /**
         * @return true if the block should be considered as "glass"; false otherwise.
         */
        boolean isGlass();

        /**
         * Return the static shape of the block, without knowing the current Level info.
         *
         * @param state Current block state
         * @return The shape of the block under given state
         */
        VoxelShape getShapeStatic(BlockState state);

    }

    /**
     * plant blocks which are related to grass and leaves
     */
    sealed interface Plant extends XKDecoBlock permits PlantLeavesBlock, PlantLeavesShatterBlock, PlantSlabBlock {
        // nothing here
    }

    // not impl
    sealed interface Special extends XKDecoBlock permits SpecialBlockDisplayBlock, SpecialConsole, SpecialCupBlock, SpecialDessertBlock, SpecialItemDisplayBlock, SpecialLightBar, SpecialRoofRidgeBlock, SpecialWallBlock, SpecialWardrobeBlock {
        // nothing here
    }


}
