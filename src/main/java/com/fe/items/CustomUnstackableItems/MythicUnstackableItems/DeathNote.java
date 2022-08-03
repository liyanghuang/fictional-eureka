package com.fe.items.CustomUnstackableItems.MythicUnstackableItems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;

import net.md_5.bungee.api.ChatColor;

public class DeathNote extends MythicUnstackableItem implements Listener{

    public DeathNote() {
        super(Material.WRITABLE_BOOK, 
        CustomEnchantManager.formatString("#4e0577D#470477e#3f0477a#380377t#310377h #290277N#220177o#1a0177t#130077e", true, false, false),
        new String[] {ChatColor.RED + CustomEnchantments.DEATH_NOTE, ChatColor.RED + "Curse of Vanishing"},
        "Using it seems to lead to painful consequences.");

        this.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
    }

    @EventHandler
    public void handleEditEvent(final PlayerEditBookEvent event) {
        if(CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.DEATH_NOTE) ||
            CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInOffHand(), CustomEnchantments.DEATH_NOTE)) {
            final BookMeta bmeta = event.getNewBookMeta();
            for(String page : bmeta.getPages()) {
                final String[] names = page.split("[\\s]+");
                for(String name: names) {
                    final Player player = Bukkit.getPlayerExact(name);
                    final Player killer = event.getPlayer();
                    if(player == null)
                        continue;
                    player.damage(1, event.getPlayer());
                    player.setHealth(0);
                    if(!player.getName().equals(killer.getName())) {
                        killer.damage(1, event.getPlayer());
                        killer.setHealth(1);
                        killer.setFoodLevel(1);
                    }
                }
            }
            if(CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.DEATH_NOTE))
                event.getPlayer().getInventory().setItemInMainHand(new DeathNote());
            if(CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInOffHand(), CustomEnchantments.DEATH_NOTE))
                event.getPlayer().getInventory().setItemInOffHand(new DeathNote());
            event.setCancelled(true);
        }
    }
    
}
