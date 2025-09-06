package org.ferroh.nMIS;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.ferroh.nMIS.constants.RecipeConstants;
import org.ferroh.nMIS.listeners.SoulCraftingPrepareListener;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.HealthBuff;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulStarter;

import java.util.List;

public final class NMIS extends JavaPlugin {
    private static JavaPlugin _plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        _plugin = this;

        initSoulRecipe();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new SoulCraftingPrepareListener(), getPlugin());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin() {
        return _plugin;
    }

    private void initSoulRecipe() {
        final List<Material> OPTIONAL_INGREDIENTS = List.of(
                new Skin("").getMaterial(),
                new HealthBuff().getMaterial());

        RecipeChoice.MaterialChoice soulStarterChoice = new RecipeChoice.MaterialChoice(new SoulStarter().getMaterial());
        RecipeChoice.MaterialChoice optionalSoulIngredientChoice = new RecipeChoice.MaterialChoice(OPTIONAL_INGREDIENTS);

        ItemStack dummyResult = new ItemStack(new MannequinSoul().getMaterial()); // Actual result is set later in SoulCraftingPrepareListener

        for (int i = 0; i <= OPTIONAL_INGREDIENTS.size(); i++) {
            NamespacedKey recipeKey = new NamespacedKey(NMIS.getPlugin(), RecipeConstants.SOUL_RECIPE_KEY_PREFIX + i);

            ShapelessRecipe soulRecipe = new ShapelessRecipe(recipeKey, dummyResult);
            soulRecipe.addIngredient(soulStarterChoice);

            for (int j = 0; j < i; j++) {
                soulRecipe.addIngredient(optionalSoulIngredientChoice);
            }

            Bukkit.addRecipe(soulRecipe);
        }
    }
}
