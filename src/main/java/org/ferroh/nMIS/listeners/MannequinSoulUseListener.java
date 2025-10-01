package org.ferroh.nMIS.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;

/**
 * Listener for when a MannequinSoul is used to make a mannequin
 */
public class MannequinSoulUseListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null) {
            return;
        }

        ItemStack usedItem = e.getPlayer().getInventory().getItemInMainHand();

        MannequinSoul mannequinSoul;
        try {
            mannequinSoul = new MannequinSoul(usedItem);
        } catch (IllegalArgumentException ex) {
            return;
        }

        e.setCancelled(true);

        mannequinSoul.spawn(e.getClickedBlock().getLocation());

        usedItem.setAmount(usedItem.getAmount() - 1);
    }
}
