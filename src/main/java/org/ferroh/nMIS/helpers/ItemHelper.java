package org.ferroh.nMIS.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.ferroh.nMIS.NMIS;

import java.util.List;

public class ItemHelper {
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

    public static boolean getPersistentBooleanDataDefaultFalse(ItemStack item, NamespacedKey key) {
        Boolean nullableData = getPersistentBooleanData(item, key);

        return nullableData != null && nullableData;
    }

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

    // Wrapper method to make ItemMeta-related methods more unit-testable.
    public static ItemMeta getItemMeta(ItemStack item) {
        return item.getItemMeta();
    }

    // Wrapper method to make ItemMeta-related methods more unit-testable.
    public static void setItemMeta(ItemStack item, ItemMeta meta) {
        item.setItemMeta(meta);
    }

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

    public static void appendLore(ItemStack item, String loreToAppend) {
        appendLore(item, List.of(loreToAppend));
    }

    public static void appendLore(ItemStack item, List<String> loreToAppend) {
        if (item == null || loreToAppend == null) {
            return;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null) {
            return;
        }

        List<String> existingLore = meta.getLore();

        if (existingLore != null) {
            existingLore.addAll(loreToAppend);
        } else {
            existingLore = loreToAppend;
        }

        meta.setLore(existingLore);
        setItemMeta(item, meta);

    }

    public static List<String> getLore(ItemStack item) {
        if (item == null) {
            return null;
        }

        ItemMeta meta = getItemMeta(item);

        if (meta == null) {
            return null;
        }

        return meta.getLore();
    }

    public static void setPlayerHeadSkin(ItemStack playerHead, String skinUsername) {
        if (playerHead == null || skinUsername == null || skinUsername.isEmpty()) {
            return;
        }

        if (playerHead.getType() != Material.PLAYER_HEAD) {
            return;
        }

        SkullMeta meta = (SkullMeta) getItemMeta(playerHead);

        if (meta == null) {
            return;
        }

        PlayerProfile profile = Bukkit.createPlayerProfile(skinUsername);

        profile.update().thenAccept(updated -> {
           Bukkit.getScheduler().runTask(NMIS.getPlugin(), () -> {
              meta.setOwnerProfile(updated);
              meta.setOwningPlayer(skinUsername);
              playerHead.setItemMeta(meta);
           });
        });
    }
}
