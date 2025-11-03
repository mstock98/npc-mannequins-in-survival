package org.ferroh.nMIS.helpers;

import org.bukkit.entity.Mannequin;
import org.ferroh.nMIS.constants.PersistentDataKeys;

public class MannequinHelper {
    public static void setAnchorState(Mannequin mannequin, AnchorState anchorState) {
        EntityHelper.setPersistentIntegerData(mannequin, PersistentDataKeys.MANNEQUIN_ENTITY_ANCHOR_STATE, anchorState.ordinal());
    }

    public static AnchorState getAnchorStateDefaultPristine(Mannequin mannequin) {
        Integer anchorStateRaw = EntityHelper.getPersistentIntegerData(mannequin, PersistentDataKeys.MANNEQUIN_ENTITY_ANCHOR_STATE);

        if (anchorStateRaw == null) {
            return AnchorState.PRISTINE;
        }

        return AnchorState.values()[anchorStateRaw];
    }



    public enum AnchorState {
        PRISTINE,
        CHIPPED,
        DAMAGED
    }
}
