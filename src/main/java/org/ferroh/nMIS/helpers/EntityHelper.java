package org.ferroh.nMIS.helpers;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Class containing some static helper methods for operating on Entity objects
 */
public class EntityHelper {
    /**
     * Get the persistent string data stored in an Entity by the NamespacedKey
     * @param entity Entity that has the persistent data
     * @param key NamespacedKey by which the data is stored
     * @return Persistent string data stored in the item or null if no data is stored by the key
     */
    public static String getPersistentStringData(Entity entity, NamespacedKey key) {
        if (entity == null || key == null) {
            return null;
        }

        return entity.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    /**
     * Set the persistent string data stored in an Entity by the NamespacedKey.
     * Does nothing if either param is null.
     * @param entity Entity to store the persistent data in
     * @param key NamespacedKey for the persistent data to store
     * @param data Persistent string data that shall be stored
     */
    public static void setPersistentStringData(Entity entity, NamespacedKey key, String data) {
        if (entity == null || key == null || data == null || data.isEmpty()) {
            return;
        }

        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        persistentDataContainer.set(key, PersistentDataType.STRING, data);
    }

    /**
     * Get the persistent boolean data stored in an Entity by the NamespacedKey
     * @param entity Entity that has the persistent data
     * @param key NamespacedKey by which the data is stored
     * @return Persistent boolean data stored in the item or null if no data is stored by the key
     */
    public static boolean getPersistentBooleanData(Entity entity, NamespacedKey key) {
        if (entity == null || key == null) {
            throw new IllegalArgumentException("Entity and key must not be null");
        }

        Boolean data = entity.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN);

        if (data == null) {
            throw new IllegalStateException("No boolean data found for key");
        }

        return data;
    }

    /**
     * Get the persistent boolean data stored in an Entity by the NamespacedKey
     * @param entity Entity that has the persistent data
     * @param key NamespacedKey by which the data is stored
     * @return Persistent string data stored in the item or false if no data is stored by the key
     */
    public static boolean getPersistentBooleanDataDefaultFalse(Entity entity, NamespacedKey key) {
        boolean data;
        try {
            data = getPersistentBooleanData(entity, key);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return false;
        }

        return data;
    }

    /**
     * Set the persistent boolean data stored in an Entity by the NamespacedKey.
     * Does nothing if either param is null.
     * @param entity Entity to store the persistent data in
     * @param key NamespacedKey for the persistent data to store
     * @param data Persistent boolean data that shall be stored
     */
    public static void setPersistentBooleanData(Entity entity, NamespacedKey key, boolean data) {
        if (entity == null || key == null) {
            return;
        }

        entity.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, data);
    }
}
