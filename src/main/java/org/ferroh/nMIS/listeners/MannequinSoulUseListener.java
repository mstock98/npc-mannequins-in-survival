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

        mannequinSoul.spawn(e.getInteractionPoint(), getMannequinHeadYaw(e.getPlayer()), getMannequinHeadPitch(e.getPlayer()), getMannequinBodyYaw(e.getPlayer()));

        usedItem.setAmount(usedItem.getAmount() - 1);
    }

    /**
     * Calculate the head yaw that the mannequin should have based on player head yaw
     * The calculated mannequin head yaw will be a value that is looking at the player
     * @param player Player to base mannequin head yaw off of
     * @return Head yaw for mannequin that is looking at the player
     */
    private float getMannequinHeadYaw(Player player) {
        if (player == null) {
            return 0.0f;
        }

        return yaw180(player.getYaw());
    }

    /**
     * Calculate the head pitch the mannequin should have based on player head pitch and sneaking status
     * @param player Player to base mannequin head pitch off of
     * @return Head pitch of mannequin that is the same as the player, or 0 head pitch (head is level) if the player is not sneaking
     */
    private float getMannequinHeadPitch(Player player) {
        if (player == null || !(player.isSneaking())) {
            return 0.0f;
        }

        return player.getPitch();
    }

    /**
     * Calculate the body yaw the mannequin should have based on the player's body yaw
     * The calculated body yaw for the mannequin will be facing the player
     * @param player Player to base mannequin body yaw off of
     * @return Body yaw for the mannequin that is mirroring the player
     */
    private float getMannequinBodyYaw(Player player) {
        if (player == null) {
            return 0.0f;
        }

        return yaw180(player.getBodyYaw());
    }

    /**
     * Rotate a head or body yaw value by 180 degrees
     * @param yaw Yaw to rotate
     * @return Rotated yaw
     */
    private float yaw180(float yaw) {
        yaw = yaw + 180;

        if (yaw >= 180) {
            yaw = yaw - 360;
        }

        return yaw;
    }
}
