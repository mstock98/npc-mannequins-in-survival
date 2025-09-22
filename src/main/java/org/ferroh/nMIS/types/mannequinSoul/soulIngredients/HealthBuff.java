package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Soul ingredient class for the HealthBuff
 * Mannequins with the HealthBuff will have increased health
 */
public class HealthBuff extends SoulIngredient {
    /**
     * ItemStack representing this soul ingredient
     */
    private ItemStack _itemStack = null;

    /**
     * Create a new HealthBuff not based on any ItemStack
     */
    public HealthBuff() {}

    /**
     * Create a new HealthBuff based on an ItemStack.
     * Throws an IllegalArgumentException of the provided ItemStack does not represent a HealthBuff.
     * @param itemStack ItemStack representing a HealthBuff soul ingredient
     */
    public HealthBuff(ItemStack itemStack) {
        if (itemStack == null) {
            throw new IllegalArgumentException("ItemStack is null");
        }

        if (!matchesIngredientMaterial(itemStack)) {
            throw new IllegalArgumentException("Wrong material");
        }

        _itemStack = itemStack;
    }

    /**
     * Return the material that represents this soul ingredient
     * @return Material for the soul ingredient
     */
    @Override
    public Material getMaterial() {
        return Material.NETHERITE_SCRAP;
    }

    /**
     * Convert this soul ingredient into an ItemStack that represents it
     * @return ItemStack that represents this soul ingredient
     */
    @Override
    public ItemStack toItemStack() {
        if (_itemStack == null) {
            _itemStack = new ItemStack(getMaterial());
        }

        return _itemStack;
    }
}
