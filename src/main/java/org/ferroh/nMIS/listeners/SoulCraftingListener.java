package org.ferroh.nMIS.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;

public class SoulCraftingListener implements Listener {
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        ItemStack[] craftingMatrix = e.getInventory().getContents();

        MannequinSoul mannequinSoul;
        try {
            mannequinSoul = new MannequinSoul(craftingMatrix);
        } catch (IllegalArgumentException ex) {
            return;
        }

        e.getInventory().setResult(mannequinSoul.toItemStack());
    }
}
