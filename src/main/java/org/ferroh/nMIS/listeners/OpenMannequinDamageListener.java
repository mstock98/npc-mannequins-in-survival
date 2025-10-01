package org.ferroh.nMIS.listeners;

import org.bukkit.entity.Mannequin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.ferroh.nMIS.NMIS;

/**
 * Listener to prevent mannequins from taking damage while its equipment is being managed
 */
public class OpenMannequinDamageListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Mannequin mannequin)) {
            return;
        }

        if (!NMIS.isMannequinOpen(mannequin.getUniqueId())) {
            return;
        }

        e.setCancelled(true);
    }
}
