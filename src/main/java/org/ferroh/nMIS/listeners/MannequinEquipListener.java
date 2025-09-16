package org.ferroh.nMIS.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.types.gui.MannequinEquipGui;

public class MannequinEquipListener implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Entity clickedEntity = e.getRightClicked();

        // TODO: Check for actual mannequins in the future
        if (!(clickedEntity instanceof Zombie fakeMannequin)) {
            return;
        }

        if (NMIS.isMannequinOpen(clickedEntity.getUniqueId())) {
            return;
        }

        EntityEquipment equipment = fakeMannequin.getEquipment();

        if (equipment != null) {
            NMIS.markMannequinAsOpen(clickedEntity.getUniqueId(), true);

            new MannequinEquipGui(equipment, clickedEntity.getUniqueId()).display(e.getPlayer());
        }
    }
}
