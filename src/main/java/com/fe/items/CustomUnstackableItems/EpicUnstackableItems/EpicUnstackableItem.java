package com.fe.items.CustomUnstackableItems.EpicUnstackableItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;

public abstract class EpicUnstackableItem extends CustomUnstackableItem{

    public EpicUnstackableItem(final Material material, final String name, final String[] enchants ,final String lore) {
        super(material, true); // epic items will always glow
        this.addName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + name);
        for(String enchant : enchants)
            this.addEnchantText(enchant);
        this.addLore("");
        this.addLore(ChatColor.DARK_AQUA + lore);
        this.addLore(ChatColor.DARK_PURPLE + "Epic");
    }
}
