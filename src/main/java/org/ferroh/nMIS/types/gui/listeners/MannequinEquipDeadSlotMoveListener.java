package org.ferroh.nMIS.types.gui.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.ferroh.nMIS.constants.PersistentDataKeys;
import org.ferroh.nMIS.helpers.ItemHelper;

/**
 * Listener for attempting to triple click the dead slot item out of the mannequin equipment GUI
 */
public class MannequinEquipDeadSlotMoveListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        if (!ItemHelper.getPersistentBooleanDataDefaultFalse(e.getItem(), PersistentDataKeys.IS_DEAD_SLOT_ITEM)) {
            return;
        }

        e.setCancelled(true);
    }
}
