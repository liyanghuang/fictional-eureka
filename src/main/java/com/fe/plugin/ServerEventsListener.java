package com.fe.plugin;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class ServerEventsListener implements Listener{
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ItemStack stick = new ItemStack(Material.STICK);
        event.getPlayer().getInventory().addItem(stick);
    }
}
