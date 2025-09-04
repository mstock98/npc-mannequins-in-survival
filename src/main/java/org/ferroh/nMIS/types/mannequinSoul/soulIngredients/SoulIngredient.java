package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class SoulIngredient {
    public abstract Material getMaterial();

    public abstract ItemStack toItemStack();

    public static boolean itemStackIsIngredient(ItemStack potentialIngredient) {
        if (potentialIngredient == null || potentialIngredient.getType().equals(Material.AIR)) {
            return false;
        }

        try {
            new SoulStarter(potentialIngredient);
            return true;
        } catch (IllegalArgumentException e) {}

        try {
            new Skin(potentialIngredient);
            return true;
        } catch (IllegalArgumentException e) {}

        return false;
    }

    protected boolean matchesIngredientMaterial(ItemStack itemStack) {
        return itemStack.getType().equals(getMaterial());
    }
}
