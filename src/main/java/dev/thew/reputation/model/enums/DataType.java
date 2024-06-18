package dev.thew.reputation.model.enums;

import lombok.SneakyThrows;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Field;
import java.util.Map;

public enum DataType {
    TAG {
        @SneakyThrows
        public Tag<?> generate(String rawData) {

            Field field = Tag.class.getDeclaredField(rawData);
            field.setAccessible(true);

            return (Tag<?>) field.get(null);
        }

        public <T, Z> boolean check(T need, Z has) {

            if (!(has instanceof Material material)) return false;
            return ((Tag<?>) need).getValues().contains(material);
        }
    },
    MATERIAL {
        public Material generate(String rawData) {
            return Material.valueOf(rawData);
        }

        public <T, Z> boolean check(T need, Z has) {

            if (!(has instanceof Material material)) return false;
            return material.equals(need);
        }
    },
    ENTITY_TYPE {
        public EntityType generate(String rawData) {
            return EntityType.valueOf(rawData);
        }

        public <T, Z> boolean check(T need, Z has) {

            if (!(has instanceof Entity entity)) return false;
            return entity.getType().equals(need);
        }
    },
    ENCHANTMENT {
        public Enchantment generate(String rawData) {
            return Enchantment.getByKey(NamespacedKey.fromString(rawData));
        }

        public <T, Z> boolean check(T need, Z has) {

            if (!(has instanceof ItemStack itemStack)) return false;
            if (!(itemStack.getItemMeta() instanceof EnchantmentStorageMeta enchantmentStorageMeta)) return false;

            for (Map.Entry<Enchantment, Integer> enchantment : enchantmentStorageMeta.getStoredEnchants().entrySet()) {
                if (need.equals(enchantment.getKey().getKey().getKey() + "_" + enchantment.getValue())) return true;
            }

            return false;
        }
    },

    POTION_TYPE {
        public PotionType generate(String rawData) {
            return PotionType.valueOf(rawData);
        }

        public <T, Z> boolean check(T need, Z has) {

            if (!(has instanceof ItemStack itemStack)) return false;
            if (!(itemStack.getItemMeta() instanceof PotionMeta potionMeta)) return false;

            PotionData potionData = potionMeta.getBasePotionData();

            PotionType potionType = potionData.getType();
            boolean isUpgraded = potionData.isUpgraded();
            boolean isExtended = potionData.isExtended();

            assert potionType.getEffectType() != null;
            return need.equals(potionType.getEffectType().getName() + "_" + (isUpgraded ? "*_" : "") + (isExtended ? "+" : ""));
        }
    };

    @SneakyThrows
    public abstract Object generate(String rawData);

    public abstract <T, Z> boolean check(T need, Z has);
}
