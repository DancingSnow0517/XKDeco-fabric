package cn.dancingsnow.xkdeco.blockentity;

import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import cn.dancingsnow.xkdeco.setup.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ItemDisplayBlockEntity extends BlockEntity implements Clearable {
    public static final String ITEMSTACK_NBT_KEY = "Display";
    private static final String SPIN_NBT_KEY = "Spin";

    private final boolean isProjector;
    private ItemStack item = ItemStack.EMPTY;
    private float spin = 0;

    public ItemDisplayBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, blockPos, blockState);
        this.isProjector = blockState.isOf(ModBlocks.ITEM_PROJECTOR.getLeft());
    }

    public Box getRenderBoundingBox() {
        if (isProjector) {
            return Box.of(Vec3d.ofBottomCenter(this.getPos().up(9)), 16, 16, 16);
        } else {
            return Box.from(Vec3d.of(this.getPos().up()));
        }
    }

    public boolean isProjector() {
        return isProjector;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        var update = !item.isEmpty() || !this.item.isEmpty();
        this.item = item;
        if (update) {
            this.markDirty();
            var blockState = this.getCachedState();
            Objects.requireNonNull(this.world).updateListeners(this.getPos(), blockState, blockState, 0);
        }
    }

    public float getSpin() {
        return spin;
    }

    public void setSpin(float spin) {
        this.spin = spin;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this,
                be -> ((ItemDisplayBlockEntity) be).writeNbtPacket(null));
    }

    public void onDataPacket(ClientConnection net, BlockEntityUpdateS2CPacket pkt) {
        readNbt(pkt.getNbt());
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return writeNbtPacket(null);
    }

    public void handleUpdateTag(NbtCompound tag) {
        readNbt(tag);
    }

    // load
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        readNbtPacket(nbt);
    }

    // saveAdditional
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        writeNbtPacket(nbt);
    }

    @Override
    public void clear() {
        this.setItem(ItemStack.EMPTY);
    }

    private void readNbtPacket(@Nullable NbtCompound tag) {
        if (tag == null) return;
        if (tag.contains(ITEMSTACK_NBT_KEY)) {
            this.item = ItemStack.fromNbt(tag.getCompound(ITEMSTACK_NBT_KEY));
        }
        if (tag.contains(SPIN_NBT_KEY)) {
            this.spin = tag.getFloat(SPIN_NBT_KEY);
        }
    }

    private NbtCompound writeNbtPacket(@Nullable NbtCompound tag) {
        if (tag == null) tag = new NbtCompound();
        tag.put(ITEMSTACK_NBT_KEY, item.writeNbt(new NbtCompound()));
        tag.putFloat(SPIN_NBT_KEY, spin);
        return tag;
    }
}
