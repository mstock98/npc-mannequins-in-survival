package org.ferroh.nMIS.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class MannequinEquipListener implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        System.out.println("Handling entity interact event");

        Entity clickedEntity = e.getRightClicked();

        // TODO: Check for actual mannequins in the future
        if (!(clickedEntity instanceof Zombie fakeMannequin)) {
            System.out.println("Clicked entity is not a zombie");
            return;
        }

        EntityEquipment mannequinEquipment = fakeMannequin.getEquipment();
        if (mannequinEquipment == null) {
            System.out.println("Zombie equipment is null");
            return;
        }

        e.setCancelled(true);

        if (e.getHand() != EquipmentSlot.HAND) {
            System.out.println("Equipment slot is not HAND. It is " + e.getHand());
            return;
        }

        Player player = e.getPlayer();
        ItemStack playerHeldItem = player.getInventory().getItemInMainHand();

        // TODO: Test for dupes
        ItemStack mannequinItem;
        if (materialIsHelmet(playerHeldItem.getType())) {
            mannequinItem = mannequinEquipment.getHelmet();

            mannequinEquipment.setHelmet(playerHeldItem);
        } else if (materialIsChestplate(playerHeldItem.getType())) {
            mannequinItem = mannequinEquipment.getChestplate();

            mannequinEquipment.setChestplate(playerHeldItem);
        } else if (materialIsLeggings(playerHeldItem.getType())) {
            mannequinItem = mannequinEquipment.getLeggings();

            mannequinEquipment.setLeggings(playerHeldItem);
        } else if (materialIsBoots(playerHeldItem.getType())) {
            mannequinItem = mannequinEquipment.getBoots();

            mannequinEquipment.setBoots(playerHeldItem);
        } else {
            mannequinItem = mannequinEquipment.getItemInMainHand();

            mannequinEquipment.setItemInMainHand(playerHeldItem);
        }

        player.getInventory().setItemInMainHand(mannequinItem);


        System.out.println("Player: " + player.getDisplayName());
        System.out.println("Player held item: " + playerHeldItem.getType());
        System.out.println("Entity held item: " + mannequinItem.getType());
    }

    // TODO: Add copper armor
    // TODO: Maybe there's a way that Spigot/Paper handles this?
    private boolean materialIsHelmet(Material material) {
        switch (material) {
            case LEATHER_HELMET, CHAINMAIL_HELMET, IRON_HELMET, GOLDEN_HELMET, DIAMOND_HELMET, NETHERITE_HELMET, TURTLE_HELMET -> {
                return true;
            }
        }

        return false;
    }

    private boolean materialIsChestplate(Material material) {
        switch (material) {
            case LEATHER_CHESTPLATE, CHAINMAIL_CHESTPLATE, IRON_CHESTPLATE, GOLDEN_CHESTPLATE, DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE, ELYTRA -> {
                return true;
            }
        }

        return false;
    }

    private boolean materialIsLeggings(Material material) {
        switch (material) {
            case LEATHER_LEGGINGS, CHAINMAIL_LEGGINGS, IRON_LEGGINGS, GOLDEN_LEGGINGS, DIAMOND_LEGGINGS, NETHERITE_LEGGINGS -> {
                return true;
            }
        }

        return false;
    }

    private boolean materialIsBoots(Material material) {
        switch (material) {
            case LEATHER_BOOTS, CHAINMAIL_BOOTS, IRON_BOOTS, GOLDEN_BOOTS, DIAMOND_BOOTS, NETHERITE_BOOTS -> {
                return true;
            }
        }

        return false;
    }
}
