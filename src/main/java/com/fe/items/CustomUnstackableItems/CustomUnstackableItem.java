package com.fe.items.CustomUnstackableItems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.fe.enchants.CustomEnchantManager;

public abstract class CustomUnstackableItem extends ItemStack{

    public CustomUnstackableItem(final Material material, final boolean hasGlow) {
        super(material, 1);
        if(hasGlow)
            CustomEnchantManager.addGlow(this);
    }

    protected void addName(final String name) {
        CustomEnchantManager.addNewName(this, name);
    }

    protected void addLore(final String lore) {
        CustomEnchantManager.addLoreText(this, lore);
    }

    protected void addEnchantText(final String enchant) {
        CustomEnchantManager.addEnchantText(this, enchant);
    }
}
