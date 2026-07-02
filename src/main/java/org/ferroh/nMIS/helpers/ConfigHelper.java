package org.ferroh.nMIS.helpers;

import org.bukkit.configuration.Configuration;
import org.ferroh.nMIS.NMIS;

/**
 * Class containing some static helper methods for accessing the plugin config
 */
public class ConfigHelper {
    private static boolean _bStatsOptOut;
    private static boolean _displayNPCTag;

    /**
     * Cache values into RAM from config.yml. Call this on plugin load
     */
    public static void init() {
        NMIS.getPlugin().saveDefaultConfig();

        _bStatsOptOut = getBoolean("bstats-opt-out");
        _displayNPCTag = getBoolean("display-npc-tag");
    }

    /**
     * Whether bStats is enabled in config.yml
     * @return True is bStats is enabled
     */
    public static boolean isbStatsEnabled() {
        return !_bStatsOptOut;
    }

    /**
     * Whether displaying the NPC tag in NMIS mannequin nameplates is enabled in config.yml
     * @return True if the NPC tag should be displayed in NMIS mannequin nameplates
     */
    public static boolean displayNPCTag() {
        return _displayNPCTag;
    }

    /**
     * Get a boolean value from config.yml
     * @param configKey config.yml path leading to the boolean value to retrieve
     * @return Boolean from config.yml, boolean from the default config.yml if the first value is missing or malformed
     */
    private static boolean getBoolean(String configKey) {
        boolean isValueSet = NMIS.getPlugin().getConfig().isSet(configKey);
        boolean isValueBoolean = NMIS.getPlugin().getConfig().isBoolean(configKey);

        return (isValueSet && isValueBoolean) ? NMIS.getPlugin().getConfig().getBoolean(configKey) : getBooleanDefault(configKey);
    }

    /**
     * Get the default boolean value for a given configuration key
     * @param configKey config.yml path to get the default value for
     * @return Default value of the config key
     * @throws IllegalStateException If a default for the config key doesn't exist
     */
    private static boolean getBooleanDefault(String configKey) {
        Configuration defaultConfig = getDefaultConfiguration();

        if (!defaultConfig.isSet(configKey)) {
            throw new IllegalStateException("Missing default config.yml value for " + configKey + ". Please make an issue on https://github.com/mstock98/npc-mannequins-in-survival/issues if one doesn't exist.");
        }

        return defaultConfig.getBoolean(configKey);
    }

    /**
     * Get the object for default config.yml values
     * @return Default config.yml values
     * @throws IllegalStateException If the object for default config.yml values doesn't exist
     */
    private static Configuration getDefaultConfiguration() {
        Configuration defaultConfig = NMIS.getPlugin().getConfig().getDefaults();

        if (defaultConfig == null) {
            throw new IllegalStateException("Missing default config.yml specifications. Please make an issue on https://github.com/mstock98/npc-mannequins-in-survival/issues if one doesn't exist.");
        }

        return defaultConfig;
    }
}
