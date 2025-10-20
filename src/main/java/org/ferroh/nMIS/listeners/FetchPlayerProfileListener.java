package org.ferroh.nMIS.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Click listener responsible for fetching and caching player profiles and skins
 */
public class FetchPlayerProfileListener implements Listener {
    /**
     * Cache for player profiles/skins
     */
    private static HashMap<String, PlayerProfile> _playerProfileMap;

    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() != Material.NAME_TAG) {
            return;
        }

        String nameTagName = ItemHelper.getDisplayName(clickedItem);

        if (!Skin.usernameIsValid(nameTagName)) {
            return;
        }

        fetchProfile(nameTagName);
    }

    /**
     * Main method for retrieving player profiles/skins from the cache
     * @param username Username of the player profile/skin to get
     * @return Cached player profile that has the given username, null there isn't one that's cached
     */
    public static PlayerProfile getCachedPlayerProfile(String username) {
        if (_playerProfileMap == null) {
            return null;
        }

        return _playerProfileMap.get(username);
    }

    /**
     * Check whether a player profile is cached
     * @param username Username of the player profile
     * @return True if the profile belonging to the username is cached
     */
    public static boolean isPlayerProfileCached(String username) {
        if (_playerProfileMap == null) {
            return false;
        }

        return _playerProfileMap.containsKey(username);
    }

    /**
     * Cache the player profile for a given username
     * @param username Username of the player profile to cache
     */
    public static void fetchProfile(String username) {
        PlayerProfile profile = (PlayerProfile) Bukkit.createPlayerProfile(Bukkit.getOfflinePlayer(username).getUniqueId(), username);

        profile.update()
                .orTimeout(10, TimeUnit.SECONDS)
                .whenComplete((updatedProfile, ex) -> {
                    Bukkit.getScheduler().runTask(NMIS.getPlugin(), () -> {
                        if (_playerProfileMap == null) {
                            _playerProfileMap = new HashMap<>();
                        }

                        _playerProfileMap.remove(username);

                        _playerProfileMap.put(username, updatedProfile);
                    });
                });
    }
}
