package org.ferroh.nMIS.types.gui.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.ferroh.nMIS.constants.PersistentDataKeys;
import org.ferroh.nMIS.helpers.ItemHelper;

public class MannequinEquipDeadSlotMoveListener implements Listener {
    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        if (!ItemHelper.getPersistentBooleanDataDefaultFalse(e.getItem(), PersistentDataKeys.IS_DEAD_SLOT_ITEM)) {
            return;
        }

        e.setCancelled(true);
    }
}
