package org.ferroh.nMIS.listeners;

import org.bukkit.entity.Mannequin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class MannequinDamageEvent implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Mannequin mannequin)) {
            return;
        }

        String username = mannequin.getProfile().name();

        if (FetchPlayerProfileListener.isPlayerProfileCached(username)) {
            return;
        }

        FetchPlayerProfileListener.fetchProfile(username);
    }
}
