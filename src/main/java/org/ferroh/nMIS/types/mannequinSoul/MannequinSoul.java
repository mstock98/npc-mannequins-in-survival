package org.ferroh.nMIS.types.mannequinSoul;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.constants.PersistentDataKeys;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.*;

import java.util.ArrayList;
import java.util.List;

public class MannequinSoul {
    private final double HEALTH_BUFF_MAX_HP = 100.0d;

    private Skin _skin = null;
    private HealthBuff _healthBuff = null;
    private Anchor _anchor = null;

    public MannequinSoul() {}

    public MannequinSoul(ItemStack[] craftingMatrix) {
        for (int i = 1; i < craftingMatrix.length; i++) {
            if (!SoulIngredient.itemStackIsIngredient(craftingMatrix[i]) && !craftingMatrix[i].getType().equals(Material.AIR)) {
                throw new IllegalArgumentException("Crafting ingredients has item that isn't a MannequinSoul ingredient");
            }
        }

        int numSoulStartersInMatrix = 0;
        int numSkinsInMatrix = 0;
        int numHealthBuffsInMatrix = 0;
        int numAnchorsInMatrix = 0;

        for (ItemStack ingredient : craftingMatrix) {
            try {
                new SoulStarter(ingredient);
                numSoulStartersInMatrix++;
            } catch (IllegalArgumentException ignored) {}

            try {
                _skin = new Skin(ingredient);
                numSkinsInMatrix++;
            } catch (IllegalArgumentException ignored) {}

            try {
                _healthBuff = new HealthBuff(ingredient);
                numHealthBuffsInMatrix++;
            } catch (IllegalArgumentException ignored) {}

            try {
                _anchor = new Anchor(ingredient);
                numAnchorsInMatrix++;
            } catch (IllegalArgumentException ignored) {}
        }

        if (numSoulStartersInMatrix != 1) {
            throw new IllegalArgumentException("Crafting ingredients must contain exactly 1 soul starter");
        }

        if (numSkinsInMatrix > 1) {
            throw new IllegalArgumentException("Crafting ingredients cannot contain more than 1 skin");
        }

        if (numHealthBuffsInMatrix > 1) {
            throw new IllegalArgumentException("Crafting ingredients cannot contain more than 1 health buff");
        }

        if (numAnchorsInMatrix > 1) {
            throw new IllegalArgumentException("Crafting ingredients cannot contain more than 1 anchor");
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

        if (ItemHelper.getPersistentBooleanDataDefaultFalse(mannequinSoulItem, PersistentDataKeys.SOUL_IS_ANCHORED)) {
            _anchor = new Anchor();
        }
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(getMaterial());

        // Populate persistent data
        ItemHelper.setPersistentBooleanData(itemStack, PersistentDataKeys.IS_MANNEQUIN_SOUL, true);

        if (getSkin() != null) {
            ItemHelper.setPersistentStringData(itemStack, PersistentDataKeys.SOUL_SKIN_USERNAME, getSkin().getUsername());
            ItemHelper.setPlayerHeadSkinFromCache(itemStack, getSkin().getUsername());
        }

        if (hasHealthBuff()) {
            ItemHelper.setPersistentBooleanData(itemStack, PersistentDataKeys.SOUL_HAS_HEALTH_BUFF, true);
        }

        if (isAnchored()) {
            ItemHelper.setPersistentBooleanData(itemStack, PersistentDataKeys.SOUL_IS_ANCHORED, true);
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

    public boolean isAnchored() {
        return _anchor != null;
    }

    public Material getMaterial() {
        return Material.PLAYER_HEAD;
    }

    private List<String> buildLore() {
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + Strings.SOUL_LORE_INSTRUCTIONS_HEADER);

        if (getSkin() != null) {
            lore.add(ChatColor.YELLOW + Strings.SOUL_LORE_PROFILE_LABEL + getSkin().getUsername());
        }

        if (hasHealthBuff()) {
            lore.add(ChatColor.DARK_RED + Strings.SOUL_LORE_HEALTH_BUFF_LABEL);
        }

        if (isAnchored()) {
            lore.add(ChatColor.DARK_GRAY + Strings.SOUL_LORE_ANCHORED_LABEL);
        }

        if (lore.size() > 1) {
            lore.add(1, " ");
            lore.add(2, ChatColor.WHITE + "" + ChatColor.UNDERLINE + Strings.SOUL_LORE_PROPERTIES_HEADER);
        }

        return lore;
    }

    public Entity spawn(Location location) {
        if (location == null || location.getWorld() == null) {
            return null;
        }

        location = new Location(location.getWorld(), location.getX(), location.getY() + 1.0, location.getZ());

        Zombie fakeMannequin = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);

        if (isAnchored()) {
            fakeMannequin.setAI(false);
        }

        if (getSkin() != null) {
            fakeMannequin.setCustomName(getSkin().getUsername());
            fakeMannequin.setCustomNameVisible(true);
        }

        if (hasHealthBuff()) {
            AttributeInstance maxHealthAttribute = fakeMannequin.getAttribute(Attribute.MAX_HEALTH);

            if (maxHealthAttribute != null) {
                maxHealthAttribute.setBaseValue(100.0);
                fakeMannequin.setHealth(100.0);
            }
        }

        return fakeMannequin;
    }

    public String getSummonCommand(Location location) {
        if (location == null) {
            return null;
        }

        return "summon minecraft:mannequin " +
                location.getX() + " " +
                location.getY() + " " +
                location.getZ() + " " +
                getMannequinNBT();
    }

    private String getMannequinNBT() {
        if (getSkin() == null && !hasHealthBuff()) {
            return "";
        }

        StringBuilder nbtSB = new StringBuilder();

        if (getSkin() != null) {
            appendCommaIfNotEmpty(nbtSB);
            nbtSB.append("profile:\"");
            nbtSB.append(getSkin().getUsername());
            nbtSB.append("\"");
        }

        if (isAnchored()) {
            appendCommaIfNotEmpty(nbtSB);
            nbtSB.append("immovable:true");
        }

        if (hasHealthBuff()) {
            appendCommaIfNotEmpty(nbtSB);
            nbtSB.append("attributes:[{id:max_health,base:");
            nbtSB.append(HEALTH_BUFF_MAX_HP);
            nbtSB.append("}]");
        }

        nbtSB.insert(0, '{');
        nbtSB.append('}');

        return nbtSB.toString();
    }

    private void appendCommaIfNotEmpty(StringBuilder sb) {
        if (sb == null || sb.isEmpty()) {
            return;
        }

        sb.append(',');
    }
}
