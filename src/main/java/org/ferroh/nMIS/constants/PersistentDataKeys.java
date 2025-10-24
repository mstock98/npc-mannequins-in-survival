package org.ferroh.nMIS.constants;

import org.bukkit.NamespacedKey;
import org.ferroh.nMIS.NMIS;

/**
 * Namespaced keys for getting/setting persistent data inside ItemStacks & Entities
 */
public class PersistentDataKeys {
    public static final NamespacedKey IS_MANNEQUIN_SOUL = new NamespacedKey(NMIS.getPlugin(), "isMannequinSoul");

    public static final NamespacedKey SOUL_HAS_HEALTH_BUFF = new NamespacedKey(NMIS.getPlugin(), "soulHasHealthBuff");
    public static final NamespacedKey SOUL_IS_ANCHORED = new NamespacedKey(NMIS.getPlugin(), "soulIsAnchored");

    public static final NamespacedKey SKIN_USERNAME = new NamespacedKey(NMIS.getPlugin(), "skinUsername");

    public static final NamespacedKey IS_DEAD_SLOT_ITEM = new NamespacedKey(NMIS.getPlugin(), "isDeadSlotItem");

    public static final NamespacedKey MANNEQUIN_STATIC_TEXTURE = new NamespacedKey(NMIS.getPlugin(), "mannequinStaticTexture");
    public static final NamespacedKey MANNEQUIN_ENTITY_HAS_HEALTH_BUFF = new NamespacedKey(NMIS.getPlugin(), "mannequinEntityHasHealthBuff");
    public static final NamespacedKey MANNEQUIN_ENTITY_IS_ANCHORED = new NamespacedKey(NMIS.getPlugin(), "mannequinEntityIsAnchored");
    public static final NamespacedKey MANNEQUIN_ENTITY_DISPLAY_NAME = new NamespacedKey(NMIS.getPlugin(), "mannequinEntityDisplayName");
}
