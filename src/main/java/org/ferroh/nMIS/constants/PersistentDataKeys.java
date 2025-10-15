package org.ferroh.nMIS.constants;

import org.bukkit.NamespacedKey;
import org.ferroh.nMIS.NMIS;

/**
 * Namespaced keys for getting/setting persistent data inside ItemStacks & Entites
 */
public class PersistentDataKeys {
    public static final NamespacedKey IS_MANNEQUIN_SOUL = new NamespacedKey(NMIS.getPlugin(), "isMannequinSoul");

    public static final NamespacedKey SOUL_SKIN_USERNAME = new NamespacedKey(NMIS.getPlugin(), "soulSkinUsername");
    public static final NamespacedKey SOUL_HAS_HEALTH_BUFF = new NamespacedKey(NMIS.getPlugin(), "soulHasHealthBuff");
    public static final NamespacedKey SOUL_IS_ANCHORED = new NamespacedKey(NMIS.getPlugin(), "soulIsAnchored");

    public static final NamespacedKey IS_DEAD_SLOT_ITEM = new NamespacedKey(NMIS.getPlugin(), "isDeadSlotItem");

    public static final NamespacedKey MANNEQUIN_ENTITY_SKIN_USERNAME = new NamespacedKey(NMIS.getPlugin(), "mannequinEntitySkinUsername");
    public static final NamespacedKey MANNEQUIN_ENTITY_HAS_HEALTH_BUFF = new NamespacedKey(NMIS.getPlugin(), "mannequinEntityHasHealthBuff");
    public static final NamespacedKey MANNEQUIN_ENTITY_IS_ANCHORED = new NamespacedKey(NMIS.getPlugin(), "mannequinEntityIsAnchored");
}
