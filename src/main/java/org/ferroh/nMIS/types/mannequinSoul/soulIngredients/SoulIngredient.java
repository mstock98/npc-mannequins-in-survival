package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class SoulIngredient {
    public abstract Material getMaterial();

    public abstract ItemStack toItemStack();

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

        return false;
    }

    protected boolean matchesIngredientMaterial(ItemStack itemStack) {
        return itemStack.getType().equals(getMaterial());
    }
}
