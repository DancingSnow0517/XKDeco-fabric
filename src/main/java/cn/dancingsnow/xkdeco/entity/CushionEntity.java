package cn.dancingsnow.xkdeco.entity;

import cn.dancingsnow.xkdeco.XKDeco;
import cn.dancingsnow.xkdeco.setup.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static cn.dancingsnow.xkdeco.setup.ModBlocks.CHAIR_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.STOOL_SUFFIX;

public class CushionEntity extends Entity {

    private static final TrackedDataHandler<Vec3d> LOCATION_DATA_SERIALIZER = new Vec3dSerializer();
    private static final TrackedData<Vec3d> DATA_DIFF_LOCATION = DataTracker.registerData(CushionEntity.class, LOCATION_DATA_SERIALIZER);
    static final double MAX_DISTANCE = 3.0;

    static {
        TrackedDataHandlerRegistry.register(LOCATION_DATA_SERIALIZER);
    }

    public CushionEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public CushionEntity(BlockPos pos, PlayerEntity player) {
        super(ModEntities.CUSHION_ENTITY, player.getWorld());
        this.noClip = true;
        this.setPos(pos.getX() + 0.5, pos.getY() + 1.075, pos.getZ() + 0.5);
        this.setStandingDiffLocation(this.calculateStandingDiff(player));
        player.setPosition(this.getPos());
        player.startRiding(this);
    }

    private Vec3d calculateStandingDiff(Entity entity) {
        if (!entity.hasVehicle()) {
            var diff = entity.getPos().add(0.0, 0.5, 0.0).subtract(this.getPos());
            return diff.lengthSquared() > MAX_DISTANCE ? diff.normalize().multiply(Math.sqrt(MAX_DISTANCE)) : diff;
        }
        return Vec3d.ZERO;
    }

    public Vec3d getStandingDiffLocation() {
        return this.dataTracker.get(DATA_DIFF_LOCATION);
    }

    public void setStandingDiffLocation(Vec3d value) {
        this.dataTracker.set(DATA_DIFF_LOCATION, value);
    }


    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(DATA_DIFF_LOCATION, Vec3d.ZERO);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        var standingDiffX = nbt.getDouble("StandingDiffX");
        var standingDiffY = nbt.getDouble("StandingDiffY");
        var standingDiffZ = nbt.getDouble("StandingDiffZ");
        this.setStandingDiffLocation(new Vec3d(standingDiffX, standingDiffY, standingDiffZ));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        var standingDiff = this.getStandingDiffLocation();
        nbt.putDouble("StandingDiffX", standingDiff.x);
        nbt.putDouble("StandingDiffY", standingDiff.y);
        nbt.putDouble("StandingDiffZ", standingDiff.z);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        var targetPosition = this.getPos().add(this.getStandingDiffLocation());
        var targetBelow = new BlockPos(targetPosition.x, targetPosition.y - 1.0, targetPosition.z);
        var canStand = passenger.getWorld().getBlockState(targetBelow).isSideSolidFullSquare(passenger.getWorld(), targetBelow, Direction.UP);
        return canStand ? targetPosition : targetPosition.add(0.0, 1.0, 0.0);
    }

    private static boolean canBlockBeSeated(BlockState state) {
        var name = Registry.BLOCK.getId(state.getBlock());
        if (XKDeco.MOD_ID.equals(name.getNamespace())) {
            var id = name.getPath();
            return id.contains(CHAIR_SUFFIX) || id.contains(STOOL_SUFFIX);
        }
        return false;
    }

    @SuppressWarnings("DuplicatedCode")
    public static ActionResult onRightClickBlock(PlayerEntity player, World world, Hand hand, HitResult hitResult) {
        if (!world.isClient) {
            var pos = hitResult.getPos();
            if (!player.isSneaking()) {
                var x = pos.getX();
                var y = pos.getY();
                var z = pos.getZ();
                var cushions = world.getEntitiesByClass(CushionEntity.class,
                        new Box(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1),
                        EntityPredicates.EXCEPT_SPECTATOR);
                if (cushions.isEmpty() && canBlockBeSeated(world.getBlockState(new BlockPos(pos)))) {
                    world.spawnEntity(new CushionEntity(new BlockPos(pos), player));
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

    private static final class Vec3dSerializer implements TrackedDataHandler<Vec3d> {

        @Override
        public void write(PacketByteBuf buf, Vec3d value) {
            buf.writeDouble(value.x).writeDouble(value.y).writeDouble(value.z);
        }

        @Override
        public Vec3d read(PacketByteBuf buf) {
            return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        }

        @Override
        public Vec3d copy(Vec3d value) {
            return value;
        }
    }
}
