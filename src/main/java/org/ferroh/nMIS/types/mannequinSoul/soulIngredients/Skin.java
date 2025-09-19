package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.helpers.ItemHelper;

public class Skin extends SoulIngredient {
    private final String _username;
    private ItemStack _itemStack;

    public Skin(String skinUsername) {
        _username = skinUsername;
    }

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

    @Override
    public Material getMaterial() {
        return Material.NAME_TAG;
    }

    @Override
    public ItemStack toItemStack() {
        if (_itemStack == null) {
            _itemStack = new ItemStack(getMaterial());
            ItemHelper.setDisplayName(_itemStack, _username);
        }

        return _itemStack;
    }

    public String getUsername() {
        return _username;
    }

    public static boolean usernameIsValid(String username) {
        if (username == null) {
            return false;
        }

        return username.matches("[a-zA-Z\\d_]{3,16}");
    }
}
