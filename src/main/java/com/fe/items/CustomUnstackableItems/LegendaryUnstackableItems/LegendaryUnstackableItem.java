package com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;

public class LegendaryUnstackableItem extends CustomUnstackableItem{

    public LegendaryUnstackableItem(final Material material, final String name, final String[] enchants ,final String lore) {
        super(material, true); // legendary items will always glow
        this.addName(ChatColor.GOLD + "" + ChatColor.BOLD + name);
        for(String enchant : enchants)
            this.addEnchantText(enchant);
        this.addLore(ChatColor.DARK_AQUA + lore);
    }
}
