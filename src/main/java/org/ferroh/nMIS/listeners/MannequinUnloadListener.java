package org.ferroh.nMIS.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mannequin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.types.CommandState;

/**
 * Listener class for when mannequins are unloaded
 */
public class MannequinUnloadListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onEntitiesUnloadEvent(EntitiesUnloadEvent e) {
        for (Entity entity : e.getEntities()) {
            if (!(entity instanceof Mannequin mannequin)) {
                continue;
            }

            if (!NMIS.isMannequinOpen(mannequin.getUniqueId())) {
                continue;
            }

            CommandState commandState = NMIS.getCommandStateForMannequin(mannequin);

            Player player = Bukkit.getPlayer(commandState.getOwner());

            if (player != null) {
                player.closeInventory();
            } else {
                throw new IllegalStateException("Could not get player associated with open mannequin. Please report this to https://github.com/mstock98/npc-mannequins-in-survival/issues. Deleting the following items from the mannequin for safety: " + mannequin.getEquipment());
            }
        }
    }
}
