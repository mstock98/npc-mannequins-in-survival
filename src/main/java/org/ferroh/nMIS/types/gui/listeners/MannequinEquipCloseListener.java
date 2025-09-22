package org.ferroh.nMIS.types.gui.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.types.CommandState;
import org.ferroh.nMIS.types.gui.MannequinEquipGui;

/**
 * Listener for closing the mannequin equipment GUI
 */
public class MannequinEquipCloseListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        CommandState commandState = NMIS.getCommandStateForPlayer(e.getPlayer().getUniqueId());

        if (commandState == null || !commandState.getStatus().equals(CommandState.Status.MANNEQUIN_EQUIPMENT_OPEN)) {
            return;
        }

        Inventory inventory = commandState.getEquipmentGui().getInventory();
        EntityEquipment equipment = commandState.getEquipmentGui().getEntityEquipment();

        ItemStack helmet = inventory.getItem(MannequinEquipGui.HELMET_GUI_SLOT);
        equipment.setHelmet(helmet);

        ItemStack chestplate = inventory.getItem(MannequinEquipGui.CHESTPLATE_GUI_SLOT);
        equipment.setChestplate(chestplate);

        ItemStack leggings = inventory.getItem(MannequinEquipGui.LEGGINGS_GUI_SLOT);
        equipment.setLeggings(leggings);

        ItemStack boots = inventory.getItem(MannequinEquipGui.BOOTS_GUI_SLOT);
        equipment.setBoots(boots);

        ItemStack mainHandItem = inventory.getItem(MannequinEquipGui.MAIN_HAND_GUI_SLOT);
        equipment.setItemInMainHand(mainHandItem);

        ItemStack offHandItem = inventory.getItem(MannequinEquipGui.OFF_HAND_GUI_SLOT);
        equipment.setItemInOffHand(offHandItem);

        NMIS.clearCommandStateForPlayer(e.getPlayer().getUniqueId());
        NMIS.markMannequinAsOpen(commandState.getEquipmentGui().getMannequinEntityID(), false);
    }
}
