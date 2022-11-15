package cn.dancingsnow.xkdeco.blockentities;

import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.state.property.Property;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class BlockDisplayBlockEntity extends BlockEntity implements Clearable {

    public static final String ITEMSTACK_NBT_KEY = "Display";
    private static final String BLOCKSTATE_NBT_KEY = "State";
    private static final String SELECTED_PROPERTY_NBT_KEY = "Selected";
    private static final BlockState EMPTY = Blocks.AIR.getDefaultState();

    @NotNull
    private ItemStack item = ItemStack.EMPTY;
    @NotNull
    private BlockState blockState = EMPTY;
    @Nullable
    private Property<?> selectedProperty = null;

    public BlockDisplayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLOCK_DISPLAY_BLOCK_ENTITY, pos, state);
    }

    public Box getRenderBoundingBox() {
        return Box.from(Vec3d.of(this.getPos().up()));
    }

    public @NotNull ItemStack getItem() {
        return item;
    }

    public void setItem(@NotNull ItemStack itemStack) {
        this.item = itemStack;
        var i = itemStack.getItem();
        if (i instanceof BlockItem blockItem) {
            blockState = blockItem.getBlock().getDefaultState();
        } else {
            blockState = EMPTY;
        }
        var properties = blockState.getProperties();
        selectedProperty = properties.isEmpty() ? null : properties.iterator().next();
        var blockState = this.getCachedState();
        this.markDirty();
        Objects.requireNonNull(this.world).updateListeners(this.getPos(), blockState, blockState, 0);
    }

    @Override
    public BlockState getCachedState() {
        return blockState;
    }

    public void setStoredBlockState(@NotNull BlockState blockState) {
        this.blockState = blockState;
        this.markDirty();
        Objects.requireNonNull(this.world).updateListeners(this.getPos(), blockState, blockState, 0);
    }

    public Optional<Property<?>> getSelectedProperty() {
        return Optional.ofNullable(selectedProperty);
    }

    public void setSelectedProperty(@Nullable Property<?> selectedProperty) {
        this.selectedProperty = selectedProperty;
        this.markDirty();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this,
                be -> ((BlockDisplayBlockEntity) be).writeNbtPacket(null));
    }


    // load
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains(ITEMSTACK_NBT_KEY)) {
            this.item = ItemStack.fromNbt(nbt.getCompound(ITEMSTACK_NBT_KEY));
        }
        if (nbt.contains(BLOCKSTATE_NBT_KEY)) {
            this.blockState = NbtHelper.toBlockState(nbt.getCompound(BLOCKSTATE_NBT_KEY));
        }
        if (nbt.contains(SELECTED_PROPERTY_NBT_KEY)) {
            var propertyName = nbt.getString(SELECTED_PROPERTY_NBT_KEY);
            this.selectedProperty = this.blockState.getProperties()
                    .stream().filter(p -> p.getName().equals(propertyName)).findFirst()
                    .orElse(blockState.getProperties().stream().findFirst().orElse(null));
        }
    }

    // saveAdditional
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put(ITEMSTACK_NBT_KEY, item.writeNbt(new NbtCompound()));
        nbt.put(BLOCKSTATE_NBT_KEY, NbtHelper.fromBlockState(blockState));
        nbt.putString(SELECTED_PROPERTY_NBT_KEY, selectedProperty == null ? "" : selectedProperty.getName());
    }

    public void handleUpdateTag(NbtCompound tag) {
        readNbtPacket(tag);
    }

    private void readNbtPacket(@Nullable NbtCompound tag) {
        if (tag == null) return;
        if (tag.contains(BLOCKSTATE_NBT_KEY)) {
            this.blockState = NbtHelper.toBlockState(tag.getCompound(BLOCKSTATE_NBT_KEY));
        }
    }

    private NbtCompound writeNbtPacket(@Nullable NbtCompound tag) {
        if (tag == null) tag = new NbtCompound();
        tag.put(BLOCKSTATE_NBT_KEY, NbtHelper.fromBlockState(blockState));
        return tag;
    }

    @Override
    public void clear() {
        this.setItem(ItemStack.EMPTY);
    }
}
