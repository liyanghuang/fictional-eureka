package com.fe.items.CustomUnstackableItems.EpicUnstackableItems;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;

public class BoomStick extends EpicUnstackableItem implements Listener{

    public BoomStick() {
        super(
            Material.GOLDEN_SWORD, 
            "BOOM Stick", 
            new String[] {CustomEnchantments.BOOM_STICK}, 
            "boom");
    }

    @EventHandler
    public void whenEntityDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof LivingEntity) {
            LivingEntity damager = (LivingEntity) event.getDamager();
            if(CustomEnchantManager.hasEnchantText(damager.getEquipment().getItemInMainHand(), CustomEnchantments.BOOM_STICK) &&
                event.getCause() == DamageCause.ENTITY_ATTACK){
                damager.getWorld().createExplosion(event.getEntity().getLocation() , 5, false, false, damager);
            }
        }
    }
    
}
