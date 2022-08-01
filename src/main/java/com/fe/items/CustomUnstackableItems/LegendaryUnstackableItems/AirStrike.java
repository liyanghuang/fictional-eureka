package com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems;

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
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;
import com.fe.plugin.PluginMain;
import com.fe.util.Constants;
import com.google.inject.Inject;

public class AirStrike extends LegendaryUnstackableItem implements Listener{

    private final FixedMetadataValue fixedMetadataValue;
    private final Random random;

    @Inject
    public AirStrike(final FixedMetadataValue fixedMetadataValue, final Random random) {
        super(
            Material.BEACON,
            "Air Strike Remote",
            new String[] {CustomEnchantments.AIR_STRIKE},
            "Launches an airstrike anywhere you can see");
        this.fixedMetadataValue = fixedMetadataValue;
        this.random = random;
    }

    @EventHandler
    public void handPlayerInteract(final PlayerInteractEvent event) {
        if((event.getAction() == Action.LEFT_CLICK_AIR || 
            event.getAction() == Action.LEFT_CLICK_BLOCK) && 
            CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.AIR_STRIKE))
        {
            event.setCancelled(true);
            final ItemStack airStrike = event.getPlayer().getInventory().getItemInMainHand();
            final ItemMeta meta = airStrike.getItemMeta();
            long timer = 0;
            if(meta.getPersistentDataContainer().has(Constants.ServerConstants.AIR_STRIKE_IDENTIFIER, PersistentDataType.LONG)) {
                timer = meta.getPersistentDataContainer().get(Constants.ServerConstants.AIR_STRIKE_IDENTIFIER, PersistentDataType.LONG);
            }
            if(System.currentTimeMillis() - timer > 20000) {
                final Predicate<Entity> isCurrentPlayer = i -> (!i.getName().equals(event.getPlayer().getName()));

                meta.getPersistentDataContainer().set(Constants.ServerConstants.AIR_STRIKE_IDENTIFIER, PersistentDataType.LONG, System.currentTimeMillis());           
                airStrike.setItemMeta(meta);

                event.getPlayer().setCooldown(Material.BEACON, 400);

                final Location eyeLoc = event.getPlayer().getEyeLocation();
                final Vector eyeDir = event.getPlayer().getEyeLocation().getDirection();
                final World world = event.getPlayer().getWorld();

                final RayTraceResult rt = world.rayTrace(eyeLoc, eyeDir, 200, FluidCollisionMode.NEVER, true, 1, isCurrentPlayer);
                if(rt != null && rt.getHitBlock() != null && rt.getHitBlockFace() != null) {

                    new BukkitRunnable() {

                        Block beacon = rt.getHitBlock().getRelative(rt.getHitBlockFace());
                        final long startTime = System.currentTimeMillis();
                        boolean firstRun = true;
                        final Material prevMat = (beacon.getType() == Material.END_GATEWAY? Material.AIR : beacon.getType());
                        BukkitTask strikeTask = null;

                        @Override
                        public void run() {

                            if(firstRun) {
                                beacon.setType(Material.END_GATEWAY);
                                firstRun = false;
                            }

                            if(System.currentTimeMillis()-startTime > 8000 && strikeTask == null) {
                                beacon.setType(prevMat);
                                strikeTask = Bukkit.getScheduler().runTaskTimer(PluginMain.getPlugin(PluginMain.class), new DoStrike(beacon.getLocation(), event.getPlayer()), 0, 1);
                            }
                            if(System.currentTimeMillis()-startTime > 13000) {
                                beacon.setType(prevMat);
                                strikeTask.cancel();
                                this.cancel();
                                return;
                            }
                        }
                    }.runTaskTimer(PluginMain.getPlugin(PluginMain.class), 0, 5);
                }

                if(rt != null && rt.getHitEntity() != null) {

                    new BukkitRunnable() {

                        final Entity target = rt.getHitEntity();
                        Block beacon = world.getBlockAt(rt.getHitEntity().getLocation().add(2,0,0));
                        Material prevMat = (beacon.getType() == Material.END_GATEWAY? Material.AIR : beacon.getType());
                        final long startTime = System.currentTimeMillis();
                        BukkitTask strikeTask = null;

                        @Override
                        public void run() {
                            if(
                                (Math.abs(target.getLocation().getX() - beacon.getLocation().getX()) > 1.0) || 
                                (Math.abs(target.getLocation().getZ() - beacon.getLocation().getZ()) > 1.0))
                            {
                                beacon.setType(prevMat);
                                beacon = world.getBlockAt(target.getLocation());
                                prevMat = (beacon.getType() == Material.END_GATEWAY? Material.AIR : beacon.getType());
                                if(strikeTask == null)
                                    beacon.setType(Material.END_GATEWAY);
                            }

                            if(System.currentTimeMillis() - startTime > 8000 && strikeTask == null) {
                                beacon.setType(prevMat);
                                strikeTask = Bukkit.getScheduler().runTaskTimer(PluginMain.getPlugin(PluginMain.class), new DoStrike(beacon.getLocation(), event.getPlayer()), 0, 1);
                            }
                            
                            if(System.currentTimeMillis() - startTime > 13000) {
                                beacon.setType(prevMat);
                                strikeTask.cancel();
                                this.cancel();
                                return;
                            }

                        }
                    }.runTaskTimer(PluginMain.getPlugin(PluginMain.class), 0, 5);
                }
            }
        }
        if((event.getAction() == Action.RIGHT_CLICK_AIR|| 
            event.getAction() == Action.RIGHT_CLICK_BLOCK) && 
            CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.AIR_STRIKE))
        {
            event.setCancelled(true);
        }
    }

    // make it so fireballs don't get blown away from where they are supposed to land
    @EventHandler
    public void handleFireballHitByExplosion(EntityDamageByEntityEvent event) {
        if(event.getEntity().hasMetadata(CustomEnchantments.AIR_STRIKE) && event.getDamager().hasMetadata(CustomEnchantments.AIR_STRIKE))
            event.setCancelled(true);
    }

    @EventHandler
    public void handleFireballLand(ProjectileHitEvent event) {
        if(!event.getEntity().hasMetadata(CustomEnchantments.AIR_STRIKE))
            return;
        event.setCancelled(true); // cancel event so there's no fire
        if(event.getHitBlock() != null) {
            event.getHitBlock().getWorld().createExplosion(event.getHitBlock().getLocation(), 8, false, false, event.getEntity());
            event.getEntity().remove();
        }
        if(event.getHitEntity() != null) {
            event.getHitEntity().getWorld().createExplosion(event.getHitEntity().getLocation(), 8, false, false, event.getEntity());
            event.getEntity().remove();
        }
    }

    private class DoStrike implements Runnable {

        private final Location location;
        private final Player source;
        private final int radius = 23;

        public DoStrike(final Location location, final Player source) {
            this.location = location;
            location.setDirection(new Vector(0, -4, 0));
            this.source = source;
        }

        @Override
        public void run() {
            for(int i = 0; i < 2; i++) {
                final Location thisStrikeLoc = location.clone();
                Fireball fireball = (Fireball) location.getWorld().spawnEntity(thisStrikeLoc.add(random.nextInt(radius * 2) - radius, location.getY() + 200, random.nextInt(radius * 2) - radius), EntityType.FIREBALL);
                fireball.setShooter(source);
                fireball.setMetadata(CustomEnchantments.AIR_STRIKE, fixedMetadataValue);
                fireball.setIsIncendiary(false);
                fireball.setYield(0);
                fireball.setVelocity(new Vector(0, -10, 0));
            }
        }
    }
}
