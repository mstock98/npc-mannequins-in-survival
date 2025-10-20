package org.ferroh.nMIS.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mannequin;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.constants.PersistentDataKeys;
import org.ferroh.nMIS.helpers.EntityHelper;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.CommandState;
import org.ferroh.nMIS.types.gui.MannequinEquipGui;

/**
 * Listener for accessing/changing the equipment, pose, and name on a mannequin
 */
public class MannequinRightClickListener implements Listener {
    /**
     * Main event handler
     * @param e Event to handle
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Entity clickedEntity = e.getRightClicked();

        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (!(clickedEntity instanceof Mannequin mannequin)) {
            return;
        }

        ItemStack heldItem = e.getPlayer().getInventory().getItemInMainHand();

        if (heldItem.getType().equals(Material.FEATHER)) {
            mannequin.setPose(getNextPose(mannequin.getPose()));
            return;
        } else if (heldItem.getType().equals(Material.NAME_TAG)) {
            String nameTagName = ItemHelper.getDisplayName(heldItem);
            mannequin.setCustomName(nameTagName);
            EntityHelper.setPersistentStringData(mannequin, PersistentDataKeys.MANNEQUIN_ENTITY_DISPLAY_NAME, nameTagName);
            heldItem.setAmount(heldItem.getAmount() - 1);
            return;
        }

        if (NMIS.isMannequinOpen(clickedEntity.getUniqueId()) || NMIS.getCommandStateForPlayer(e.getPlayer().getUniqueId()) != null) {
            return;
        }

        MannequinEquipGui gui = new MannequinEquipGui(mannequin);

        CommandState commandState = new CommandState(CommandState.Status.MANNEQUIN_EQUIPMENT_OPEN, e.getPlayer().getUniqueId());
        commandState.setEquipmentGui(gui);
        NMIS.setCommandStateForPlayer(e.getPlayer().getUniqueId(), commandState);

        NMIS.markMannequinAsOpen(clickedEntity.getUniqueId(), commandState);

        gui.display(e.getPlayer());
    }

    /**
     * Get the next mannequin pose in the list when cycling through poses
     * @param currentPose Current pose in the cycle
     * @return Next pose in the cycle, first pose (standing) if current pose is null or unrecognized
     */
    private Pose getNextPose(Pose currentPose) {
        if (currentPose == null) {
            return Pose.STANDING;
        }

        switch (currentPose) {
            case STANDING -> {
                return Pose.FALL_FLYING;
            }

            case FALL_FLYING -> {
                return Pose.SLEEPING;
            }

            case SLEEPING -> {
                return Pose.SNEAKING;
            }

            case SNEAKING -> {
                return Pose.SWIMMING;
            }

            case SWIMMING -> {
                return Pose.STANDING;
            }
        }

        return Pose.STANDING;
    }
}
