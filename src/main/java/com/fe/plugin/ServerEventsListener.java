package com.fe.plugin;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.AirJordans;
import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.RainBow;
import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.TrackingBow;
import com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems.AirStrike;
import com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems.StaffOfIce;
import com.fe.items.CustomUnstackableItems.MythicUnstackableItems.DeathNote;
import com.fe.items.CustomUnstackableItems.MythicUnstackableItems.Wabbajack;

public class ServerEventsListener implements Listener{

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        event.getPlayer().setOp(true);
        event.getPlayer().setFlySpeed(0.1f);
        event.getPlayer().getInventory().addItem(new TrackingBow());
        event.getPlayer().getInventory().addItem(new RainBow());
        event.getPlayer().getInventory().addItem(new AirStrike());
        event.getPlayer().getInventory().addItem(new AirJordans());
        event.getPlayer().getInventory().addItem(new Wabbajack());
        event.getPlayer().getInventory().addItem(new StaffOfIce());
        event.getPlayer().getInventory().addItem(new DeathNote());
        ItemStack helm = new ItemStack(Material.NETHERITE_HELMET);
        ItemStack chest = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemStack legs = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
        helm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        helm.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        chest.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        legs.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        event.getPlayer().getInventory().addItem(helm);
        event.getPlayer().getInventory().addItem(chest);
        event.getPlayer().getInventory().addItem(legs);
        event.getPlayer().getInventory().addItem(boots);
    }
}
