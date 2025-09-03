package org.ferroh.nMIS.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ferroh.nMIS.constants.Strings;

public class SoulCraftingListener implements Listener {
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        ItemStack[] craftingMatrix = e.getInventory().getContents();


    }

    private boolean matrixContainsSoulStarter(ItemStack[] craftingMatrix) {
        if (craftingMatrix == null) {
            return false;
        }

        boolean soulStarterFound = false;
        for (ItemStack ingredient : craftingMatrix) {
            ItemMeta ingredientMeta = ingredient.getItemMeta();

            if (ingredient.getType() != Material.PAPER || ingredientMeta == null || !ingredientMeta.hasDisplayName()) {
                continue;
            }

            String anvilName = ingredientMeta.getItemName();

            if (anvilName.equalsIgnoreCase(Strings.SOUL_STARTER_NAME)) {
                soulStarterFound = true;
                break;
            }
        }

        return soulStarterFound;
    }
}
