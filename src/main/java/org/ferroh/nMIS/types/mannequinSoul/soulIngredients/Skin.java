package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.helpers.ItemHelper;

/**
 * Soul ingredient class for Skin
 * The Skin ingredient will determine what player skin the mannequin will have
 */
public class Skin extends SoulIngredient {
    /**
     * Player username of the skin
     */
    private final String _username;

    /**
     * ItemStack representing this soul ingredient
     */
    private ItemStack _itemStack;

    /**
     * Create a Skin object based on a player username to use as the skin
     * @param skinUsername Player username
     */
    public Skin(String skinUsername) {
        _username = skinUsername;
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
            ItemHelper.setDisplayName(_itemStack, _username);
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
     * Static helper method to determine if a username is a valid Minecraft Java Edition username
     * @param username Username to check for validity
     * @return True of the username is valid
     */
    public static boolean usernameIsValid(String username) {
        if (username == null) {
            return false;
        }

        return username.matches("[a-zA-Z\\d_]{3,16}");
    }
}
