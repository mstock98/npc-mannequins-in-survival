package org.ferroh.nMIS.types;

import org.ferroh.nMIS.types.gui.MannequinEquipGui;

/**
 * Class representing player state across commands and events
 */
public class CommandState {
    /**
     * Get the current status enum member
     */
    private Status _status;

    /**
     * Get the mannequin equipment GUI for MANNEQUIN_EQUIPMENT_OPEN status
     */
    private MannequinEquipGui _equipmentGui;

    /**
     * Create a new command state object
     * @param status Status of the command state
     */
    public CommandState(Status status) {
        setStatus(status);
    }

    /**
     * Get the status for this command state object
     * @return Status for command state
     */
    public Status getStatus() {
        return _status;
    }

    /**
     * Set the status for this command state
     * @param status Status to set for this command state
     */
    public void setStatus(Status status) {
        _status = status;
    }

    /**
     * Get the mannequin equipment GUI if status is MANNEQUIN_EQUIPMENT_OPEN
     * @return Equipment gui
     */
    public MannequinEquipGui getEquipmentGui() {
        return _equipmentGui;
    }

    /**
     * Set the mannequin equipment GUI
     * @param equipmentGui GUI to set
     */
    public void setEquipmentGui(MannequinEquipGui equipmentGui) {
        _equipmentGui = equipmentGui;
    }

    /**
     * Enum representing the different statuses that a CommandState can have
     */
    public enum Status {
        MANNEQUIN_EQUIPMENT_OPEN
    }
}
