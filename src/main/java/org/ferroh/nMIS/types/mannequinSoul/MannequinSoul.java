package org.ferroh.nMIS.types.mannequinSoul;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.constants.PersistentDataKeys;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulIngredient;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulStarter;

import java.util.ArrayList;
import java.util.List;

public class MannequinSoul {
    private Skin _skin = null;

    public MannequinSoul(ItemStack[] craftingMatrix) {
        for (ItemStack ingredient : craftingMatrix) {
            if (!SoulIngredient.itemStackIsIngredient(ingredient) && !ingredient.getType().equals(Material.AIR)) {
                throw new IllegalArgumentException("Crafting ingredients has item that isn't a MannequinSoul ingredient");
            }
        }

        int numSoulStartersInMatrix = 0;
        for (ItemStack ingredient : craftingMatrix) {
            try {
                new SoulStarter(ingredient);
                numSoulStartersInMatrix++;
            } catch (IllegalArgumentException e) {}
        }
        if (numSoulStartersInMatrix != 1) {
            throw new IllegalArgumentException("Crafting ingredients must contain exactly 1 soul starter");
        }

        int numSkinsInMatrix = 0;
        for (ItemStack ingredient : craftingMatrix) {
            try {
                _skin = new Skin(ingredient);
                numSkinsInMatrix++;
            } catch (IllegalArgumentException e) {}
        }
        if (numSkinsInMatrix > 1) {
            throw new IllegalArgumentException("Crafting ingredients cannot contain more than 1 skin");
        }
    }

    public MannequinSoul(ItemStack potentialMannequinSoulItem) {
        if (potentialMannequinSoulItem == null) {
            throw new IllegalArgumentException("Mannequin soul item cannot be null");
        }

        if (!potentialMannequinSoulItem.getType().equals(getMannequinSoulMaterial())) {
            throw new IllegalArgumentException("Wrong mannequin soul material");
        }

        if (!ItemHelper.getPersistentBooleanDataDefaultFalse(potentialMannequinSoulItem, PersistentDataKeys.IS_MANNEQUIN_SOUL)) {
            throw new IllegalArgumentException("ItemStack is not marked as a mannequin soul");
        }

        ItemStack mannequinSoulItem = potentialMannequinSoulItem; // Rename for readability

        // Optional fields
        String skinUsername = ItemHelper.getPersistentStringData(mannequinSoulItem, PersistentDataKeys.MANNEQUIN_SKIN_USERNAME);
        if (skinUsername != null && !skinUsername.isEmpty()) {
            _skin = new Skin(skinUsername);
        }
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(getMannequinSoulMaterial());

        // Populate persistent data
        ItemHelper.setPersistentBooleanData(itemStack, PersistentDataKeys.IS_MANNEQUIN_SOUL, true);

        if (getSkin() != null) {
            ItemHelper.setPersistentStringData(itemStack, PersistentDataKeys.MANNEQUIN_SKIN_USERNAME, getSkin().getUsername());
        }

        // Set display info
        ItemHelper.setDisplayName(itemStack, Strings.SOUL_NAME);
        ItemHelper.setLore(itemStack, buildLore());

        return itemStack;
    }

    public Skin getSkin() {
        return _skin;
    }

    private Material getMannequinSoulMaterial() {
        return Material.PAPER;
    }

    private List<String> buildLore() {
        List<String> lore = new ArrayList<>();

        lore.add(Strings.SOUL_LORE_INSTRUCTIONS_HEADER);

        if (getSkin() != null) {
            lore.add(" ");
            lore.add(Strings.SOUL_LORE_PROPERTIES_HEADER);

            if (getSkin() != null) {
                lore.add(Strings.SOUL_LORE_PROFILE_LABEL + getSkin().getUsername());
            }
        }

        return lore;
    }
}
