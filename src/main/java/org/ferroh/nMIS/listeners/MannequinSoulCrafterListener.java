package org.ferroh.nMIS.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.ferroh.nMIS.constants.RecipeKeys;

/**
 * Listener to prevent mannequin souls from being crafted in a crafter
 */
public class MannequinSoulCrafterListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onCrafterCraft(CrafterCraftEvent e) {
        if (!RecipeKeys.isSoulRecipeKey(e.getRecipe().getKey())) {
            return;
        }

        e.setCancelled(true);
    }
}
