package org.ferroh.nMIS.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.constants.RecipeKeys;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;

/**
 * Event handler for crafting MannequinSouls
 */
public class SoulCraftingPrepareListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        if (!(e.getRecipe() instanceof CraftingRecipe craftingRecipe)) {
            return;
        }

        if (!RecipeKeys.isSoulRecipeKey(craftingRecipe.getKey())) {
            return;
        }

        ItemStack[] craftingMatrix = e.getInventory().getContents();

        MannequinSoul mannequinSoul;
        try {
            mannequinSoul = new MannequinSoul(craftingMatrix);
        } catch (IllegalArgumentException ex) {
            e.getInventory().setResult(null);
            return;
        }

        e.getInventory().setResult(mannequinSoul.toItemStack());
    }
}
