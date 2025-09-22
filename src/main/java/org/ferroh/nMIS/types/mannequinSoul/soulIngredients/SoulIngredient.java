package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract class representing shared functionality for all soul ingredients
 */
public abstract class SoulIngredient {
    /**
     * Get the material that a particular type of soul ingredient should have
     * @return Material for soul ingredient
     */
    public abstract Material getMaterial();

    /**
     * Get the item stack representing a soul ingredient
     * @return ItemStack for soul ingredient
     */
    public abstract ItemStack toItemStack();

    /**
     * Static helper method to determine if a given ItemStack represents a soul ingredient
     * @param potentialIngredient ItemStack to test for soul ingredient membership
     * @return True if the given ItemStack represents a soul ingredient
     */
    public static boolean itemStackIsIngredient(ItemStack potentialIngredient) {
        if (potentialIngredient == null) {
            return false;
        }

        try {
            new SoulStarter(potentialIngredient);
            return true;
        } catch (IllegalArgumentException ignored) {}

        try {
            new Skin(potentialIngredient);
            return true;
        } catch (IllegalArgumentException ignored) {}

        try {
            new HealthBuff(potentialIngredient);
            return true;
        } catch (IllegalArgumentException ignored) {}

        try {
            new Anchor(potentialIngredient);
            return true;
        } catch (IllegalArgumentException ignored) {}

        return false;
    }

    /**
     * Determine if a given ItemStack matches the material of this soul ingredient
     * @param itemStack ItemStack to test
     * @return True if the ItemStack has the correct material of this soul ingredient
     */
    protected boolean matchesIngredientMaterial(ItemStack itemStack) {
        return itemStack.getType().equals(getMaterial());
    }
}
