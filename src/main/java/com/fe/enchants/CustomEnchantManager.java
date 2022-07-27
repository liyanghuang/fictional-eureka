package com.fe.enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomEnchantManager {

    public static ItemStack addGlow(ItemStack itemStack) {

        itemStack.addUnsafeEnchantment((itemStack.getType() == Material.BOW) ? Enchantment.PROTECTION_ENVIRONMENTAL : Enchantment.ARROW_INFINITE, 1);
        // hides the enchantments
        final ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        // returns the new itemstack
        return itemStack;
    }

    public static ItemStack addEnchantText(ItemStack itemStack, String enchantText) {
        
        final ItemMeta meta = itemStack.getItemMeta();
        List<String> previousLore = meta.getLore();
        if(previousLore == null)
            previousLore = new ArrayList<>();
        previousLore.add(enchantText);
        meta.setLore(previousLore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}