package cn.dancingsnow.xkdeco.utils;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;


public class MathUtil {
    public static final double TAU = Math.PI * 2;

    public static boolean containsInclusive(Box boundingBox, Vec3d vec) {
        return containsInclusive(boundingBox, vec.x, vec.y, vec.z);
    }

    public static boolean containsInclusive(Box boundingBox, double x, double y, double z) {
        return x >= boundingBox.minX && x <= boundingBox.maxX
                && y >= boundingBox.minY && y <= boundingBox.maxY
                && z >= boundingBox.minZ && z <= boundingBox.maxZ;
    }
}
