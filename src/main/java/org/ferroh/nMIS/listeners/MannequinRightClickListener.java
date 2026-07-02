package org.ferroh.nMIS.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.ferroh.nMIS.helpers.MannequinHelper;
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

        if (!EntityHelper.isNMISMannequin(mannequin)) {
            return;
        }

        ItemStack heldItem = e.getPlayer().getInventory().getItemInMainHand();

        if (heldItem.getType().equals(Material.FEATHER)) {
            handlePoseChange(mannequin);
        } else if (heldItem.getType().equals(Material.NAME_TAG)) {
            handleNameTagRename(mannequin, heldItem);
        } else if (ItemHelper.isAnvil(heldItem)) {
            handleAnchorToggle(e, mannequin, heldItem);
        } else {
            handleEquipmentChange(e, mannequin);
        }
    }

    /**
     * Opens the equipment change GUI for a mannequin to the player
     * @param e Event for right-clicking the mannequin
     * @param mannequin Mannequin that will have equipment modified
     */
    private void handleEquipmentChange(PlayerInteractEntityEvent e, Mannequin mannequin) {
        if (NMIS.isMannequinOpen(mannequin.getUniqueId()) || NMIS.getCommandStateForPlayer(e.getPlayer().getUniqueId()) != null) {
            return;
        }

        MannequinEquipGui gui = new MannequinEquipGui(mannequin);

        CommandState commandState = new CommandState(CommandState.Status.MANNEQUIN_EQUIPMENT_OPEN, e.getPlayer().getUniqueId());
        commandState.setEquipmentGui(gui);
        NMIS.setCommandStateForPlayer(e.getPlayer().getUniqueId(), commandState);

        NMIS.markMannequinAsOpen(mannequin.getUniqueId(), commandState);

        gui.display(e.getPlayer());
    }

    /**
     * Cycle to the next pose for the given mannequin
     * @param mannequin Mannequin that will have its pose changed
     */
    private void handlePoseChange(Mannequin mannequin) {
        mannequin.setPose(getNextPose(mannequin.getPose()));
    }

    /**
     * Change the display name of a mannequin based on a name tag
     * @param mannequin Mannequin that will have its display name changed
     * @param nameTag Name tag with the display name to set for the mannequin
     */
    private void handleNameTagRename(Mannequin mannequin, ItemStack nameTag) {
        String nameTagName = ItemHelper.getDisplayName(nameTag);
        mannequin.setCustomName(nameTagName);
        EntityHelper.setPersistentStringData(mannequin, PersistentDataKeys.MANNEQUIN_ENTITY_DISPLAY_NAME, nameTagName);
        nameTag.setAmount(nameTag.getAmount() - 1);
    }

    /**
     * Toggle the "Anchored" status for a mannequin
     * @param e The PlayerInteractEntity event that needs to be handled
     * @param mannequin The mannequin from the event
     * @param heldAnvil The anvil that was held in the player's hand
     */
    private void handleAnchorToggle(PlayerInteractEntityEvent e, Mannequin mannequin, ItemStack heldAnvil) {
        if (MannequinHelper.getAnchorStateDefaultMissing(mannequin) != MannequinHelper.AnchorState.MISSING) {
            mannequin.setImmovable(false);
            mannequin.setNoPhysics(false);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.3f, 0.6f);
            ItemHelper.addToInventoryOrDropOnGround(e.getPlayer(), MannequinHelper.anchorStateToItemStack(MannequinHelper.getAnchorStateDefaultMissing(mannequin), 1));
            MannequinHelper.setAnchorState(mannequin, MannequinHelper.AnchorState.MISSING);
        } else {
            mannequin.setImmovable(true);
            mannequin.setNoPhysics(true);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.3f, 1.2f);
            MannequinHelper.setAnchorState(mannequin, MannequinHelper.itemStackToAnchorState(heldAnvil));
            heldAnvil.setAmount(heldAnvil.getAmount() - 1);
        }
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
