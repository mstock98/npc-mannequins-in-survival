package org.ferroh.nMIS;

import org.bukkit.plugin.java.JavaPlugin;
import org.ferroh.nMIS.listeners.SoulCraftingListener;

public final class NMIS extends JavaPlugin {
    private static JavaPlugin _plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        _plugin = this;

        // Register event listeners
        getServer().getPluginManager().registerEvents(new SoulCraftingListener(), getPlugin());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin() {
        return _plugin;
    }
}
