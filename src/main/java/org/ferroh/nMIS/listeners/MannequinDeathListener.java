package org.ferroh.nMIS.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Mannequin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;

/**
 * Listener class to drop mannequin equipment on mannequin death
 */
public class MannequinDeathListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Mannequin mannequin)) {
            return;
        }

        EntityEquipment equipment = mannequin.getEquipment();
        Location deathLocation = e.getEntity().getLocation();
        World deathWorld = deathLocation.getWorld();

        for (ItemStack armorPiece : equipment.getArmorContents()) {
            deathWorld.dropItem(deathLocation, armorPiece);
        }

        if (!ItemHelper.isNullOrAir(equipment.getItemInMainHand())) {
            deathWorld.dropItem(deathLocation, equipment.getItemInMainHand());
        }

        if (!ItemHelper.isNullOrAir(equipment.getItemInOffHand())) {
            deathWorld.dropItem(deathLocation, equipment.getItemInOffHand());
        }

        MannequinSoul mannequinSoul;
        try {
            mannequinSoul = new MannequinSoul(mannequin);
        } catch (IllegalArgumentException ex) {
            return;
        }

        deathWorld.dropItem(deathLocation, mannequinSoul.toItemStack());
    }
}
