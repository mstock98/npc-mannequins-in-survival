package org.ferroh.nMIS.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.types.mannequinSoul.MannequinSoul;

/**
 * Listener for when a MannequinSoul is used to make a mannequin
 */
public class MannequinSoulUseListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null) {
            return;
        }

        ItemStack usedItem = e.getPlayer().getInventory().getItemInMainHand();

        MannequinSoul mannequinSoul;
        try {
            mannequinSoul = new MannequinSoul(usedItem);
        } catch (IllegalArgumentException ex) {
            return;
        }

        e.setCancelled(true);

        mannequinSoul.spawn(e.getClickedBlock().getLocation(), getMannequinYaw(e.getPlayer()), getMannequinPitch(e.getPlayer()));

        usedItem.setAmount(usedItem.getAmount() - 1);
    }

    /**
     * Calculate the yaw that the mannequin should have based on player yaw
     * The calculated mannequin yaw will be a value that is looking at the player
     * @param player Player to base mannequin yaw off of
     * @return Yow for mannequin that is looking at the player
     */
    private float getMannequinYaw(Player player) {
        if (player == null) {
            return 0.0f;
        }

        float mannequinYaw = player.getYaw();

        mannequinYaw = mannequinYaw + 180;

        if (mannequinYaw >= 180) {
            mannequinYaw = mannequinYaw - 360;
        }

        return mannequinYaw;
    }

    /**
     * Calculate the pitch the mannequin should have based on player pitch and sneaking status
     * @param player Player to base mannequin pitch off of
     * @return Pitch of mannequin that is the same as the player, or 0 pitch if the player is not sneaking
     */
    private float getMannequinPitch(Player player) {
        if (player == null || !(player.isSneaking())) {
            return 0.0f;
        }

        return player.getPitch();
    }
}
