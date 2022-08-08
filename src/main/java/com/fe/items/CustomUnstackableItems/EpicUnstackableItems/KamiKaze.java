package com.fe.items.CustomUnstackableItems.EpicUnstackableItems;

import java.util.Random;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.fe.enchants.CustomEnchantManager;
import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;
import com.fe.enchants.CustomEnchantments;
import com.fe.plugin.PluginMain;
import com.fe.util.Constants;
import com.google.inject.Inject;

public class KamiKaze extends EpicUnstackableItem implements Listener{

    @Inject
    public KamiKaze() {
        super(
            Material.ELYTRA,
            "Kami Kaze",
            new String[] {CustomEnchantments.KAMI_KAZE},
            "Allahu Akbar");
    }

    @EventHandler
	public void onCrashLanding(final EntityDamageEvent event) {
        Entity e = event.getEntity(); 

        // check if entity hurt is a player
        if(e instanceof Player) {
            Player p = (Player) e;
            final ItemStack kamikaze = p.getInventory().getChestplate();
            final ItemMeta meta = kamikaze.getItemMeta();
            boolean isGliding = false;
            // check if player is wearing elytra and got hurt from flying into a wall
            if(meta.getPersistentDataContainer().has(Constants.ServerConstants.IS_GLIDING_IDENTIFIER, PersistentDataType.INTEGER))
                isGliding = meta.getPersistentDataContainer().get(Constants.ServerConstants.IS_GLIDING_IDENTIFIER, PersistentDataType.INTEGER) == Constants.ServerConstants.PERSISTENT_DATA_TYPE_TRUE;
            if (CustomEnchantManager.hasEnchantText(p.getInventory().getChestplate(), CustomEnchantments.KAMI_KAZE) && 
                ((event.getCause() == DamageCause.FLY_INTO_WALL && isGliding)|| 
                (event.getCause() == DamageCause.FALL && isGliding))) {
                // use CustomUnstackableItem cooldown system to declare a 20 second cooldown, have to use static methods b/c spigot doesn't cast well
                final ItemStack item = p.getInventory().getChestplate();

                if(CustomUnstackableItem.isAvailable(Constants.ServerConstants.KAMI_KAZE_IDENTIFIER, item, 1000)) {
                    CustomUnstackableItem.setCooldown(Constants.ServerConstants.KAMI_KAZE_IDENTIFIER, item);
                    p.setHealth(0);
                    p.getWorld().createExplosion(p.getLocation(), 10, true, false);
                }    
            }
        }
    }

    @EventHandler
    public void onToggleGlide(final EntityToggleGlideEvent event) {
        Entity e = event.getEntity();
        if(e instanceof Player) {
            Player p = (Player) e;
            if (CustomEnchantManager.hasEnchantText(p.getInventory().getChestplate(), CustomEnchantments.KAMI_KAZE)) {
                if(!p.isGliding()) {
                    final ItemStack kamikaze = p.getInventory().getChestplate();
                    final ItemMeta meta = kamikaze.getItemMeta();
                    meta.getPersistentDataContainer().set(Constants.ServerConstants.IS_GLIDING_IDENTIFIER, PersistentDataType.INTEGER, Constants.ServerConstants.PERSISTENT_DATA_TYPE_TRUE);
                    kamikaze.setItemMeta(meta);
                }
                if(p.isGliding()) {
                    final ItemStack kamikaze = p.getInventory().getChestplate();
                    final ItemMeta meta = kamikaze.getItemMeta();
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            meta.getPersistentDataContainer().set(Constants.ServerConstants.IS_GLIDING_IDENTIFIER, PersistentDataType.INTEGER, Constants.ServerConstants.PERSISTENT_DATA_TYPE_FALSE);
                            kamikaze.setItemMeta(meta);
                        }
                        
                    }.runTaskLater(PluginMain.getPlugin(PluginMain.class), 1);
                }
            }
        }
    }
}


// runnable: every 5 seconds, check if player has a firework
// if doesnot have firework, give them firework

// event listener
    // if player is wearing elyra AND takes damage from block
        // explode at player location