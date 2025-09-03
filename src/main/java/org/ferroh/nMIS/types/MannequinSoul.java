package org.ferroh.nMIS.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MannequinSoul {
    private String _profile = null;

    public MannequinSoul() {}

    public MannequinSoul(String profile) {
        setProfile(profile);
    }

    public ItemStack toItemStack() {
        return new ItemStack(Material.PAPER);
    }

    public String getProfile() {
        return _profile;
    }

    public void setProfile(String profile) {
        _profile = profile;
    }
}
