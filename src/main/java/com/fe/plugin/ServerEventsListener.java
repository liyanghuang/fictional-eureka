package com.fe.plugin;

import javax.sound.midi.Track;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.RainBow;
import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.TrackingBow;
import com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems.AirStrike;
import com.google.inject.Inject;

public class ServerEventsListener implements Listener{

    private final TrackingBow trackingBow;
    private final RainBow rainBow;
    private final AirStrike airStrike;

    @Inject
    public ServerEventsListener(final TrackingBow trackingBow, final RainBow rainBow, final AirStrike airStrike) {
        this.trackingBow = trackingBow;
        this.rainBow = rainBow;
        this.airStrike = airStrike;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(this.trackingBow);
        event.getPlayer().getInventory().addItem(this.rainBow);
        event.getPlayer().getInventory().addItem(this.airStrike);
        ItemStack helm = new ItemStack(Material.NETHERITE_HELMET);
        ItemStack chest = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemStack legs = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
        helm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        event.getPlayer().getInventory().addItem(helm);
        event.getPlayer().getInventory().addItem(chest);
        event.getPlayer().getInventory().addItem(legs);
        event.getPlayer().getInventory().addItem(boots);
    }
}
