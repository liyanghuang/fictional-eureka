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
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
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

public class SpikeWave extends EpicUnstackableItem implements Listener {

    @Inject
    public SpikeWave() {
        super(
            Material.POINTED_DRIPSTONE,
            "Spike Attack",
            new String[] {CustomEnchantments.SPIKE_WAVE},
            "Throws a row of spikes");
    }

    @EventHandler
    public void handPlayerInteract(final PlayerInteractEvent event) {
        // Activates if player left clicks air/block and checks if item in hand is enchanted with SPIKE_WAVE
        if((event.getAction() == Action.LEFT_CLICK_AIR || 
            event.getAction() == Action.LEFT_CLICK_BLOCK) && 
            CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.SPIKE_WAVE))
        {
            System.out.println("event is happening"); // DELETE

            // Finds caster, puts inside raytrace so it avoids user
            final Predicate<Entity> isCurrentPlayer = i -> (!i.getName().equals(event.getPlayer().getName()));
            // Caster information
            final Location eyeLoc = event.getPlayer().getEyeLocation();
            final Vector eyeDir = event.getPlayer().getEyeLocation().getDirection();
            final World world = event.getPlayer().getWorld();

            final RayTraceResult rt = world.rayTrace(eyeLoc, eyeDir, 200, FluidCollisionMode.NEVER, true, 1, isCurrentPlayer);

            // hits an entity
            if (rt.getHitEntity() != null) {
                System.out.println("something been hit"); //DELETE

                // schedules the task of running the loop
                new BukkitRunnable() {
                    // get TARGET LOCATION & BLOCK LOCATION
                    final Entity targ = rt.getHitEntity();
                    Location targLoc;
                    Location chaseLoc = event.getPlayer().getLocation();

;
                    // These set of codes are initialized only once
                    final long startTime = System.currentTimeMillis(); 
                    long interval = 3000;
                    long timeLimit = 20000;
                    float firePower = (float) 1.0;
                    ArrayList<Block> spikeList = new ArrayList<Block>();
                    double distance;

                    // looping
                    @Override
                    public void run() {

                        // ATTACK LOOP
                        if(System.currentTimeMillis() - startTime> interval){

                            // stops decreasing attack interval at 0.2 seconds
                            if (interval > 200){
                                interval = interval - 100;
                            }

                            // Get new target location and spike location
                            targLoc = targ.getLocation();
                            chaseLoc = getNextBlockLoc(chaseLoc, targLoc);
                            distance = targLoc.distance(chaseLoc);

                            // Dripstone spawn and despawn
                            chaseLoc.getBlock().setType(Material.POINTED_DRIPSTONE);
                            chaseLoc.add(0,1,0).getBlock().setType(Material.POINTED_DRIPSTONE);
                            chaseLoc.add(0,2,0).getBlock().setType(Material.POINTED_DRIPSTONE);

                            // Adds location of spike to list to delete later
                            spikeList.add(chaseLoc.getBlock());

                            // Explosion at block, no fire nor grief
                            world.createExplosion(chaseLoc, firePower, false, false);
                            if (firePower < 10){
                                firePower = (float) (firePower * 1.1);
                            }

                        }

                        // Cancel attack if takes took long
                        if(System.currentTimeMillis() - startTime > timeLimit || distance < 3){
                            // despawn dripstone
                            for (int i = 0; i > spikeList.size() ; ++i){
                                spikeList.get(i).setType(Material.AIR);
                            }

                            System.out.println("attack done");
                            this.cancel();
                            return;
                        }
                    }

                    // Method for finding the next location of attack
                    private Location getNextBlockLoc(Location chaseLoc2, Location targLoc2) {
                        Vector fire = targLoc2.toVector().subtract(chaseLoc2.toVector()).normalize();
                        Block tempBlock = chaseLoc2.add(fire.multiply(2)).add(0, -2, 0).getBlock();
                        // if air, keep going down until hits a block. Else go up. Target block is the air above surface
                        if (tempBlock.getType() == Material.AIR){
                            do {
                                tempBlock = tempBlock.getLocation().add(0,-1,0).getBlock();
                            } while (tempBlock.getType() == Material.AIR);
                            tempBlock = tempBlock.getLocation().add(0,1,0).getBlock();
                        }
                        else {
                            do {
                                tempBlock = tempBlock.getLocation().add(0,1,0).getBlock();
                            } while (tempBlock.getType() != Material.AIR);
                        }
                        return tempBlock.getLocation();
                    }
               }.runTaskTimer(PluginMain.getPlugin(PluginMain.class), 0, 4);               
            }

            // hits a block
            if (rt.getHitBlock() != null){
                return;
            }

                // add VECTOR * [certain multiplier] + BLOCK LOCATION to get TEMP BLOCK LOCATION

                // check if TEMP NEW BLOCK LOCATION satisfies conditions

                // get new TARGET LOCATION
                // get new BLOCK LOCATION
                // get new unit VECTOR from BLOCK to TARGET

                // place a dripstone block on top of the TARGET BLOCK
                // set an explosion at the TARGET BLOCK
                    // explosion must not grief, only damage

                // Things changing for every loop:
                     // explosion level increases
                        // if explosion level is over [max explosion]
                            // do not increase explosion level
                    // interval of loop is shorter
                        // if time is under [time limit]
                            // do not decrease interval of attack
        }
    }
}