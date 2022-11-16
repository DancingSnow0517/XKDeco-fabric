package cn.dancingsnow.xkdeco.mixin;


import cn.dancingsnow.xkdeco.entity.CushionEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        var pos = getPos();
        var pos1 = new Vec3d(pos.x-0.5, pos.y-0.5, pos.z-0.5);
        var pos2 = new Vec3d(pos.x+0.5, pos.y+0.5, pos.z+0.5);
        var cushions = world.getEntitiesByClass(CushionEntity.class, new Box(pos1, pos2), EntityPredicates.EXCEPT_SPECTATOR);
        for (var cushion : cushions) {
            if (cushion.getPassengerList().isEmpty()) {
                cushion.remove(RemovalReason.DISCARDED);
            }
        }
    }

}
