package org.ferroh.nMIS;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.ferroh.nMIS.constants.RecipeConstants;
import org.ferroh.nMIS.listeners.MannequinEquipListener;
import org.ferroh.nMIS.listeners.MannequinSoulUseListener;
import org.ferroh.nMIS.listeners.SoulCraftingPrepareListener;
import org.ferroh.nMIS.types.CommandState;
import org.ferroh.nMIS.types.gui.listeners.MannequinEquipCloseListener;
import org.ferroh.nMIS.types.gui.listeners.MannequinEquipDeadSlotListener;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Anchor;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.HealthBuff;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulStarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class NMIS extends JavaPlugin {
    private static JavaPlugin _plugin;

    private static HashMap<UUID, CommandState> _commandStateMap;
    private static List<UUID> _openMannequins;

    @Override
    public void onEnable() {
        // Plugin startup logic
        _plugin = this;

        initSoulRecipe();

        _commandStateMap = new HashMap<>();
        _openMannequins = new ArrayList<>();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new SoulCraftingPrepareListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinSoulUseListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipCloseListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipDeadSlotListener(), getPlugin());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin() {
        return _plugin;
    }

    public static CommandState getCommandStateForPlayer(UUID playerUUID) {
        if (_commandStateMap.containsKey(playerUUID)) {
            return _commandStateMap.get(playerUUID);
        }

        return null;
    }

    public static void setCommandStateForPlayer(UUID playerUUID, CommandState commandState) {
        _commandStateMap.put(playerUUID, commandState);
    }

    public static void clearCommandStateForPlayer(UUID playerUUID) {
        _commandStateMap.remove(playerUUID);
    }

    public static void markMannequinAsOpen(UUID mannequinID, boolean isOpen) {
        if (mannequinID == null) {
            throw new IllegalArgumentException("Mannequin ID cannot be null");
        }

        if (isOpen) {
            if (!_openMannequins.contains(mannequinID)) {
                _openMannequins.add(mannequinID);
            }
        } else {
            _openMannequins.remove(mannequinID);
        }
    }

    public static boolean isMannequinOpen(UUID mannequinID) {
        return _openMannequins.contains(mannequinID);
    }

    private void initSoulRecipe() {
        final List<Material> OPTIONAL_INGREDIENTS = List.of(
                new Skin("").getMaterial(),
                new HealthBuff().getMaterial(),
                new Anchor().getMaterial());

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
