package org.ferroh.nMIS.types.gui.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.types.CommandState;
import org.ferroh.nMIS.types.gui.MannequinEquipGui;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;

import java.util.UUID;

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

        if (!(e.getPlayer() instanceof Player player)) {
            return;
        }

        resolveMannequinOpenCommandState(commandState, player.getUniqueId());
    }

    /**
     * Sync open mannequin equipment GUI with the equipment on mannequin entities
     * @param commandState Command state object for open mannequin GUI
     * @param playerID PlayerID that has the command state object
     */
    public static void resolveMannequinOpenCommandState(CommandState commandState, UUID playerID) {
        if (commandState == null || playerID == null || !commandState.getStatus().equals(CommandState.Status.MANNEQUIN_EQUIPMENT_OPEN)) {
            return;
        }

        Inventory inventory = commandState.getEquipmentGui().getInventory();
        EntityEquipment equipment = commandState.getEquipmentGui().getEntityEquipment();

        ItemStack helmet = inventory.getItem(MannequinEquipGui.HELMET_GUI_SLOT);
        equipment.setHelmet(helmet);
        equipment.setHelmetDropChance(MannequinSoul.ITEM_DROP_CHANCE);

        ItemStack chestplate = inventory.getItem(MannequinEquipGui.CHESTPLATE_GUI_SLOT);
        equipment.setChestplate(chestplate);
        equipment.setChestplateDropChance(MannequinSoul.ITEM_DROP_CHANCE);

        ItemStack leggings = inventory.getItem(MannequinEquipGui.LEGGINGS_GUI_SLOT);
        equipment.setLeggings(leggings);
        equipment.setLeggingsDropChance(MannequinSoul.ITEM_DROP_CHANCE);

        ItemStack boots = inventory.getItem(MannequinEquipGui.BOOTS_GUI_SLOT);
        equipment.setBoots(boots);
        equipment.setBootsDropChance(MannequinSoul.ITEM_DROP_CHANCE);

        ItemStack mainHandItem = inventory.getItem(MannequinEquipGui.MAIN_HAND_GUI_SLOT);
        equipment.setItemInMainHand(mainHandItem);
        equipment.setItemInMainHandDropChance(MannequinSoul.ITEM_DROP_CHANCE);

        ItemStack offHandItem = inventory.getItem(MannequinEquipGui.OFF_HAND_GUI_SLOT);
        equipment.setItemInOffHand(offHandItem);
        equipment.setItemInOffHandDropChance(MannequinSoul.ITEM_DROP_CHANCE);

        NMIS.markMannequinAsOpen(commandState.getEquipmentGui().getMannequinEntityID(), false);
        commandState.getEquipmentGui().getMannequin().setInvulnerable(false);
        NMIS.clearCommandStateForPlayer(playerID);
    }
}
