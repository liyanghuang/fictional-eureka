package com.fe.items.CustomUnstackableItems.EpicUnstackableItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Predicate;

import javax.xml.transform.Templates;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
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

public class SpikeWave extends EpicUnstackableItem implements Listener {

    @Inject
    public SpikeWave() {
        super(
            Material.POINTED_DRIPSTONE,
            "Spike Attack",
            new String[] {CustomEnchantments.SPIKE_WAVE},
            "Throws a row of spikes");
    }

    // Initialized to be used by all events
    Player p;
    boolean usedSpikeWave = false;

    @EventHandler
    public void handPlayerInteract(final PlayerInteractEvent event) {
        // Activates if player left clicks air/block and checks if item in hand is enchanted with SPIKE_WAVE
        if((event.getAction() == Action.LEFT_CLICK_AIR || 
            event.getAction() == Action.LEFT_CLICK_BLOCK) && 
            CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.SPIKE_WAVE))
        {
            p = event.getPlayer();
            usedSpikeWave = true;
            // Finds caster, puts inside raytrace so it avoids user
            final Predicate<Entity> isCurrentPlayer = i -> (!i.getName().equals(event.getPlayer().getName()));
            // Caster information
            final Location eyeLoc = event.getPlayer().getEyeLocation();
            final Vector eyeDir = event.getPlayer().getEyeLocation().getDirection();
            final World world = event.getPlayer().getWorld();

            final RayTraceResult rt = world.rayTrace(eyeLoc, eyeDir, 200, FluidCollisionMode.NEVER, true, 1, isCurrentPlayer);

            // hits an entity
            if (rt.getHitEntity() != null) {

                // schedules the task of running the loop
                new BukkitRunnable() {
                    // get TARGET LOCATION & BLOCK LOCATION
                    final LivingEntity targ = (LivingEntity) rt.getHitEntity();
                    Location targLoc = targ.getLocation();
                    Location chaseLoc = event.getPlayer().getLocation();
                    // These set of codes are initialized only once
                    final long startTime = System.currentTimeMillis();
                    long sinceLastAttack = startTime;
                    long interval = 1000;
                    long timeLimit = 20000;
                    float firePower = (float) 2;
                    ArrayList<Block> spikeList = new ArrayList<Block>();
                    double distance = chaseLoc.distance(targLoc);

                    // looping
                    @Override
                    public void run() {

                        // ATTACK LOOP
                        if(System.currentTimeMillis() - sinceLastAttack > interval){

                            // stops decreasing attack interval at 0.2 seconds
                            if (interval > 200){
                                interval = interval - 150;
                            }

                            // Get new target location and spike location
                            targLoc = targ.getLocation();
                            chaseLoc = getNextBlockLoc(chaseLoc, targLoc);
                            distance = targLoc.distance(chaseLoc);

                            // Dripstone spawn and despawn
                            chaseLoc.getBlock().setType(Material.POINTED_DRIPSTONE);
                            spikeList.add(chaseLoc.getBlock());
                            // Explosion at block surface, no fire nor grief
                            world.createExplosion(chaseLoc, firePower, false, false);
                            if (firePower < 10){
                                firePower = (float) (firePower * 1.03);
                            }
                            sinceLastAttack = System.currentTimeMillis();

                            // Adding to location changes variable location
                            chaseLoc.add(0,1,0).getBlock().setType(Material.POINTED_DRIPSTONE);
                            chaseLoc.add(0,1,0).getBlock().setType(Material.POINTED_DRIPSTONE);
                        }
                    
                        // Cancel attack if takes took long
                        if(System.currentTimeMillis() - startTime > timeLimit || distance < 3){
                            // despawn dripstone
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < spikeList.size() ; ++i){
                                        Location breakSpike = spikeList.get(i).getLocation();
                                        breakSpike.getBlock().setType(Material.AIR);
                                    }
                                    return;
                                }
                            }.runTaskLater(PluginMain.getPlugin(PluginMain.class), 70);
                            
                            if(distance < 3){
                                EntityDamageEvent spikeEvent = new EntityDamageEvent(targ, DamageCause.CONTACT, targ.getHealth());
                                targ.damage(firePower);
                                targ.setLastDamageCause(spikeEvent);
                            }

                            this.cancel();
                            return;
                        }
                    }
               }.runTaskTimer(PluginMain.getPlugin(PluginMain.class), 0, 4);               
            }

            // hits a block
            if (rt.getHitBlock() != null){
                return;
            }
        }
    }

    // Method for finding the next location of attack
    public Location getNextBlockLoc(Location chaseLoc2, Location targLoc2) {
        Vector fire = targLoc2.toVector().subtract(chaseLoc2.toVector()).setY(0).normalize();
        Block tempBlock = chaseLoc2.add(fire.multiply(2)).add(0, -2, 0).getBlock();
        // if air, keep going down until hits a block. Else go up. Target block is the air above surface
        if (!(tempBlock.getType().isSolid())){
            do {
                tempBlock = tempBlock.getLocation().add(0,-1,0).getBlock();
            } while (!(tempBlock.getType().isSolid()));
            tempBlock = tempBlock.getLocation().add(0,1,0).getBlock();
        }
        else {
            do {
                tempBlock = tempBlock.getLocation().add(0,1,0).getBlock();
            } while (tempBlock.getType().isSolid());
        }
        return tempBlock.getLocation();
    }

    // The broken dripstone will not drop items
    @EventHandler
    public void ItemSpawnEvent (final ItemSpawnEvent dropEvent){
        Item spikeDrop = dropEvent.getEntity();
        if (spikeDrop.getItemStack().getType() == Material.POINTED_DRIPSTONE && usedSpikeWave){
                spikeDrop.remove();
        }
    }
}