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
import org.ferroh.nMIS.listeners.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static ArrayList<UUID> _openMannequins;

    /**
     * bStats plugin ID (for metrics)
     */
    private static final int BSTATS_PLUGIN_ID = 27362;

    // Earliest Minecraft version that this plugin can run
    private final int _EARLIEST_MAJOR_VERSION = 1;
    private final int _EARLIEST_MINOR_VERSION = 21;
    private final int _EARLIEST_PATCH_VERSION = 9;

    /**
     * Logic to run when this plugin is enabled
     */
    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!validateMinecraftVersion()) {
            throw new IllegalStateException("This plugin is for Minecraft Java 1.21.9 or greater. Disabling...");
        }

        _plugin = this;

        new Metrics(getPlugin(), BSTATS_PLUGIN_ID);

        initSoulRecipe();

        _commandStateMap = new HashMap<>();
        _openMannequins = new ArrayList<>();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new SoulCraftingPrepareListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinSoulUseListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinRightClickListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipCloseListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipClickDeadSlotListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new FetchPlayerProfileListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinEquipDeadSlotMoveListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new MannequinDeathListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new OpenMannequinDamageListener(), getPlugin());
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
     * Mark a particular mannequin as having its equipment GUI open
     * @param mannequinID UUID of the mannequin entity
     */
    public static void markMannequinAsOpen(UUID mannequinID) {
        if (mannequinID == null) {
            throw new IllegalArgumentException("Mannequin ID cannot be null");
        }

        if (_openMannequins.contains(mannequinID)) {
            throw new IllegalStateException("Plugin attempted to open a mannequin that is already open. Please report this to https://github.com/mstock98/npc-mannequins-in-survival/issues");
        }

        _openMannequins.add(mannequinID);
    }

    /**
     * Mark a particular mannequin as having its equipment GUI closed
     * @param mannequinID UUID of the mannequin entity
     */
    public static void markMannequinAsClosed(UUID mannequinID) {
        if (mannequinID == null) {
            throw new IllegalArgumentException("Mannequin ID cannot be null");
        }

        if (!_openMannequins.contains(mannequinID)) {
            throw new IllegalStateException("Plugin attempted to close a mannequin that is already closed. Please report this to https://github.com/mstock98/npc-mannequins-in-survival/issues");
        }

        _openMannequins.remove(mannequinID);
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
     * Determine whether the Minecraft version the server is running is high enough for this plugin to work
     * @return True if the Minecraft server version is high enough
     */
    private boolean validateMinecraftVersion() {
        String rawVersion = Bukkit.getServer().getVersion();

        String versionRegex = "^(\\d+)\\.(\\d+)(?:\\.(\\d+))?";
        Pattern versionPattern = Pattern.compile(versionRegex);
        Matcher versionMatcher = versionPattern.matcher(rawVersion);

        if (!versionMatcher.find()) {
            return false;
        }

        int majorVersion = Integer.parseInt(versionMatcher.group(1));
        int minorVersion = Integer.parseInt(versionMatcher.group(2));
        int patchVersion = versionMatcher.group(3) != null ? Integer.parseInt(versionMatcher.group(3)) : 0;

        if (majorVersion > _EARLIEST_MAJOR_VERSION) {
            return true;
        }

        if (minorVersion > _EARLIEST_MINOR_VERSION) {
            return true;
        } else if (minorVersion < _EARLIEST_MINOR_VERSION) {
            return false;
        }

        return patchVersion >= _EARLIEST_PATCH_VERSION;
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
