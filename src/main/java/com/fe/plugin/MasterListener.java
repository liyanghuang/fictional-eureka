package com.fe.plugin;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import com.fe.enchants.CustomEnchantManager;

public class MasterListener implements Listener{
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ItemStack stick = new ItemStack(Material.STICK);
        CustomEnchantManager.addEnchantText(stick, "Hello what's up");
        CustomEnchantManager.addEnchantText(stick, "not much what about u");
        CustomEnchantManager.addEnchantText(stick, "lmfao not much either");
        CustomEnchantManager.addGlow(stick);
        event.getPlayer().getInventory().addItem(stick);
    }
}
