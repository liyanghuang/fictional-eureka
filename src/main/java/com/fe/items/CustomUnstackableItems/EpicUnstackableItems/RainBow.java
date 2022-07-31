package com.fe.items.CustomUnstackableItems.EpicUnstackableItems;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;
import com.fe.plugin.PluginMain;
import com.google.inject.Inject;

public class RainBow extends EpicUnstackableItem implements Listener{

    private final FixedMetadataValue fixedMetadataValue;
    private final Random random;

    @Inject
    public RainBow(FixedMetadataValue fixedMetadataValue, Random random) {
        super(
            Material.BOW, 
            "Rain Bow", 
            new String[] {CustomEnchantments.RAIN_BOW}, 
            "Makes it rain, whatever");
        this.fixedMetadataValue = fixedMetadataValue;
        this.random = random;
    }

    @EventHandler
    public void handleBowShoot(EntityShootBowEvent event) {
        if(CustomEnchantManager.hasEnchantText(event.getBow(), CustomEnchantments.RAIN_BOW)) {
            event.getProjectile().setMetadata(CustomEnchantments.RAIN_BOW, fixedMetadataValue);
            new BukkitRunnable() {

                Entity arrow = event.getProjectile();

                @Override
                public void run() {
                    if(arrow.isDead())
                        this.cancel();
                    else {
                        Entity dupArrow = arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.ARROW);
                        dupArrow.setMetadata(CustomEnchantments.RAIN_BOW, fixedMetadataValue);
                        dupArrow.setVelocity(arrow.getVelocity().add(new Vector(random.nextFloat() - 0.5, random.nextFloat() - 0.5, random.nextFloat() - 0.5)));
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