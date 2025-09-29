package org.ferroh.nMIS;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.ferroh.nMIS.constants.RecipeConstants;
import org.ferroh.nMIS.listeners.FetchPlayerProfileListener;
import org.ferroh.nMIS.listeners.MannequinEquipListener;
import org.ferroh.nMIS.listeners.MannequinSoulUseListener;
import org.ferroh.nMIS.listeners.SoulCraftingPrepareListener;
import org.ferroh.nMIS.types.CommandState;
import org.ferroh.nMIS.types.gui.listeners.MannequinEquipCloseListener;
import org.ferroh.nMIS.types.gui.listeners.MannequinEquipClickDeadSlotListener;
import org.ferroh.nMIS.types.gui.listeners.MannequinEquipDeadSlotMoveListener;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Anchor;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.HealthBuff;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulStarter;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Map;

/**
 * Main plugin class for NPC Mannequins in Survival
 */
public final class NMIS extends JavaPlugin {
    /**
     * Plugin instance
     */
    private static JavaPlugin _plugin;

    /**
     * Current command status for all players
     */
    private static HashMap<UUID, CommandState> _commandStateMap;

    /**
     * Map of all mannequins that have equipment GUIs open
     */
    private static List<UUID> _openMannequins;

    /**
     * bStats plugin ID (for metrics)
     */
    private static final int BSTATS_PLUGIN_ID = 27362;

    /**
     * Logic to run when this plugin is enabled
     */
    @Override
    public void onEnable() {
        // Plugin startup logic
        _plugin = this;

        Metrics metrics = new Metrics(getPlugin(), BSTATS_PLUGIN_ID);

        initSoulRecipe();

        _commandStateMap = new HashMap<>();
        _openMannequins = new ArrayList<>();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new SoulCraftingPrepareListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinSoulUseListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipCloseListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipClickDeadSlotListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new FetchPlayerProfileListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipDeadSlotMoveListener(), getPlugin());
    }

    /**
     * Logic to run when the plugin is disabled
     */
    @Override
    public void onDisable() {
        for (Map.Entry<UUID, CommandState> commandStateEntry : _commandStateMap.entrySet()) {
            MannequinEquipCloseListener.resolveMannequinOpenCommandState(commandStateEntry.getValue(), commandStateEntry.getKey());
        }
    }

    /**
     * Get the plugin instance
     * @return NMIS plugin object
     */
    public static JavaPlugin getPlugin() {
        return _plugin;
    }

    /**
     * Get a player's current command state
     * @param playerUUID Player ID
     * @return Command state for player, null if there isn't any
     */
    public static CommandState getCommandStateForPlayer(UUID playerUUID) {
        if (_commandStateMap.containsKey(playerUUID)) {
            return _commandStateMap.get(playerUUID);
        }

        return null;
    }

    /**
     * Set the command state for a particular player
     * @param playerUUID Player ID to set command state for
     * @param commandState Command state that the player should have
     */
    public static void setCommandStateForPlayer(UUID playerUUID, CommandState commandState) {
        _commandStateMap.put(playerUUID, commandState);
    }

    /**
     * Set the command state to null for a particular player
     * @param playerUUID Player ID to clear command state for
     */
    public static void clearCommandStateForPlayer(UUID playerUUID) {
        _commandStateMap.remove(playerUUID);
    }

    /**
     * Mark a particular mannequin as having their equipment GUI open/closed
     * @param mannequinID UUID of the mannequin entity
     * @param isOpen True to mark the mannequin as open, false to mark the mannequin as closed
     */
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

    /**
     * Determine whether a mannequin entity has their equipment GUI opened by a player
     * @param mannequinID UUID of the mannequin entity
     * @return True if the mannequin has its equipment GUI open
     */
    public static boolean isMannequinOpen(UUID mannequinID) {
        return _openMannequins.contains(mannequinID);
    }

    /**
     * Initialize the crafting recipe for a mannequin sour
     */
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
