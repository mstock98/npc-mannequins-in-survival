package org.ferroh.nMIS.types.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Mannequin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.constants.PersistentDataKeys;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;

import java.util.UUID;

/**
 * Class representing the mannequin equipment GUI
 */
public class MannequinEquipGui {
    // Constants for inventory slot numbers in the GUI
    public static final int HELMET_GUI_SLOT = 0;
    public static final int CHESTPLATE_GUI_SLOT = 1;
    public static final int LEGGINGS_GUI_SLOT = 2;
    public static final int BOOTS_GUI_SLOT = 3;
    public static final int MAIN_HAND_GUI_SLOT = 4;
    public static final int OFF_HAND_GUI_SLOT = 5;
    public static final int DEAD_SLOT_1 = 6;
    public static final int DEAD_SLOT_2 = 7;
    public static final int DEAD_SLOT_3 = 8;

    /**
     * Equipment object for the mannequin for this GUI
     */
    private final Mannequin _mannequin;

    /**
     * Inventory object for this GUI
     */
    private Inventory _inventory = null;

    /**
     * Create a new MannequinEquipGui for a given mannequin entity
     * @param mannequin The mannequin entity
     */
    public MannequinEquipGui(Mannequin mannequin) {
        if (mannequin == null) {
            throw new IllegalArgumentException("Mannequin entity cannot be null");
        }

        _mannequin = mannequin;
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

        EntityEquipment equipment = _mannequin.getEquipment();

        if (!ItemHelper.isNullOrAir(equipment.getHelmet())) {
            _inventory.setItem(HELMET_GUI_SLOT, equipment.getHelmet());
        }

        if (!ItemHelper.isNullOrAir(equipment.getChestplate())) {
            _inventory.setItem(CHESTPLATE_GUI_SLOT, equipment.getChestplate());
        }

        if (!ItemHelper.isNullOrAir(equipment.getLeggings())) {
            _inventory.setItem(LEGGINGS_GUI_SLOT, equipment.getLeggings());
        }

        if (!ItemHelper.isNullOrAir(equipment.getBoots())) {
            _inventory.setItem(BOOTS_GUI_SLOT, equipment.getBoots());
        }

        if (!ItemHelper.isNullOrAir(equipment.getItemInMainHand())) {
            _inventory.setItem(MAIN_HAND_GUI_SLOT, equipment.getItemInMainHand());
        }

        if (!ItemHelper.isNullOrAir(equipment.getItemInOffHand())) {
            _inventory.setItem(OFF_HAND_GUI_SLOT, equipment.getItemInOffHand());
        }

        // Set the dead slots
        ItemStack deadSlotItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemHelper.setPersistentBooleanData(deadSlotItem, PersistentDataKeys.IS_DEAD_SLOT_ITEM, true);

        for (int i = 6; i < 9; i++) {
            _inventory.setItem(i, deadSlotItem);
        }

        player.openInventory(_inventory);
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
        return _mannequin.getEquipment();
    }

    /**
     * Get the entity ID for the mannequin tied to this GUI
     * @return Entity ID for mannequin
     */
    public UUID getMannequinEntityID() {
        return _mannequin.getUniqueId();
    }

    /**
     * Get the mannequin entity associated with this GUI
     * @return Mannequin that is having its equipment changed via this GUI
     */
    public Mannequin getMannequin() {
        return _mannequin;
    }
}
