package org.ferroh.nMIS.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.constants.PersistentDataKeys;

/**
 * Class containing some static helper methods for operating on Mannequins
 */
public class MannequinHelper {
    /**
     * Record what damage state the anvil used to anchor the mannequin was is
     * @param mannequin Mannequin entity to store state data
     * @param anchorState Damage level of the anvil
     */
    public static void setAnchorState(Mannequin mannequin, AnchorState anchorState) {
        EntityHelper.setPersistentIntegerData(mannequin, PersistentDataKeys.MANNEQUIN_ANCHOR_STATE, anchorState.ordinal());
    }

    /**
     * Get the damage state of the anvil used to anchor the mannequin
     * @param mannequin Mannequin entity that has anvil state data
     * @return Damage level of the anchoring anvil, or MISSING if no data present
     */
    public static AnchorState getAnchorStateDefaultMissing(Mannequin mannequin) {
        Integer anchorStateRaw = EntityHelper.getPersistentIntegerData(mannequin, PersistentDataKeys.MANNEQUIN_ANCHOR_STATE);

        if (anchorStateRaw == null) {
            return AnchorState.MISSING;
        }

        return AnchorState.values()[anchorStateRaw];
    }

    /**
     * Convert a DamageState enum member to an anvil ItemStack with the specified quantity
     * @param anchorState Anvil/anchor damage state enum member
     * @param amount Number of anvils in the item stack
     * @return ItemStack of anvils with the specified damage level and amount, or null if AnchorState is MISSING or amount is less than 1
     */
    public static ItemStack anchorStateToItemStack(AnchorState anchorState, int amount) {
        if (amount < 1) {
            return null;
        }

        switch (anchorState) {
            case PRISTINE -> {
                return new ItemStack(Material.ANVIL, amount);
            }
            case CHIPPED -> {
                return new ItemStack(Material.CHIPPED_ANVIL, amount);
            }
            case DAMAGED -> {
                return new ItemStack(Material.DAMAGED_ANVIL, amount);
            }
        }

        return null;
    }

    /**
     * Convert an ItemStack of anvils to an AnchorState enum member
     * @param anvil Anvil ItemStack to convert
     * @return AnchorState enum member based on the damage state of the anvil ItemStack,
     *         or null if passed in ItemStack is null or not an anvil
     */
    public static AnchorState itemStackToAnchorState(ItemStack anvil) {
        if (anvil == null) {
            return AnchorState.MISSING;
        }

        switch (anvil.getType()) {
            case Material.ANVIL -> {
                return AnchorState.PRISTINE;
            }
            case Material.CHIPPED_ANVIL -> {
                return AnchorState.CHIPPED;
            }
            case Material.DAMAGED_ANVIL -> {
                return AnchorState.DAMAGED;
            }
        }

        return AnchorState.MISSING;
    }

    /**
     * Enum representing the different damage levels that an anchor can have,
     * plus an extra member for when the anvil is missing or an ItemStack is not an anvil
     */
    public enum AnchorState {
        PRISTINE,
        CHIPPED,
        DAMAGED,
        MISSING
    }
}
