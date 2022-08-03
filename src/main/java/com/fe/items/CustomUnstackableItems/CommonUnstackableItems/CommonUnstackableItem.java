package com.fe.items.CustomUnstackableItems.CommonUnstackableItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;

public class CommonUnstackableItem extends CustomUnstackableItem{

    public CommonUnstackableItem(final Material material, final boolean hasGlow, final String name, final String lore) {
        super(material, hasGlow);
        this.addName(ChatColor.DARK_GREEN + name);
        this.addLore(ChatColor.DARK_AQUA + lore);
        this.addLore(ChatColor.DARK_GREEN + "Common");
    }
}
