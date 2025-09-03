package org.ferroh.nMIS.types.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.helpers.ItemHelper;

public class Skin extends SoulIngredient {
    private String _username;
    private ItemStack _itemStack;

    public Skin(ItemStack potentialSkinItem) {
        if (potentialSkinItem == null) {
            throw new IllegalStateException("ItemStack is null");
        }

        if (!matchesIngredientMaterial(potentialSkinItem)) {
            throw new IllegalStateException("Wrong material");
        }

        String username = ItemHelper.getDisplayName(potentialSkinItem);

        if (username == null) {
            throw new IllegalStateException("Skin username is null");
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
}
