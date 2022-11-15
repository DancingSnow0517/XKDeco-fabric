package cn.dancingsnow.xkdeco.utils;

import cn.dancingsnow.xkdeco.block.IsotropicRoofBlock;
import cn.dancingsnow.xkdeco.block.IsotropicRoofEaveBlock;
import cn.dancingsnow.xkdeco.block.IsotropicRoofFlatBlock;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Locale;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class RoofUtil {
    /*** Test Roof Type ***/
    public static boolean isRoof(BlockState state) {
        return state.getBlock() instanceof IsotropicRoofBlock;
    }

    public static boolean isFlatRoof(BlockState state) {
        return state.getBlock() instanceof IsotropicRoofFlatBlock;
    }

    public static boolean isEave(BlockState state) {
        return state.getBlock() instanceof IsotropicRoofEaveBlock;
    }

    /*** Test Open Shape ***/
    public static boolean isOpenSide(RoofShape shape, Rotation actual, Rotation expected) {
        return switch (shape) {
            case STRAIGHT -> actual == expected;
            case INNER -> actual == expected || actual.getClockWise() == expected;
            case OUTER -> false;
        };
    }

    public static boolean isHalfOpenClockWiseSide(RoofShape shape, Rotation actual, Rotation expected) {
        return switch (shape) {
            case STRAIGHT, INNER -> actual.getCounterClockWise() == expected;
            case OUTER -> actual == expected;
        };
    }

    public static boolean isHalfOpenCounterClockWiseSide(RoofShape shape, Rotation actual, Rotation expected) {
        return switch (shape) {
            case STRAIGHT, OUTER -> actual.getClockWise() == expected;
            case INNER -> actual.get2DOpposite() == expected;
        };
    }

    public static boolean isClosedSide(RoofShape shape, Rotation actual, Rotation expected) {
        return switch (shape) {
            case STRAIGHT -> actual.get2DOpposite() == expected;
            case INNER -> false;
            case OUTER -> actual.getCounterClockWise() == expected || actual.get2DOpposite() == expected;
        };
    }

    public interface PlacementCheckerModifier extends BlockStateVariantMap.QuadFunction<
                    // placement Level, placement BlockPos, placement Direction, initial BlockState
                    World, BlockPos, Direction, BlockState,
                    // modified BlockState, if passed the checker, otherwise is empty
                    Optional<BlockState>> {
    }

    /*** Test Neighbor Roof State ***/
    public static PlacementCheckerModifier tryConnectTo(
            Rotation target,
            QuadPredicate<Rotation, RoofShape, RoofHalf, RoofVariant> rotHalfVariantPredicate,
            BinaryOperator<BlockState> thenSet) {

        return (level, placePos, placeDirection, initialState) -> {
            var targetPosDirection = target.rotate(placeDirection);
            var targetPos = placePos.offset(targetPosDirection);
            var targetState = level.getBlockState(targetPos);

            if (isRoof(targetState) && rotHalfVariantPredicate.test(
                    Rotation.fromDirections(placeDirection, targetState.get(IsotropicRoofBlock.FACING)),
                    targetState.get(IsotropicRoofBlock.SHAPE),
                    targetState.get(IsotropicRoofBlock.HALF),
                    targetState.get(IsotropicRoofBlock.VARIANT))
            ) {
                return Optional.of(thenSet.apply(initialState, targetState));
            } else {
                return Optional.empty();
            }
        };
    }

    public static PlacementCheckerModifier tryConnectToFlat(
            Rotation target,
            BiPredicate<Boolean, RoofHalf> parallelHalfPredicate,
            BinaryOperator<BlockState> thenSet) {

        return (level, placePos, placeDirection, initialState) -> {
            var targetPosDirection = target.rotate(placeDirection);
            var targetPos = placePos.offset(targetPosDirection);
            var targetState = level.getBlockState(targetPos);

            if (isFlatRoof(targetState) && parallelHalfPredicate.test(
                    targetState.get(IsotropicRoofFlatBlock.AXIS) == placeDirection.getAxis(),
                    targetState.get(IsotropicRoofFlatBlock.HALF))
            ) {
                return Optional.of(thenSet.apply(initialState, targetState));
            } else {
                return Optional.empty();
            }
        };
    }

    public static PlacementCheckerModifier tryConnectToEave(
            Rotation target,
            TriPredicate<Rotation, RoofShape, RoofHalf> rotHalfPredicate,
            BinaryOperator<BlockState> thenSet) {

        return (level, placePos, placeDirection, initialState) -> {
            var targetPosDirection = target.rotate(placeDirection);
            var targetPos = placePos.offset(targetPosDirection);
            var targetState = level.getBlockState(targetPos);

            if (isEave(targetState) && rotHalfPredicate.test(
                    Rotation.fromDirections(placeDirection, targetState.get(IsotropicRoofEaveBlock.FACING)),
                    targetState.get(IsotropicRoofEaveBlock.SHAPE),
                    targetState.get(IsotropicRoofEaveBlock.HALF))
            ) {
                return Optional.of(thenSet.apply(initialState, targetState));
            } else {
                return Optional.empty();
            }
        };
    }

    public static RoofHalf getPlacementHalf(ItemPlacementContext ctx) {
        return switch (ctx.getSide()) {
            case UP -> RoofHalf.TIP;
            case DOWN -> RoofHalf.BASE;
            case EAST, WEST, NORTH, SOUTH ->
                    ctx.getHitPos().y - Math.floor(ctx.getHitPos().y) > 0.5
                            ? RoofHalf.BASE : RoofHalf.TIP;
        };
    }

    public enum Rotation {
        LEFT(Direction::rotateYCounterclockwise),
        FRONT(UnaryOperator.identity()),
        RIGHT(Direction::rotateYClockwise),
        BACK(Direction::getOpposite),
        UP(d -> Direction.UP),
        DOWN(direction -> Direction.DOWN);

        private final UnaryOperator<Direction> rotator;

        Rotation(UnaryOperator<Direction> rotator) {
            this.rotator = rotator;
        }

        public Direction rotate(Direction direction) {
            return this.rotator.apply(direction);
        }

        public Rotation getClockWise() {
            return switch (this) {
                case LEFT -> FRONT;
                case FRONT -> RIGHT;
                case RIGHT -> BACK;
                case BACK -> LEFT;
                case UP, DOWN -> this;
            };
        }

        public Rotation getCounterClockWise() {
            return switch (this) {
                case LEFT -> BACK;
                case FRONT -> LEFT;
                case RIGHT -> FRONT;
                case BACK -> RIGHT;
                case UP, DOWN -> this;
            };
        }

        public Rotation get2DOpposite() {
            return switch (this) {
                case LEFT -> RIGHT;
                case FRONT -> BACK;
                case RIGHT -> LEFT;
                case BACK -> FRONT;
                case UP, DOWN -> this;
            };
        }

        public static Rotation fromDirections(Direction start, Direction end) {
            for (var r : Rotation.values()) if (end == r.rotate(start)) return r;
            throw new IllegalStateException("wtf why none of the rotations matches");
        }
    }

    public enum RoofHalf implements StringIdentifiable {
        BASE, TIP;

        public RoofHalf otherHalf() {
            return this == BASE ? TIP : BASE;
        }

        @Override
        public String asString() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum RoofShape implements StringIdentifiable {
        STRAIGHT, INNER, OUTER;

        @Override
        public String asString() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum RoofVariant implements StringIdentifiable {
        NORMAL, SLOW, STEEP;

        @Override
        public String asString() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
