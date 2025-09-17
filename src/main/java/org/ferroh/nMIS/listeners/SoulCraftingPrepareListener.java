package org.ferroh.nMIS.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.constants.RecipeConstants;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;

public class SoulCraftingPrepareListener implements Listener {
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        if (!(e.getRecipe() instanceof CraftingRecipe craftingRecipe)) {
            return;
        }

        if (!craftingRecipe.getKey().getKey().startsWith(RecipeConstants.SOUL_RECIPE_KEY_PREFIX)) {
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

        if (mannequinSoul.getSkin() != null) {
            ItemHelper.setPlayerHeadSkinForCraftingEvent(e.getInventory().getResult(), mannequinSoul.getSkin().getUsername(), e);
        }
    }
}
