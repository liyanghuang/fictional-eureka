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
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rotatable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.scanner.Constant;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;
import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;
import com.fe.plugin.PluginMain;
import com.fe.util.Constants;
import com.fe.util.Constants.ServerConstants;
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

    @EventHandler
    public void handPlayerInteract(final PlayerInteractEvent event) {
        // Activates if player left clicks air/block and checks if item in hand is enchanted with SPIKE_WAVE
        if((event.getAction() == Action.LEFT_CLICK_AIR || 
            event.getAction() == Action.LEFT_CLICK_BLOCK) &&
            CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.SPIKE_WAVE))
        {
            Player p = event.getPlayer();
            final ItemStack item = p.getInventory().getItemInMainHand();
            final ItemMeta meta = item.getItemMeta();

            if (CustomUnstackableItem.isAvailable(Constants.ServerConstants.SPIKE_WAVE_IDENTIFIER, item, 20000)) { 
                CustomUnstackableItem.setCooldown(Constants.ServerConstants.SPIKE_WAVE_IDENTIFIER, item);

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
                        ArrayList<Block> breakList = new ArrayList<Block>();
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

                                // Dripstone spawn
                                chaseLoc.getBlock().setType(Material.POINTED_DRIPSTONE);
                                spikeList.add(chaseLoc.getBlock());

                                // Explosion at block surface, no fire nor grief
                                world.createExplosion(chaseLoc, firePower, false, false);
                                if (firePower < 10){
                                    firePower = (float) (firePower * 1.03);
                                }
                                sinceLastAttack = System.currentTimeMillis();

                                // Add to be broken blocks to prevent item drops later
                                chaseLoc.add(0,1,0).getBlock().setType(Material.POINTED_DRIPSTONE);
                                chaseLoc.getBlock().setMetadata(CustomEnchantments.BREAK_NO_DROP, Constants.ServerConstants.FIXED_METADATA_TRUE);

                                chaseLoc.add(0,1,0).getBlock().setType(Material.POINTED_DRIPSTONE);
                                chaseLoc.getBlock().setMetadata(CustomEnchantments.BREAK_NO_DROP, Constants.ServerConstants.FIXED_METADATA_TRUE);
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
                                
                                // Damage them more if the spike actually gets to them
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
                else if (rt.getHitBlock() != null){

                    System.out.println("hit a block");
                    Location targLocB = rt.getHitBlock().getLocation();
                    Location spawnLocB = targLocB.add(0, 100, 0);
                }
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

    @EventHandler
    public void ItemSpawnEvent(final ItemSpawnEvent event){
        boolean noDrop = false;
        Block dropBlock = event.getLocation().getBlock();

        if (dropBlock.hasMetadata(CustomEnchantments.BREAK_NO_DROP)){
            noDrop = dropBlock.getMetadata(CustomEnchantments.BREAK_NO_DROP).get(0) == Constants.ServerConstants.FIXED_METADATA_TRUE;
        }

        if ( noDrop ){
            event.getEntity().remove();
            dropBlock.setMetadata(CustomEnchantments.BREAK_NO_DROP, Constants.ServerConstants.FIXED_METADATA_FALSE);
        }

    }

}



/*
    // The broken dripstone will not drop items
    @EventHandler
    public void ItemSpawnEvent (final ItemSpawnEvent dropEvent){
        Item spikeDrop = dropEvent.getEntity();
        boolean noDrops = 

        if (spikeDrop.getItemStack().getType() == Material.POINTED_DRIPSTONE && noDrops){
                spikeDrop.remove();
                noDrops = false;
        }
    }
}
*/