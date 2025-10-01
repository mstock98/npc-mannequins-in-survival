package org.ferroh.nMIS.types.mannequinSoul;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.constants.PersistentDataKeys;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a mannequin in item form
 */
public class MannequinSoul {
    /**
     * Amount of HP that HealthBuff should grant
     */
    private final double _HEALTH_BUFF_MAX_HP = 100.0d;

    /**
     * Skin to use when there is none
     */
    private final String _DEFAULT_SKIN_USERNAME = "MHF_Steve";

    // Optional SoulIngredients
    private Skin _skin = null;
    private HealthBuff _healthBuff = null;
    private Anchor _anchor = null;

    /**
     * Create a new MannequinSoul not based on any ItemStack or crafting matrix
     */
    public MannequinSoul() {}

    /**
     * Create a new MannequinSoul based on a crafting matrix (crafting ingredients)
     * Throws an IllegalArgumentException if the crafting matrix is not a valid soul recipe
     * @param craftingMatrix Crafting ingredients to craft the mannequin soul
     */
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

    /**
     * Create a mannequin soul from an ItemStack that represents it
     * @param potentialMannequinSoulItem ItemStack representing the mannequin soul
     */
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

        ItemStack mannequinSoulItem = potentialMannequinSoulItem; // Renamed for readability

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

    /**
     * Convert this mannequin soul to an ItemStack
     * @return ItemStack representing this MannequinSoul
     */
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

    /**
     * Get the Skin ingredient for this mannequin soul
     * @return Skin ingredient
     */
    public Skin getSkin() {
        return _skin;
    }

    /**
     * Determine whether this mannequin soul has a health buff
     * @return True if this mannequin soul has a health buff
     */
    public boolean hasHealthBuff() {
        return _healthBuff != null;
    }

    /**
     * Determine whether this mannequin soul is anchored
     * @return True if this mannequin soul is anchored
     */
    public boolean isAnchored() {
        return _anchor != null;
    }

    /**
     * Get the material that ItemStacks that represent a mannequin soul should have
     * @return Material for mannequin soul items
     */
    public Material getMaterial() {
        return Material.PLAYER_HEAD;
    }

    /**
     * Create the ItemStack lore for this mannequin soul
     * @return ItemStack lore for this mannequin soul
     */
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

    /**
     * Spawn a mannequin entity from this mannequin soul
     * @param location Location to spawn mannequin at
     * @return Spawned mannequin entity
     */
    public Entity spawn(Location location) {
        if (location == null || location.getWorld() == null) {
            return null;
        }

        location = new Location(location.getWorld(), location.getX(), location.getY() + 1.0, location.getZ());

        Mannequin mannequin = (Mannequin) location.getWorld().spawnEntity(location, EntityType.MANNEQUIN);

        if (isAnchored()) {
            mannequin.setImmovable(true);
        }

        if (getSkin() != null) {;
            mannequin.setCustomName(getSkin().getUsername());
            mannequin.setProfile(ResolvableProfile.resolvableProfile().name(getSkin().getUsername()).build());
        } else {
            mannequin.setCustomName(Strings.DEFAULT_MANNEQUIN_DISPLAY_NAME);
            mannequin.setProfile(ResolvableProfile.resolvableProfile().name(_DEFAULT_SKIN_USERNAME).build());
        }
        mannequin.setCustomNameVisible(true);

        if (hasHealthBuff()) {
            AttributeInstance maxHealthAttribute = mannequin.getAttribute(Attribute.MAX_HEALTH);

            if (maxHealthAttribute != null) {
                maxHealthAttribute.setBaseValue(_HEALTH_BUFF_MAX_HP);
                mannequin.setHealth(_HEALTH_BUFF_MAX_HP);
            }
        }

        return mannequin;
    }
}
