package com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;
import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;
import com.fe.plugin.PluginMain;
import com.fe.util.Constants;

public class GrapplingHook extends LegendaryUnstackableItem implements Listener{
 
    public GrapplingHook() {
        super(
            Material.CHAIN,
            "Grappling Hook",
            new String [] {CustomEnchantments.GRAPPLING_HOOK},
            "Become a shittier spiderman"
        );
    }

    	// todo: figure out offhand better

	@EventHandler
	public void playerInteract(final PlayerInteractEvent event)
	{
        if(CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.GRAPPLING_HOOK))
            event.setCancelled(true);

		if(((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) && 
            CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.GRAPPLING_HOOK)) ||
            ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && 
            CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInOffHand(), CustomEnchantments.GRAPPLING_HOOK) &&
            !event.getPlayer().getInventory().getItemInMainHand().getType().isInteractable()))
		{
            event.setCancelled(true);

            final Player player = event.getPlayer();
            final ItemStack item;
            if(CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.GRAPPLING_HOOK))
                item = player.getInventory().getItemInMainHand();
            else
                item = player.getInventory().getItemInOffHand();

            if(CustomUnstackableItem.isAvailable(Constants.ServerConstants.GRAPPLING_HOOK_IDENTIFIER, item, 2000)) {
                CustomUnstackableItem.setCooldown(Constants.ServerConstants.GRAPPLING_HOOK_IDENTIFIER, item);
                event.getPlayer().setCooldown(Material.CHAIN, 40);

                final Vector lookingDir = player.getEyeLocation().getDirection().multiply(0.5);
                final Predicate<Entity> isCurrentPlayer = i -> (!i.getName().equals(event.getPlayer().getName()) && i.getType() != EntityType.ARMOR_STAND);

                new BukkitRunnable() {

                    private boolean maxLengthReached = false;
                    private boolean hitBlock = false;
                    private Block block = null;
                    private Stack<ArmorStand> grapplingPieces = new Stack<ArmorStand>();
                    private Location startLoc = player.getLocation().add(0, 1, 0);
                    private Vector startVec = player.getLocation().toVector();

                    @Override
                    public void run() {

                        // update armor stand positions
                        for(ArmorStand as : grapplingPieces) {
                            as.teleport(as.getLocation().add(player.getLocation().toVector().subtract(startVec)));
                        }
                        startVec = player.getLocation().toVector();

                        // shoot the grapple
                        if(!maxLengthReached) {

                            maxLengthReached = true;

                            for(int i = 0; i < 40; i++) {
                                startLoc.add(lookingDir);
                                if(player.getWorld().getBlockAt(startLoc).getType().isSolid()) {
                                    hitBlock = true;
                                    block = player.getWorld().getBlockAt(startLoc);
                                    break;
                                }
                                if(!player.getWorld().getNearbyEntities(startLoc, 1, 1, 1, isCurrentPlayer).isEmpty()) {
                                    Entity hitEntity = player.getWorld().getNearbyEntities(startLoc, 1, 1, 1, isCurrentPlayer).stream().findFirst().get();
                                    hitEntity.setVelocity(player.getLocation().toVector().subtract(hitEntity.getLocation().toVector()).normalize().multiply(4));
                                    break;
                                }
                                // rendering the grappling pieces
                                grapplingPieces.push(getGrapplingPiece(startLoc.subtract(0, 2, 0)));
                                startLoc.add(0, 2, 0);
                            }
                        }
                        // if we hit a block
                        else if(hitBlock) {
                            player.setVelocity(player.getVelocity().add(block.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(0.3)));
                            for(int i = 0; i < 3; i++) {
                                if(!grapplingPieces.isEmpty()) {
                                    ArmorStand armorStand = grapplingPieces.pop();
                                    armorStand.remove();
                                }
                            }
                            if(grapplingPieces.isEmpty())
                                this.cancel();
                        }
                        // or else retract the grapple
                        else {
                            for(int i = 0; i < 3; i++) {
                                if(!grapplingPieces.isEmpty()) {
                                    ArmorStand armorStand = grapplingPieces.pop();
                                    armorStand.remove();
                                }
                            }
                            if(grapplingPieces.isEmpty())
                                this.cancel();

                        }
                    }
                }.runTaskTimer(PluginMain.getPlugin(PluginMain.class), 0, 1);
            }
		}
	}

    public ArmorStand getGrapplingPiece(Location loc) {
        ArmorStand armorStand = (ArmorStand)loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.getEquipment().setHelmet(new ItemStack(Material.CHAIN, 1), true);
        armorStand.setHeadPose(new EulerAngle(Math.toRadians(loc.getPitch()) + (Math.PI / 2), 0, 0));
        return armorStand;
    }
}
