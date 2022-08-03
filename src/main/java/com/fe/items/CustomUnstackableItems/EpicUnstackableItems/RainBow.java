package com.fe.items.CustomUnstackableItems.EpicUnstackableItems;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;
import com.fe.plugin.PluginMain;
import com.fe.util.Constants;
import com.google.inject.Inject;

public class RainBow extends EpicUnstackableItem implements Listener{

    @Inject
    public RainBow() {
        super(
            Material.BOW, 
            "Rain Bow", 
            new String[] {CustomEnchantments.RAIN_BOW}, 
            "Makes it rain, whatever");
    }

    @EventHandler
    public void handleBowShoot(EntityShootBowEvent event) {
        if(CustomEnchantManager.hasEnchantText(event.getBow(), CustomEnchantments.RAIN_BOW)) {
            event.getProjectile().setMetadata(CustomEnchantments.RAIN_BOW, Constants.ServerConstants.FIXED_METADATA_TRUE);
            new BukkitRunnable() {

                Entity arrow = event.getProjectile();

                @Override
                public void run() {
                    if(arrow.isDead())
                        this.cancel();
                    else {
                        Entity dupArrow = arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.ARROW);
                        dupArrow.setMetadata(CustomEnchantments.RAIN_BOW, Constants.ServerConstants.FIXED_METADATA_TRUE);
                        dupArrow.setVelocity(arrow.getVelocity().add(new Vector(Constants.ServerConstants.RANDOM.nextFloat() - 0.5, Constants.ServerConstants.RANDOM.nextFloat() - 0.5, Constants.ServerConstants.RANDOM.nextFloat() - 0.5)));
                    }
                }
            }.runTaskTimer(PluginMain.getPlugin(PluginMain.class), 2, 2);
        }
    }

    @EventHandler
    public void handleArrowLand(ProjectileHitEvent event) {
        if(!event.getEntity().hasMetadata(CustomEnchantments.RAIN_BOW))
            return;
        if(event.getHitBlock() != null) {
            event.getHitBlock().getWorld().spawnEntity(event.getHitBlock().getLocation(), EntityType.IRON_GOLEM);
            event.getEntity().remove();
        }
        if(event.getHitEntity() != null) {
            event.getHitEntity().getWorld().spawnEntity(event.getHitEntity().getLocation(), EntityType.IRON_GOLEM);
            event.getEntity().remove();
        }
    }
}