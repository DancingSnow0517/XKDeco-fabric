package cn.dancingsnow.xkdeco.data;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;

import static net.minecraft.data.server.BlockLootTableGenerator.applyExplosionDecay;

public class ModBlockLootTableGenerator {

    private static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(net.minecraft.predicate.item.ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));

    public static LootTable.Builder dropSlabsWithSilkTouch(Block drop) {
        return LootTable.builder().pool(
                LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(applyExplosionDecay(drop, ItemEntry.builder(drop)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                        .conditionally(BlockStatePropertyLootCondition.builder(drop)
                                                .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))))))
                        .conditionally(WITH_SILK_TOUCH));

    }
}
