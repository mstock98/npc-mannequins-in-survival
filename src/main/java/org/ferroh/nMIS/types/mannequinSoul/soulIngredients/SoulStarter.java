package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;

public class SoulStarter extends SoulIngredient {
    private ItemStack _itemStack = null;

    @Override
    public Material getMaterial() {
        return Material.PAPER;
    }

    public SoulStarter() {};

    public SoulStarter(ItemStack potentialSoulStarterItem) {
        if (potentialSoulStarterItem == null) {
            throw new IllegalArgumentException("Soul starter item is null");
        }

        if (!matchesIngredientMaterial(potentialSoulStarterItem)) {
            throw new IllegalArgumentException("Soul starter material is wrong");
        }

        ItemMeta meta = ItemHelper.getItemMeta(potentialSoulStarterItem);

        if (meta == null) {
            throw new IllegalArgumentException("Could not get item meta");
        }

        if (!meta.hasDisplayName() || !meta.getDisplayName().equalsIgnoreCase(Strings.SOUL_STARTER_NAME)) {
            throw new IllegalArgumentException("Item doesn't have the right name");
        }

        _itemStack = potentialSoulStarterItem;
    }

    @Override
    public ItemStack toItemStack() {
        if (_itemStack == null) {
            _itemStack = new ItemStack(getMaterial());
            ItemHelper.setDisplayName(_itemStack, Strings.SOUL_STARTER_NAME);
        }

        return _itemStack;
    }
}
