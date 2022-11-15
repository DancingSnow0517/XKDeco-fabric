package cn.dancingsnow.xkdeco.blockentities;

import cn.dancingsnow.xkdeco.blocks.SpecialWallBlock;
import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class WallBlockEntity extends BlockEntity {
    private Block eastBlock = Blocks.AIR;
    private Block westBlock = Blocks.AIR;
    private Block southBlock = Blocks.AIR;
    private Block northBlock = Blocks.AIR;

    public WallBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WALL_BLOCK_ENTITY, pos, state);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public void updateBlocksFromLevel(SpecialWallBlock wall) {
        var pos = this.getPos();
        var block = this.getCachedState().getBlock();
        if (this.world != null) {
            var northWall = wall.connectsTo(this.world.getBlockState(pos.north()));
            var eastWall = wall.connectsTo(this.world.getBlockState(pos.east()));
            var southWall = wall.connectsTo(this.world.getBlockState(pos.south()));
            var westWall = wall.connectsTo(this.world.getBlockState(pos.west()));
            this.northBlock = northWall.orElse(Blocks.AIR);
            this.eastBlock = eastWall.orElse(Blocks.AIR);
            this.southBlock = southWall.orElse(Blocks.AIR);
            this.westBlock = westWall.orElse(Blocks.AIR);
            this.markDirty();
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.northBlock = Objects.requireNonNullElse(Registry.BLOCK
                .get(new Identifier(nbt.getString("NorthBlockName"))), Blocks.AIR);
        this.eastBlock = Objects.requireNonNullElse(Registry.BLOCK
                .get(new Identifier(nbt.getString("EastBlockName"))), Blocks.AIR);
        this.southBlock = Objects.requireNonNullElse(Registry.BLOCK
                .get(new Identifier(nbt.getString("SouthBlockName"))), Blocks.AIR);
        this.westBlock = Objects.requireNonNullElse(Registry.BLOCK
                .get(new Identifier(nbt.getString("WestBlockName"))), Blocks.AIR);
        if (this.getCachedState().getBlock() instanceof SpecialWallBlock wall) {
            this.updateBlocksFromLevel(wall);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("NorthBlockName", Objects.requireNonNullElse(
                Registry.BLOCK.getId(this.northBlock), Registry.BLOCK.getId(Blocks.AIR)).toString());
        nbt.putString("EastBlockName", Objects.requireNonNullElse(
                Registry.BLOCK.getId(this.eastBlock), Registry.BLOCK.getId(Blocks.AIR)).toString());
        nbt.putString("SouthBlockName", Objects.requireNonNullElse(
                Registry.BLOCK.getId(this.southBlock), Registry.BLOCK.getId(Blocks.AIR)).toString());
        nbt.putString("WestBlockName", Objects.requireNonNullElse(
                Registry.BLOCK.getId(this.westBlock), Registry.BLOCK.getId(Blocks.AIR)).toString());

    }
}
