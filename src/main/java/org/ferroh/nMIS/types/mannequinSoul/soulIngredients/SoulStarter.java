package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ferroh.nMIS.constants.Strings;
import org.ferroh.nMIS.helpers.ItemHelper;

/**
 * Soul ingredient class for the SoulStarter
 * A soul starter is used in the crafting recipe to indicate that the player wants to craft a mannequin soul
 */
public class SoulStarter extends SoulIngredient {
    /**
     * ItemStack representing this soul ingredient
     */
    private ItemStack _itemStack = null;

    /**
     * Return the material that represents this soul ingredient
     * @return Material for the soul ingredient
     */
    @Override
    public Material getMaterial() {
        return Material.PAPER;
    }

    /**
     * Create a new SoulStarter not based on any ItemStack
     */
    public SoulStarter() {};

    /**
     * Create a new SoulStarter based on an ItemStack.
     * Throws an IllegalArgumentException of the provided ItemStack does not represent a SoulStarter.
     * @param potentialSoulStarterItem ItemStack representing a SoulStarter soul ingredient
     */
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
            throw new IllegalArgumentException("Item doesn't have the right name. Expected: " + Strings.SOUL_STARTER_NAME + " Actual: " + meta.getDisplayName());
        }

        _itemStack = potentialSoulStarterItem;
    }

    /**
     * Convert this soul ingredient into an ItemStack that represents it
     * @return ItemStack that represents this soul ingredient
     */
    @Override
    public ItemStack toItemStack() {
        if (_itemStack == null) {
            _itemStack = new ItemStack(getMaterial());
            ItemHelper.setDisplayName(_itemStack, Strings.SOUL_STARTER_NAME);
        }

        return _itemStack;
    }
}
