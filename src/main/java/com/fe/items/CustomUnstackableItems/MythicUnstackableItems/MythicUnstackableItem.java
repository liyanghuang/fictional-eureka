package com.fe.items.CustomUnstackableItems.MythicUnstackableItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;

public abstract class MythicUnstackableItem extends CustomUnstackableItem{

    public MythicUnstackableItem(final Material material, final String name, final String[] enchants ,final String lore) {
        super(material, true); // legendary items will always glow
        this.addName(name); // mythic items will have their own name set with color codes
        for(String enchant : enchants)
            this.addEnchantText(enchant);
        this.addLore("");
        this.addLore(ChatColor.DARK_AQUA + lore);
        this.addLore(ChatColor.DARK_RED + "Mythical");
    }
}