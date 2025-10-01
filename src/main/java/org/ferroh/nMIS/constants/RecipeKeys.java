package org.ferroh.nMIS.constants;

import org.bukkit.NamespacedKey;
import org.ferroh.nMIS.NMIS;

/**
 * Keys/key prefixes for registering custom recipes
 */
public class RecipeKeys {
    /**
     * Key prefix for mannequin soul recipe
     */
    private static final String _SOUL_RECIPE_KEY_PREFIX = "mannequin_soul";

    /**
     * Get a NamespacedKey for a mannequin soul recipe based on the number of optional ingredients
     * @param numberOfOptionalIngredients Number of optional ingredients in the mannequin soul recipe
     * @return NamespacedKey for the number of optional ingredients
     */
    public static NamespacedKey getSoulRecipeKey(int numberOfOptionalIngredients) {
        if (numberOfOptionalIngredients < 0 || numberOfOptionalIngredients > 8) {
            throw new IllegalArgumentException("Number of optional ingredients must be between 0 and 8");
        }

        return new NamespacedKey(NMIS.getPlugin(), _SOUL_RECIPE_KEY_PREFIX + numberOfOptionalIngredients);
    }

    /**
     * Determine if a recipe key is for a mannequin soul recipe
     * @param recipeKey Recipe key to check
     * @return True if the recipe key belongs to a mannequin soul recipe
     */
    public static boolean isSoulRecipeKey(NamespacedKey recipeKey) {
        if (recipeKey == null) {
            return false;
        }

        if (!(recipeKey.getNamespace().equals(NMIS.getPlugin().namespace()))) {
            return false;
        }

        return recipeKey.getKey().startsWith(_SOUL_RECIPE_KEY_PREFIX);
    }
}
