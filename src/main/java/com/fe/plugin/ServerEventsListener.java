package com.fe.plugin;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.fe.main.ServerMain;

public class ServerEventsListener implements Listener{

    private final ServerMain serverMain;

    public ServerEventsListener(ServerMain serverMain) {
        this.serverMain = serverMain;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ItemStack stick = new ItemStack(Material.STICK);
        ItemStack sword = new ItemStack(Material.IRON_SWORD, 64);
        serverMain.getCustomEnchantManager().addEnchantText(stick, "hello there");
        serverMain.getCustomEnchantManager().addEnchantText(stick, "what's there");
        serverMain.getCustomEnchantManager().addEnchantText(stick, "hello there");
        serverMain.getCustomEnchantManager().addGlow(stick);

        event.getPlayer().getInventory().addItem(stick);
        event.getPlayer().getInventory().addItem(sword);
    }
}
