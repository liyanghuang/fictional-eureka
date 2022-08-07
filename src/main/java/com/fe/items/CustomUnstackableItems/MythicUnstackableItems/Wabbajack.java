package com.fe.items.CustomUnstackableItems.MythicUnstackableItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;
import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;
import com.fe.plugin.PluginMain;
import com.fe.util.Constants;

public class Wabbajack extends MythicUnstackableItem implements Listener{
    
    public Wabbajack() {
        super(
            Material.BLAZE_ROD,
            CustomEnchantManager.formatString("#ff00b3L#ff00a6O#ff0099L", true, false, true) + "" + 
            CustomEnchantManager.formatString("#ff008dW#ff0080A#ff0073B#ff0066B#ff005aA#ff004dJ#ff0040A#ff0033C#ff0026K", true, false, false) + "" +
            CustomEnchantManager.formatString("#ff001aL#ff000dO#ff0000L", true, false, true),
            new String[] {CustomEnchantments.WABBAJACK},
            "What does it do..."
        );
    }

    @EventHandler
	public void playerInteract(final PlayerInteractEvent event) {
		if((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) 
            && CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.WABBAJACK))
		{
            event.setCancelled(true);
            final ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            //if(CustomUnstackableItem.isAvailable(Constants.ServerConstants.WABBAJACK_IDENTIFIER, item, 5000)) {
             //   CustomUnstackableItem.setCooldown(Constants.ServerConstants.WABBAJACK_IDENTIFIER, item);
              //  event.getPlayer().setCooldown(Material.BLAZE_ROD, 100);
                final SmallFireball fireball = event.getPlayer().launchProjectile(SmallFireball.class, event.getPlayer().getEyeLocation().getDirection().multiply(2));
                fireball.setIsIncendiary(false);
                fireball.setYield(0);
                fireball.setMetadata(CustomEnchantments.WABBAJACK, Constants.ServerConstants.FIXED_METADATA_TRUE);
            //}
		}
	}

    @EventHandler
    public void onProjectileLand(final ProjectileHitEvent event) {
        if(!event.getEntity().hasMetadata(CustomEnchantments.WABBAJACK)
            || event.getEntity().hasMetadata(Constants.ServerConstants.DONT_DUPE))
            return;

        event.getEntity().setMetadata(Constants.ServerConstants.DONT_DUPE, Constants.ServerConstants.FIXED_METADATA_TRUE);
        // is a wabbajack projectile
        event.setCancelled(true); // cancel the event to prevent terrain and explosion damage
        // we hit a block
        if(event.getHitBlock() != null) {
            event.getEntity().remove();
        }
        // we hit an entity that's not the player
        if(event.getHitEntity() != null && !(event.getHitEntity() instanceof Player)) {
            final Entity hitEntity = event.getHitEntity();
            switch(Constants.ServerConstants.RANDOM.nextInt(5)) {
                case 0:
                    hitEntity.getWorld().createExplosion(hitEntity.getLocation(), 4, false, false, event.getEntity());
                    event.getEntity().remove();
                    return;
                case 1:
                    // don't kill legendary custom mobs
                    try {
                        hitEntity.getWorld().dropItem(hitEntity.getLocation(), new ItemStack(getRandomItem(), 1));
                    } catch (IllegalArgumentException e) {
                        hitEntity.getWorld().dropItem(hitEntity.getLocation(), new ItemStack(Material.DIAMOND, 1));
                    }
                    hitEntity.remove();
                    event.getEntity().remove();
                    return;
                case 2:
                    hitEntity.getWorld().spawnEntity(hitEntity.getLocation(), getRandomMob());
                    hitEntity.remove();
                    event.getEntity().remove();
                    return;
                case 3:
                    encaseInIce(hitEntity.getLocation());
                    event.getEntity().remove();
                    return;
                case 4:
                    splashPotion(hitEntity);
                    event.getEntity().remove();
                    return;
            }
        }
        // we hit an entity that is the player
        if(event.getHitEntity() != null && (event.getHitEntity() instanceof Player)) {
            System.out.println("hit player");
        }
    }

    private Material getRandomItem() {
        return Material.values()[Constants.ServerConstants.RANDOM.nextInt(Material.values().length)];
    }



    private EntityType getRandomMob() {
        EntityType entityType = EntityType.values()[Constants.ServerConstants.RANDOM.nextInt(EntityType.values().length)];
        if(
            entityType != EntityType.SMALL_FIREBALL &&
            entityType != EntityType.SNOWBALL &&
            entityType != EntityType.SPECTRAL_ARROW &&
            entityType != EntityType.SPLASH_POTION &&
            entityType != EntityType.THROWN_EXP_BOTTLE &&
            entityType != EntityType.AREA_EFFECT_CLOUD &&
            entityType != EntityType.ARMOR_STAND &&
            entityType != EntityType.ARROW &&
            entityType != EntityType.CHEST_BOAT &&
            entityType != EntityType.DRAGON_FIREBALL &&
            entityType != EntityType.DROPPED_ITEM &&
            entityType != EntityType.EGG &&
            entityType != EntityType.ELDER_GUARDIAN &&
            entityType != EntityType.ENDER_CRYSTAL &&
            entityType != EntityType.ENDER_DRAGON &&
            entityType != EntityType.ENDER_PEARL &&
            entityType != EntityType.ENDER_SIGNAL &&
            entityType != EntityType.EXPERIENCE_ORB &&
            entityType != EntityType.EVOKER_FANGS &&
            entityType != EntityType.FIREBALL &&
            entityType != EntityType.FALLING_BLOCK &&
            entityType != EntityType.FIREWORK &&
            entityType != EntityType.FISHING_HOOK &&
            entityType != EntityType.GIANT &&
            entityType != EntityType.GLOW_ITEM_FRAME &&
            entityType != EntityType.ITEM_FRAME &&
            entityType != EntityType.LEASH_HITCH &&
            entityType != EntityType.LIGHTNING &&
            entityType != EntityType.LLAMA_SPIT &&
            entityType != EntityType.MARKER &&
            entityType != EntityType.MINECART_CHEST &&
            entityType != EntityType.MINECART_COMMAND  &&
            entityType != EntityType.MINECART_FURNACE &&
            entityType != EntityType.MINECART_HOPPER &&
            entityType != EntityType.MINECART_MOB_SPAWNER &&
            entityType != EntityType.MINECART_TNT &&
            entityType != EntityType.PAINTING &&
            entityType != EntityType.PLAYER &&
            entityType != EntityType.PRIMED_TNT &&
            entityType != EntityType.SHULKER_BULLET &&
            entityType != EntityType.SMALL_FIREBALL &&
            entityType != EntityType.SNOWBALL &&
            entityType != EntityType.SPECTRAL_ARROW &&
            entityType != EntityType.SPLASH_POTION &&
            entityType != EntityType.THROWN_EXP_BOTTLE &&
            entityType != EntityType.TRIDENT &&
            entityType != EntityType.UNKNOWN &&
            entityType != EntityType.WARDEN &&
            entityType != EntityType.WITHER &&
            entityType != EntityType.WITHER_SKULL
        ) {
            return entityType;
        }
        return EntityType.CHICKEN;
    }

    private void encaseInIce(Location loc) {
        final List<Block> nearbyBlocks = getNearbyBlocks(loc, 2);
        for(Block block : nearbyBlocks) {

            if(block.getType() == Material.AIR) {
                block.setMetadata(Constants.ITEM_CONSTANTS.DONT_DO_BLOCK_EVENTS, Constants.ServerConstants.FIXED_METADATA_TRUE);
                if(Constants.ServerConstants.RANDOM.nextInt(4) < 3) 
                    block.setType(Material.ICE);
                else {
                    if(Constants.ServerConstants.RANDOM.nextInt(2) == 0) 
                        block.setType(Material.BLUE_ICE);
                    else
                        block.setType(Material.PACKED_ICE);
                }
            }
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                for(Block block : nearbyBlocks) {
                    if(
                        (block.getType() == Material.ICE || block.getType() == Material.BLUE_ICE || block.getType() == Material.PACKED_ICE) &&
                        block.hasMetadata(Constants.ITEM_CONSTANTS.DONT_DO_BLOCK_EVENTS)
                    ) {
                        block.setType(Material.WATER);
                    }
                }
            }

        }.runTaskLater(PluginMain.getPlugin(PluginMain.class), 200);
        new BukkitRunnable() {

            @Override
            public void run() {
                for(Block block : nearbyBlocks) {
                    if(block.getType() == Material.WATER && block.hasMetadata(Constants.ITEM_CONSTANTS.DONT_DO_BLOCK_EVENTS))
                        block.setType(Material.AIR);
                    if(block.hasMetadata(Constants.ITEM_CONSTANTS.DONT_DO_BLOCK_EVENTS))
                        block.removeMetadata(Constants.ITEM_CONSTANTS.DONT_DO_BLOCK_EVENTS, PluginMain.getPlugin(PluginMain.class));
                }
            }

        }.runTaskLater(PluginMain.getPlugin(PluginMain.class), 240);
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        if(event.getBlock().hasMetadata(Constants.ITEM_CONSTANTS.DONT_DO_BLOCK_EVENTS))
            event.setCancelled(true);
    }

    private List<Block> getNearbyBlocks(Location location, int radius) {
        final List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    if(Math.abs(x - location.getBlockX()) + Math.abs(y - location.getBlockY()) + Math.abs(z - location.getBlockZ()) < radius * 2)
                        blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    private void splashPotion(Entity hitEntity) {
        final PotionEffectType potionEffectType = PotionEffectType.values()[Constants.ServerConstants.RANDOM.nextInt(PotionEffectType.values().length)];
        final PotionEffect potionEffect = new PotionEffect(potionEffectType, Constants.ServerConstants.RANDOM.nextInt(400) + 100, Constants.ServerConstants.RANDOM.nextInt(5));
        for (Entity entity: hitEntity.getNearbyEntities(5, 5, 5)) {
            if(entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.addPotionEffect(potionEffect);
            }
        }
        LivingEntity livingEntity = (LivingEntity) hitEntity;
        livingEntity.addPotionEffect(potionEffect);
        hitEntity.getWorld().playEffect(hitEntity.getLocation(), Effect.POTION_BREAK, Color.fromRGB(Constants.ServerConstants.RANDOM.nextInt(256), Constants.ServerConstants.RANDOM.nextInt(256), Constants.ServerConstants.RANDOM.nextInt(256)).asRGB());
    }

}