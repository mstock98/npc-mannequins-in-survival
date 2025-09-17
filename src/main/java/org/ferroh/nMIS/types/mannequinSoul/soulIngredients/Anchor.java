package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Anchor extends SoulIngredient {
    private ItemStack _itemStack = null;

    public Anchor() {}

    public Anchor(ItemStack itemStack) {
        if (itemStack == null) {
            throw new IllegalArgumentException("ItemStack is null");
        }

        if (!matchesIngredientMaterial(itemStack)) {
            throw new IllegalArgumentException("Wrong material");
        }

        _itemStack = itemStack;
    }

    @Override
    public Material getMaterial() {
        return Material.ANVIL;
    }

    @Override
    public ItemStack toItemStack() {
        if (_itemStack == null) {
            _itemStack = new ItemStack(getMaterial());
        }

        return _itemStack;
    }
}
