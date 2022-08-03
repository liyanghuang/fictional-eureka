package com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;
import com.fe.items.CustomUnstackableItems.CustomUnstackableItem;
import com.fe.plugin.PluginMain;
import com.fe.util.Constants;

public class StaffOfIce extends LegendaryUnstackableItem implements Listener{

    private static final boolean [][][] iceSculpture = {
        {	
            {true, true, true, true, true},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {false, true, false, true, false},
            {true, true, true, false, true}
        },
        {	
            {false, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, false, false, true, false},
            {false, true, true, false, false}
        },
        {	
            {false, true, false, false, false},
            {true, false, true, true, false},
            {true, false, false, true, true},
            {false, true, false, true, false},
            {false, false, true, false, false}
        },
        {	
            {false, false, false, false, false},
            {false, true, true, true, false},
            {true, true, false, true, true},
            {false, true, true, false, false},
            {false, false, false, false, false}
        },
        {	
            {false, false, false, false, false},
            {false, false, true, false, false},
            {false, true, true, true, false},
            {false, true, false, false, false},
            {false, false, false, false, false}
        },
        {	
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, true, true, false},
            {false, false, false, false, false},
            {false, false, false, false, false}
        },
        {	
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, true, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false}
        }
    };

    public StaffOfIce() {
        super(
            Material.BONE,
            "Staff of Ice",
            new String [] {CustomEnchantments.STAFF_OF_ICE},
            "ice in the veins yuh"
        );
    }

    	

	@EventHandler
	public void playerInteract(final PlayerInteractEvent event)
	{
		if((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) 
            && CustomEnchantManager.hasEnchantText(event.getPlayer().getInventory().getItemInMainHand(), CustomEnchantments.STAFF_OF_ICE))
		{

            final ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            if(CustomUnstackableItem.isAvailable(Constants.ServerConstants.STAFF_OF_ICE_IDENTIFIER, item, 5000)) {
                CustomUnstackableItem.setCooldown(Constants.ServerConstants.STAFF_OF_ICE_IDENTIFIER, item);
                event.getPlayer().setCooldown(Material.BONE, 100);
                Snowball snow = event.getPlayer().launchProjectile(Snowball.class, event.getPlayer().getEyeLocation().getDirection().multiply(2));
                snow.setMetadata(CustomEnchantments.STAFF_OF_ICE, Constants.ServerConstants.FIXED_METADATA_TRUE);
            }
		}
	}
	
	@EventHandler
	public void projectileHit(final ProjectileHitEvent event)
	{
		if(event.getEntity().hasMetadata(CustomEnchantments.STAFF_OF_ICE) && event.getHitEntity() != null)
		{
			List<Block> blocks = collectBlocks(event.getHitEntity().getLocation());
			for(Block b : blocks)
			{
				if(b.getType() == Material.AIR || b.getType() == Material.GRASS || b.getType() == Material.SNOW)
					b.setType(Material.PACKED_ICE);
			}
			new BukkitRunnable()
			{
				public void run()
				{
					for(Block b : blocks)
					{
						if(b.getType() == Material.PACKED_ICE)
							b.setType(Material.AIR);
					}
				}
			}.runTaskLater(PluginMain.getPlugin(PluginMain.class), 100);
		}
	}
	
	private List<Block> collectBlocks(Location loc)
	{
		List<Block> blocks = new ArrayList<Block>();
		for(int h = 0; h < 7; h++)
		{
			for(int i = -2; i < 3; i++)
			{
				for(int j = -2; j < 3; j++)
				{
					if(iceSculpture[h][i+2][j+2])
					{
						blocks.add(loc.getWorld().getBlockAt(loc.add(i, h, j)));
						loc.subtract(i, h, j);
					}
				}
			}
		}
		return blocks;
	}
    
}
