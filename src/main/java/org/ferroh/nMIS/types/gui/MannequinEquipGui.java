package org.ferroh.nMIS.types.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.CommandState;

import java.util.UUID;

/**
 * Class representing the mannequin equipment GUI
 */
public class MannequinEquipGui {
    // Constants for inventory slot numbers in the GUI
    public static int HELMET_GUI_SLOT = 0;
    public static int CHESTPLATE_GUI_SLOT = 1;
    public static int LEGGINGS_GUI_SLOT = 2;
    public static int BOOTS_GUI_SLOT = 3;
    public static int MAIN_HAND_GUI_SLOT = 4;
    public static int OFF_HAND_GUI_SLOT = 5;
    public static int DEAD_SLOT_1 = 6;
    public static int DEAD_SLOT_2 = 7;
    public static int DEAD_SLOT_3 = 8;

    /**
     * Equipment object for the mannequin for this GUI
     */
    private final EntityEquipment _equipment;

    /**
     * UUID of the mannequin for this GUI
     */
    private final UUID _mannequinEntityID;

    /**
     * Inventory object for this GUI
     */
    private Inventory _inventory = null;

    /**
     * Create a new MannequinEquipGui for a given mannequin equipment object and mannequin entity UUID
     * @param mannequinEquipment Equipment items (inventory) that the mannequin has
     * @param mannequinEntityID UUID of the mannequin entity
     */
    public MannequinEquipGui(EntityEquipment mannequinEquipment, UUID mannequinEntityID) {
        if (mannequinEquipment == null) {
            throw new IllegalArgumentException("Mannequin equipment cannot be null");
        }

        if (mannequinEntityID == null) {
            throw new IllegalArgumentException("Mannequin entity ID cannot be null");
        }

        _equipment = mannequinEquipment;
        _mannequinEntityID = mannequinEntityID;
    }

    /**
     * Show this mannequin equipment GUI to the specified player
     * @param player Player to show this GUI to
     */
    public void display(Player player) {
        if (player == null) {
            return;
        }

        _inventory = Bukkit.createInventory(null, 9, Strings.MANNEQUIN_EQUIPMENT_GUI_LABEL);

        if (!ItemHelper.isNullOrAir(_equipment.getHelmet())) {
            _inventory.setItem(HELMET_GUI_SLOT, _equipment.getHelmet());
        }

        if (!ItemHelper.isNullOrAir(_equipment.getChestplate())) {
            _inventory.setItem(CHESTPLATE_GUI_SLOT, _equipment.getChestplate());
        }

        if (!ItemHelper.isNullOrAir(_equipment.getLeggings())) {
            _inventory.setItem(LEGGINGS_GUI_SLOT, _equipment.getLeggings());
        }

        if (!ItemHelper.isNullOrAir(_equipment.getBoots())) {
            _inventory.setItem(BOOTS_GUI_SLOT, _equipment.getBoots());
        }

        if (!ItemHelper.isNullOrAir(_equipment.getItemInMainHand())) {
            _inventory.setItem(MAIN_HAND_GUI_SLOT, _equipment.getItemInMainHand());
        }

        if (!ItemHelper.isNullOrAir(_equipment.getItemInOffHand())) {
            _inventory.setItem(OFF_HAND_GUI_SLOT, _equipment.getItemInOffHand());
        }

        // Set the dead slots
        for (int i = 6; i < 9; i++) {
            _inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }

        player.openInventory(_inventory);

        CommandState commandState = new CommandState(CommandState.Status.MANNEQUIN_EQUIPMENT_OPEN);
        commandState.setEquipmentGui(this);
        NMIS.setCommandStateForPlayer(player.getUniqueId(), commandState);
    }

    /**
     * Get the Inventory object for this GUI
     * @return Inventory object tied to the GUI
     */
    public Inventory getInventory() {
        return _inventory;
    }

    /**
     * Get the equipment object for the mannequin tied to this GUI
     * @return Entity equipment object for the mannequin
     */
    public EntityEquipment getEntityEquipment() {
        return _equipment;
    }

    /**
     * Get the entity ID for the mannequin tied to this GUI
     * @return Entity ID for mannequin
     */
    public UUID getMannequinEntityID() {
        return _mannequinEntityID;
    }
}
