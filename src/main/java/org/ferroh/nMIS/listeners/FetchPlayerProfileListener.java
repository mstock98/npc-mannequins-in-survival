package org.ferroh.nMIS.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class FetchPlayerProfileListener implements Listener {
    private static HashMap<String, PlayerProfile> _playerProfileMap;

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

    public static PlayerProfile getCachedPlayerProfile(String username) {
        if (_playerProfileMap == null) {
            return null;
        }

        return _playerProfileMap.get(username);
    }

    private void fetchProfile(String username) {
        PlayerProfile profile = Bukkit.createPlayerProfile(Bukkit.getOfflinePlayer(username).getUniqueId(), username);

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
