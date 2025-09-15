package org.ferroh.nMIS.types.gui.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.types.CommandState;
import org.ferroh.nMIS.types.gui.MannequinEquipGui;

public class MannequinEquipDeadSlotListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        CommandState commandState = NMIS.getCommandStateForPlayer(e.getWhoClicked().getUniqueId());

        if (commandState == null || !commandState.getStatus().equals(CommandState.Status.MANNEQUIN_EQUIPMENT_OPEN)) {
            return;
        }

        if (e.getRawSlot() == MannequinEquipGui.DEAD_SLOT_1 ||
                e.getRawSlot() == MannequinEquipGui.DEAD_SLOT_2 ||
                e.getRawSlot() == MannequinEquipGui.DEAD_SLOT_3) {
            e.setCancelled(true);
        }
    }
}
