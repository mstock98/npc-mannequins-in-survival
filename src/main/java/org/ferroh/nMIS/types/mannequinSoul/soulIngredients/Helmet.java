package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Helmet extends SoulIngredient {
    private ItemStack _itemStack = null;

    public Helmet(ItemStack helmetItemStack) {
        if (helmetItemStack == null) {
            throw new IllegalArgumentException("Helmet ItemStack cannot be null");
        }

        if (!materialIsHelmet(helmetItemStack.getType())) {
            throw new IllegalArgumentException("ItemStack is not a helmet");
        }

        _itemStack = helmetItemStack;
    }

    @Override
    public Material getMaterial() {
        if (_itemStack == null) {
            throw new IllegalStateException("Could not get ItemStack for Helmet SoulIngredient");
        }

        return _itemStack.getType();
    }

    @Override
    public ItemStack toItemStack() {
        return _itemStack;
    }

    private boolean materialIsHelmet(Material material) {
        switch (material) {
            case LEATHER_HELMET, CHAINMAIL_HELMET, IRON_HELMET, GOLDEN_HELMET, DIAMOND_HELMET, NETHERITE_HELMET -> {
                return true;
            }
        }

        return false;
    }
}
