package org.ferroh.nMIS.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;

/**
 * Listener for clicking on a name tag item stack. Caches the player profile/skin texture for the username written on the name tag
 */
public class NameTagClickSkinCacheListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack clickedItem = e.getCurrentItem();

        Skin skin;
        try {
            skin = new Skin(clickedItem);
        } catch (IllegalArgumentException ex) {
            return;
        }

        skin.cacheProfileAsync();
    }
}
