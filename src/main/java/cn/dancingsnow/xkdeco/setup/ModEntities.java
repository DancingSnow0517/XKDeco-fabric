package cn.dancingsnow.xkdeco.setup;

import cn.dancingsnow.xkdeco.XKDeco;
import cn.dancingsnow.xkdeco.entity.CushionEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    public static final EntityType<Entity> CUSHION_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(XKDeco.MOD_ID, "cushion"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, CushionEntity::new).build());

    public static void registry() {

    }

}
