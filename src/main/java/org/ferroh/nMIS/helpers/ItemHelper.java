package org.ferroh.nMIS.helpers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.Skin;

import java.util.List;

/**
 * Class containing some static helper methods for operating on ItemStack objects
 */
public class ItemHelper {
    /**
     * Sets the display name of an ItemStack
     * @param item ItemStack to set the display name
     * @param displayName Display name that the item should have
     */
    public static void setDisplayName(ItemStack item, String displayName) {
        if (item == null || displayName == null) {
            return;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null) {
            return;
        }

        meta.setDisplayName(displayName);

        setItemMeta(item, meta);
    }

    /**
     * Get the display name that an ItemStack has
     * @param item ItemStack to get display name from
     * @return Display name from item stack, null if there is none
     */
    public static String getDisplayName(ItemStack item) {
        if (item == null) {
            return null;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null || !meta.hasDisplayName()) {
            return null;
        }

        return meta.getDisplayName();
    }

    /**
     * Get the persistent string data stored in an ItemStack by the NamespacedKey
     * @param item ItemStack that has the persistent data
     * @param key NamespacedKey by which the data is stored
     * @return Persistent string data stored in the item or null if no data is stored by the key
     */
    public static String getPersistentStringData(ItemStack item, NamespacedKey key) {
        if (item == null || key == null) {
            return null;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null) {
            return null;
        }

        PersistentDataContainer persistentData = meta.getPersistentDataContainer();

        String stringData;
        try {
            stringData = persistentData.get(key, PersistentDataType.STRING);
        } catch (NullPointerException e) {
            return null;
        }

        return stringData;
    }

    /**
     * Set the persistent string data stored in an ItemStack by the NamespacedKey.
     * Does nothing if either param is null.
     * @param item ItemStack to store the persistent data in
     * @param key NamespacedKey for the persistent data to store
     * @param stringData Persistent string data that shall be stored
     */
    public static void setPersistentStringData(ItemStack item, NamespacedKey key, String stringData) {
        if (item == null || key == null || stringData == null) {
            return;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null) {
            return;
        }

        PersistentDataContainer persistentData = meta.getPersistentDataContainer();

        persistentData.set(key, PersistentDataType.STRING, stringData);

        setItemMeta(item, meta);
    }

    /**
     * Get the persistent boolean data stored in an ItemStack by the NamespacedKey
     * @param item ItemStack that has the persistent data
     * @param key NamespacedKey by which the data is stored
     * @return Persistent boolean data stored in the item or null if no data is stored by the key
     */
    public static Boolean getPersistentBooleanData(ItemStack item, NamespacedKey key) {
        if (item == null || key == null) {
            return null;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null) {
            return null;
        }

        PersistentDataContainer persistentData = meta.getPersistentDataContainer();

        Boolean booleanData;
        try {
            booleanData = persistentData.get(key, PersistentDataType.BOOLEAN);
        } catch (NullPointerException e) {
            return null;
        }

        return booleanData;
    }

    /**
     * Get the persistent boolean data stored in an ItemStack by the NamespacedKey
     * @param item ItemStack that has the persistent data
     * @param key NamespacedKey by which the data is stored
     * @return Persistent string data stored in the item or false if no data is stored by the key
     */
    public static boolean getPersistentBooleanDataDefaultFalse(ItemStack item, NamespacedKey key) {
        Boolean nullableData = getPersistentBooleanData(item, key);

        return nullableData != null && nullableData;
    }

    /**
     * Set the persistent boolean data stored in an ItemStack by the NamespacedKey.
     * Does nothing if either param is null.
     * @param item ItemStack to store the persistent data in
     * @param key NamespacedKey for the persistent data to store
     * @param booleanData Persistent boolean data that shall be stored
     */
    public static void setPersistentBooleanData(ItemStack item, NamespacedKey key, boolean booleanData) {
        if (item == null || key == null) {
            return;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null) {
            return;
        }

        PersistentDataContainer persistentData = meta.getPersistentDataContainer();

        persistentData.set(key, PersistentDataType.BOOLEAN, booleanData);

        setItemMeta(item, meta);
    }

    /**
     * Gets the ItemMeta from an ItemStack.
     * Wrapper method to make ItemMeta-related methods more unit-testable.
     * @param item ItemStack to get the ItemMeta from
     * @return ItemMeta from item
     */
    public static ItemMeta getItemMeta(ItemStack item) {
        return item.getItemMeta();
    }

    /**
     * Sets the ItemMeta from an ItemStack
     * Wrapper method to make ItemMeta-related methods more unit-testable.
     * @param item ItemStack to set the ItemMeta on
     * @param meta ItemMeta to set on item
     */
    public static void setItemMeta(ItemStack item, ItemMeta meta) {
        item.setItemMeta(meta);
    }

    /**
     * Sets the lore on a given ItemStack
     * @param item ItemStack to set lore on
     * @param lore Lore to set on the ItemStack
     */
    public static void setLore(ItemStack item, List<String> lore) {
        if (item == null || lore == null) {
            return;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null) {
            return;
        }

        meta.setLore(lore);
        setItemMeta(item, meta);

    }

    /**
     * Determines if an ItemStack is null or has material that is Material.AIR
     * @param itemStack Item to test if it is null or air
     * @return True if the item is null or air
     */
    public static boolean isNullOrAir(ItemStack itemStack) {
        return (itemStack == null) || (itemStack.getType().equals(Material.AIR));
    }

    /**
     * Set a player head to use the specified player skin
     * @param playerHead Head to apply skin to
     * @param skin Skin to apply to the player head
     */
    public static void setPlayerHeadSkin(ItemStack playerHead, Skin skin) {
        if (playerHead == null || playerHead.getType() != Material.PLAYER_HEAD) {
            return;
        }

        if (skin == null) {
            return;
        }

        SkullMeta meta = (SkullMeta) getItemMeta(playerHead);

        if (meta == null) {
            return;
        }

        meta.setOwnerProfile(skin.getStaticProfile());

        playerHead.setItemMeta(meta);
    }
}
