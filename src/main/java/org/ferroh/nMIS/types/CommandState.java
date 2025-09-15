package org.ferroh.nMIS.types;

import org.ferroh.nMIS.types.gui.MannequinEquipGui;

public class CommandState {
    private Status _status;
    private MannequinEquipGui _equipmentGui;

    public CommandState(Status status) {
        setStatus(status);
    }

    public Status getStatus() {
        return _status;
    }

    public void setStatus(Status status) {
        _status = status;
    }

    public MannequinEquipGui getEquipmentGui() {
        return _equipmentGui;
    }

    public void setEquipmentGui(MannequinEquipGui equipmentGui) {
        _equipmentGui = equipmentGui;
    }

    public enum Status {
        MANNEQUIN_EQUIPMENT_OPEN
    }
}
