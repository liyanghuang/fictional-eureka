package com.fe.items.CustomUnstackableItems.EpicUnstackableItems;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;
import com.google.inject.Inject;

public class AirJordans extends EpicUnstackableItem implements Listener{
    
    @Inject
    public AirJordans() {
        super(
            Material.GOLDEN_BOOTS, 
            "Air Jordans", 
            new String[] {CustomEnchantments.AIR_JORDANS, "Feather Falling IX"}, 
            "Expensive shoes that can improve your vertical");
        this.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 9);
    }

    @EventHandler
	public void onToggleFlight(final PlayerToggleFlightEvent event)
	{
        final Player p = event.getPlayer();
        // this is necessary to cancel any flight events that may occur in survival
        if(p.getGameMode() == GameMode.SURVIVAL)
        {
            event.setCancelled(true);
        }

		if(p.getInventory().getBoots() != null && CustomEnchantManager.hasEnchantText(p.getInventory().getBoots(), CustomEnchantments.AIR_JORDANS) && p.getGameMode() == GameMode.SURVIVAL) {
            if(p.getAllowFlight()) {
                p.setFlying(false);
                p.setAllowFlight(false);
                final Vector launchDir = event.getPlayer().getVelocity().multiply(10);
                launchDir.setY(2);
                p.setVelocity(launchDir);
            }
		}
	}
	
	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent event)
	{
        final Player p = event.getPlayer();
		if(p.getInventory().getBoots() != null && CustomEnchantManager.hasEnchantText(p.getInventory().getBoots(), CustomEnchantments.AIR_JORDANS) && p.getGameMode() == GameMode.SURVIVAL) {
            // both are needed b/c is on ground is deprecated for player
            final LivingEntity le = event.getPlayer();
            if(le.isOnGround()) {
                p.setAllowFlight(true);
            }
		}
        else {
            if(p.getGameMode() == GameMode.SURVIVAL)
                p.setAllowFlight(false); // default not allowed to fly, should activate on jump
        }
	}
}
