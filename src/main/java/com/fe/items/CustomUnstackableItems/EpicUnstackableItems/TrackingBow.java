package com.fe.items.CustomUnstackableItems.EpicUnstackableItems;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
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

public class TrackingBow extends EpicUnstackableItem implements Listener{

    private final FixedMetadataValue fixedMetadataValue;
    private final Random random;

    @Inject
    public TrackingBow(FixedMetadataValue fixedMetadataValue, Random random) {
        super(
            Material.BOW, 
            "Tracking Bow", 
            new String[] {CustomEnchantments.TRACKING_BOW}, 
            "Shoots exploding arrows that track down and destroy targets. It has a mind of it's own...");
        this.fixedMetadataValue = fixedMetadataValue;
        this.random = random;
    }

    @EventHandler
    public void handleBowShoot(EntityShootBowEvent event) {
        if(CustomEnchantManager.hasEnchantText(event.getBow(), CustomEnchantments.TRACKING_BOW)) {
            event.getProjectile().setMetadata(CustomEnchantments.TRACKING_BOW, fixedMetadataValue);
            new BukkitRunnable() {

                Entity arrow = event.getProjectile();
                Entity target = null;
                LivingEntity shooter = (LivingEntity)event.getEntity();

                @Override
                public void run() {
                    if(arrow.isDead())
                        this.cancel();
                    else {
                        if(target == null || target.isDead()){
                            List<Entity> mobs = arrow.getNearbyEntities(30, 30, 30);
                            // remove mobs that we shoudn't target
                            mobs.removeIf(e -> (!(e instanceof LivingEntity) || e.equals(shooter) || !shooter.hasLineOfSight(e)));
                            if(mobs.size() > 0) {
                                // target a random mob from the mobs we can target
                                target = mobs.get(random.nextInt(mobs.size()));
                            }
                        }
                        if(target != null && !target.isDead()) {
                            Vector targetDir = target.getLocation().toVector().add(new Vector(0, 0.7, 0)).subtract(arrow.getLocation().toVector()).normalize();
                            Vector directionChange = targetDir.subtract(arrow.getVelocity().normalize());
                            arrow.setVelocity(arrow.getVelocity().add(directionChange.multiply(0.5)));
                        }
                    }
                }
            }.runTaskTimer(PluginMain.getPlugin(PluginMain.class), 2, 2);
        }
    }

    @EventHandler
    public void handleArrowLand(ProjectileHitEvent event) {
        if(!event.getEntity().hasMetadata(CustomEnchantments.TRACKING_BOW))
            return;
        if(event.getHitBlock() != null) {
            event.getHitBlock().getWorld().createExplosion(event.getHitBlock().getLocation(), 5, false, false, event.getEntity());
            event.getEntity().remove();
        }
        if(event.getHitEntity() != null) {
            event.getHitEntity().getWorld().createExplosion(event.getHitEntity().getLocation(), 5, false, false, event.getEntity());
            event.getEntity().remove();
        }
    }
}
