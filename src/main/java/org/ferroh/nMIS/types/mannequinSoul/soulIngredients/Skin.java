package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Soul ingredient class for Skin
 * The Skin ingredient will determine what player skin the mannequin will have
 */
public class Skin extends SoulIngredient {
    /**
     * Texture string for the Steve skin
     */
    private static final String _STEVE_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTc2MTE2NjUyNTQ3MCwKICAicHJvZmlsZUlkIiA6ICJjMDZmODkwNjRjOGE0OTExOWMyOWVhMWRiZDFhYWI4MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNSEZfU3RldmUiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVjNGVlNWNlMjBhZWQ5ZTMzZTg2NmM2NmNhYTM3MTc4NjA2MjM0YjM3MjEwODRiZjAxZDEzMzIwZmIyZWIzZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";

    /**
     * Skin object for the Steve skin
     */
    public static final Skin STEVE = new Skin(_STEVE_TEXTURE, Strings.DEFAULT_MANNEQUIN_DISPLAY_NAME);

    /**
     * Cache for player profiles fetched from Minecraft/Mojang APIs
     */
    private static HashMap<String, PlayerProfile> _profileCache;

    /**
     * Username of the owner of the skin
     */
    private String _username;

    /**
     * ItemStack representing this soul ingredient
     */
    private ItemStack _itemStack;

    /**
     * Texture string for this Skin. Once set, the texture string does not change.
     */
    private String _staticTexture;

    /**
     * Create a Skin object from a texture string
     * @param staticTexture Texture to use for this Skin. If null, the Steve texture will be used.
     */
    public Skin(String staticTexture) {
        _staticTexture = staticTexture;
    }

    /**
     * Create a Skin object from a texture string and a username.
     * @param staticTexture Texture to use for this Skin. If null, the texture will be retrieved via username if cached. (see Skin#cacheProfileAsync).
     * @param username Username of the skin. Does not have to match the texture.
     */
    public Skin(String staticTexture, String username) {
        this(staticTexture);
        _username = username;
    }

    /**
     * Create a Skin object based on an ItemStack that represents a Skin
     * @param potentialSkinItem ItemStack to create the skin object from
     */
    public Skin(ItemStack potentialSkinItem) {
        if (potentialSkinItem == null) {
            throw new IllegalArgumentException("ItemStack is null");
        }

        if (!matchesIngredientMaterial(potentialSkinItem)) {
            throw new IllegalArgumentException("Wrong material");
        }

        String username = ItemHelper.getDisplayName(potentialSkinItem);

        if (!usernameIsValid(username)) {
            throw new IllegalArgumentException("Invalid minecraft username");
        }

        _username = username;
        _itemStack = potentialSkinItem;
    }

    /**
     * Get the material that ItemStacks representing a Skin have
     * @return Material for the Skin soul ingredient
     */
    @Override
    public Material getMaterial() {
        return Material.NAME_TAG;
    }

    /**
     * Get the ItemStack representing this Skin object
     * @return ItemStack for this Skin soul ingredient
     */
    @Override
    public ItemStack toItemStack() {
        if (_itemStack == null) {
            _itemStack = new ItemStack(getMaterial());
            ItemHelper.setDisplayName(_itemStack, getUsername());
        }

        return _itemStack;
    }

    /**
     * Get the player username for this skin
     * @return Player username
     */
    public String getUsername() {
        return _username;
    }

    /**
     * Get the texture for this Skin. If the static texture is set, this texture will always be the same for this Skin object.
     * Attempts to retrieve texture from profile cache (see Skin#cacheProfileAsync) if the texture is not set for this object.
     * If the texture is not in the profile cache, the Steve texture will be returned,
     * and subsequent calls to this method will attempt to look for the texture again in the profile cache.
     * @return Texture for Skin. Will never be null or empty.
     */
    public String getStaticTexture() {
        if (_staticTexture == null || _staticTexture.isEmpty()) {
            if (getCachedProfile() != null) {
                for (ProfileProperty property : getCachedProfile().getProperties()) {
                    if (property.getName().equals("textures")) {
                        _staticTexture = property.getValue();
                        break;
                    }
                }
            }

            if (_staticTexture == null || _staticTexture.isEmpty()) {
                return _STEVE_TEXTURE;
            }
        }

        return _staticTexture;
    }

    /**
     * Get a player profile based on the static texture set for this skin.
     * Player profile will have a different UUID every time.
     * @return PlayerProfile object with the static texture as its skin
     */
    public PlayerProfile getStaticProfile() {
        String staticTexture = getStaticTexture();

        PlayerProfile staticProfile = Bukkit.createProfile(UUID.randomUUID(), null); // Random UUID since UUID value doesn't matter other than being non-null
        staticProfile.setProperty(new ProfileProperty("textures", staticTexture));

        return staticProfile;
    }

    /**
     * Attempt to retrieve player profile and texture information based on a Minecraft Java username
     * asynchronously from Minecraft/Mojang and cache that information.
     */
    public void cacheProfileAsync() {
        if (!usernameIsValid(getUsername())) {
            return;
        }

        if (_profileCache == null) {
            _profileCache = new HashMap<>();
        }

        PlayerProfile profile = _profileCache.get(getUsername());

        if (profile == null) {
            profile = Bukkit.createProfile(Bukkit.getOfflinePlayer(getUsername()).getUniqueId(), getUsername());
        }

        profile.update()
                .orTimeout(10, TimeUnit.SECONDS)
                .whenComplete((updatedProfile, ex) -> {
                    Bukkit.getScheduler().runTask(NMIS.getPlugin(), () -> {
                        _profileCache.remove(getUsername());

                        _profileCache.put(getUsername(), updatedProfile);
                    });
                });
    }

    /**
     * Retrieve the cached player profile and texture data for the username set on this Skin
     * @return Cached PlayerProfile object
     */
    private PlayerProfile getCachedProfile() {
        if (_profileCache == null || getUsername() == null) {
            return null;
        }

        return _profileCache.get(getUsername());
    }

    /**
     * Helper method to determine if a username is a valid Minecraft Java Edition username
     * @param username Username to check for validity
     * @return True of the username is valid
     */
    public boolean usernameIsValid(String username) {
        if (username == null) {
            return false;
        }

        return username.matches("[a-zA-Z\\d_]{3,16}");
    }
}
