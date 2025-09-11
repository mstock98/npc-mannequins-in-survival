package org.ferroh.nMIS.types.mannequinSoul;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.constants.PersistentDataKeys;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.HealthBuff;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulIngredient;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulStarter;

import java.util.ArrayList;
import java.util.List;

public class MannequinSoul {
    private Skin _skin = null;
    private HealthBuff _healthBuff = null;

    public MannequinSoul() {}

    public MannequinSoul(ItemStack[] craftingMatrix) {
        for (int i = 1; i < craftingMatrix.length; i++) {
            if (!SoulIngredient.itemStackIsIngredient(craftingMatrix[i]) && !craftingMatrix[i].getType().equals(Material.AIR)) {
                throw new IllegalArgumentException("Crafting ingredients has item that isn't a MannequinSoul ingredient");
            }
        }

        // TODO: Make this one loop
        int numSoulStartersInMatrix = 0;
        for (ItemStack ingredient : craftingMatrix) {
            try {
                new SoulStarter(ingredient);
                numSoulStartersInMatrix++;
            } catch (IllegalArgumentException ignored) {}
        }
        if (numSoulStartersInMatrix != 1) {
            throw new IllegalArgumentException("Crafting ingredients must contain exactly 1 soul starter");
        }

        int numSkinsInMatrix = 0;
        for (ItemStack ingredient : craftingMatrix) {
            try {
                _skin = new Skin(ingredient);
                numSkinsInMatrix++;
            } catch (IllegalArgumentException ignored) {}
        }
        if (numSkinsInMatrix > 1) {
            throw new IllegalArgumentException("Crafting ingredients cannot contain more than 1 skin");
        }

        int numHealthBuffsInMatrix = 0;
        for (ItemStack ingredient : craftingMatrix) {
            try {
                _healthBuff = new HealthBuff(ingredient);
                numHealthBuffsInMatrix++;
            } catch (IllegalArgumentException ignored) {}
        }
        if (numHealthBuffsInMatrix > 1) {
            throw new IllegalArgumentException("Crafting ingredients cannot contain more than 1 health buff");
        }
    }

    public MannequinSoul(ItemStack potentialMannequinSoulItem) {
        if (potentialMannequinSoulItem == null) {
            throw new IllegalArgumentException("Mannequin soul item cannot be null");
        }

        if (!potentialMannequinSoulItem.getType().equals(getMaterial())) {
            throw new IllegalArgumentException("Wrong mannequin soul material");
        }

        if (!ItemHelper.getPersistentBooleanDataDefaultFalse(potentialMannequinSoulItem, PersistentDataKeys.IS_MANNEQUIN_SOUL)) {
            throw new IllegalArgumentException("ItemStack is not marked as a mannequin soul");
        }

        ItemStack mannequinSoulItem = potentialMannequinSoulItem; // Rename for readability

        // Optional fields
        String skinUsername = ItemHelper.getPersistentStringData(mannequinSoulItem, PersistentDataKeys.SOUL_SKIN_USERNAME);
        if (skinUsername != null && !skinUsername.isEmpty()) {
            _skin = new Skin(skinUsername);
        }

        if (ItemHelper.getPersistentBooleanDataDefaultFalse(mannequinSoulItem, PersistentDataKeys.SOUL_HAS_HEALTH_BUFF)) {
            _healthBuff = new HealthBuff();
        }
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(getMaterial());

        // Populate persistent data
        ItemHelper.setPersistentBooleanData(itemStack, PersistentDataKeys.IS_MANNEQUIN_SOUL, true);

        if (getSkin() != null) {
            ItemHelper.setPersistentStringData(itemStack, PersistentDataKeys.SOUL_SKIN_USERNAME, getSkin().getUsername());
            ItemHelper.setPlayerHeadSkin(itemStack, getSkin().getUsername());
        }

        if (hasHealthBuff()) {
            ItemHelper.setPersistentBooleanData(itemStack, PersistentDataKeys.SOUL_HAS_HEALTH_BUFF, true);
        }

        // Set display info
        ItemHelper.setDisplayName(itemStack, Strings.SOUL_NAME);
        ItemHelper.setLore(itemStack, buildLore());

        return itemStack;
    }

    public Skin getSkin() {
        return _skin;
    }

    public boolean hasHealthBuff() {
        return _healthBuff != null;
    }

    public Material getMaterial() {
        return Material.PLAYER_HEAD;
    }

    private List<String> buildLore() {
        List<String> lore = new ArrayList<>();

        lore.add(Strings.SOUL_LORE_INSTRUCTIONS_HEADER);
        lore.add(" ");
        lore.add(Strings.SOUL_LORE_PROPERTIES_HEADER);

        if (getSkin() != null) {
            lore.add(Strings.SOUL_LORE_PROFILE_LABEL + getSkin().getUsername());
        }

        lore.add(Strings.SOUL_LORE_HEALTH_BUFF_LABEL + hasHealthBuff());

        return lore;
    }

    public Entity spawn(Location location) {
        if (location == null || location.getWorld() == null) {
            return null;
        }

        location = new Location(location.getWorld(), location.getX(), location.getY() + 1.0, location.getZ());

        Zombie fakeMannequin = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);

        fakeMannequin.setAI(false);

        fakeMannequin.setCustomName(getSkin().getUsername());
        fakeMannequin.setCustomNameVisible(true);

        if (hasHealthBuff()) {
            AttributeInstance maxHealthAttribute = fakeMannequin.getAttribute(Attribute.MAX_HEALTH);

            if (maxHealthAttribute != null) {
                maxHealthAttribute.setBaseValue(100.0);
                fakeMannequin.setHealth(100.0);
            }
        }

        return fakeMannequin;
    }
}
