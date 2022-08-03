package com.fe.items.CustomUnstackableItems.MythicUnstackableItems;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;

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
}
