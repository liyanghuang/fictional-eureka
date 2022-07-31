package com.fe.enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.fe.exceptions.BadEnchantException;

public class CustomEnchantManager {

    private static final int DEFAULT_WRAPPING_LENGTH = 30;

    public static ItemStack addGlow(final ItemStack itemStack) {

        itemStack.addUnsafeEnchantment((itemStack.getType() == Material.BOW) ? Enchantment.PROTECTION_ENVIRONMENTAL : Enchantment.ARROW_INFINITE, 1);
        // hides the enchantments
        final ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        // returns the new itemstack
        return itemStack;
    }

    public static ItemStack addEnchantText(final ItemStack itemStack, final String enchantText) throws BadEnchantException{
        
        final ItemMeta meta = itemStack.getItemMeta();
        List<String> previousLore = meta.getLore();
        if(previousLore == null)
            previousLore = new ArrayList<>();
        if(enchantText.length() >= 30)
            throw new BadEnchantException("Enchantment String length is too long");
        previousLore.add(ChatColor.GRAY + enchantText);
        meta.setLore(previousLore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    // To add color, add a ChatColor prepended to the string, use this for adding natural and unnatural enchants
    public static ItemStack addLoreText(final ItemStack itemStack, final String loreText) {
        
        final ItemMeta meta = itemStack.getItemMeta();
        List<String> previousLore = meta.getLore();
        if(previousLore == null)
            previousLore = new ArrayList<>();
        final String[] wrappedText = wrapString(loreText, DEFAULT_WRAPPING_LENGTH).split("\\r?\\n");
        previousLore.add(""); // add empty line to separate from enchantments
        for (final String wrappedLine : wrappedText)
            previousLore.add(ChatColor.getLastColors(wrappedText[0]) + wrappedLine);
        meta.setLore(previousLore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    // add unsafe natural enchants
    public static ItemStack addUnsafeEnchantment(final ItemStack itemStack, final Enchantment enchantment, final int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        // hides the enchantments
        final ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        // returns the new itemstack
        return itemStack;
    }

    public static boolean hasEnchantText(final ItemStack itemStack, final String enchantText) {
        final ItemMeta meta = itemStack.getItemMeta();
        if(meta == null)
            return false;
        List<String> previousLore = meta.getLore();
        if(previousLore == null)
            return false; // false if no lore
        for(final String loreLine : previousLore) {
            if(ChatColor.stripColor(loreLine).equals(enchantText))
                return true;
        }
        return false;
    }

    public static ItemStack addNewName(final ItemStack itemStack, final String name) {
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    // recursively wrap lore string in case it's too long
    public static String wrapString(final String s, final int wrappingLength) {
        if(s.length() < wrappingLength)
            return s;
        int spaceIndex = s.substring(0, wrappingLength).lastIndexOf(" ");
        if(spaceIndex != -1 && spaceIndex < wrappingLength)
            return s.substring(0, spaceIndex) + "\n" + wrapString(s.substring(spaceIndex + 1), wrappingLength);
        return s.substring(0, wrappingLength) + "\n" + wrapString(s.substring(wrappingLength), wrappingLength);
    }
}