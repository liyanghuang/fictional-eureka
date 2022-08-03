package com.fe.items.CustomUnstackableItems;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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

    public static void setCooldown(final NamespacedKey key, final ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, System.currentTimeMillis());
        item.setItemMeta(meta);
    }

    public static boolean isAvailable(final NamespacedKey key, final ItemStack item, final int cooldown) {
        final ItemMeta meta = item.getItemMeta();
        if(meta.getPersistentDataContainer().has(key, PersistentDataType.LONG))
            if((System.currentTimeMillis() - meta.getPersistentDataContainer().get(key, PersistentDataType.LONG)) > cooldown)
                return true;
            else
                return false;
        else
            return true;
    }
}
