package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Soul ingredient class for the Anchor.
 * An anchor means that the mannequin will not move or fall
 */
public class Anchor extends SoulIngredient {
    /**
     * ItemStack representing this soul ingredient
     */
    private ItemStack _itemStack = null;

    /**
     * Create a new Anchor not based on any ItemStack
     */
    public Anchor() {}

    /**
     * Create a new Anchor based on an ItemStack.
     * Throws an IllegalArgumentException of the provided ItemStack does not represent an Anchor.
     * @param itemStack ItemStack representing an Anchor soul ingredient
     */
    public Anchor(ItemStack itemStack) {
        if (itemStack == null) {
            throw new IllegalArgumentException("ItemStack is null");
        }

        if (!matchesIngredientMaterial(itemStack)) {
            throw new IllegalArgumentException("Wrong material");
        }

        _itemStack = itemStack;
    }

    /**
     * Return the material that represents this soul ingredient
     * @return Material for the soul ingredient
     */
    @Override
    public Material getMaterial() {
        return Material.ANVIL;
    }

    /**
     * Convert this soul ingredient into an ItemStack that represents it
     * @return ItemStack that represents this soul ingredient
     */
    @Override
    public ItemStack toItemStack() {
        if (_itemStack == null) {
            _itemStack = new ItemStack(getMaterial());
        }

        return _itemStack;
    }
}
