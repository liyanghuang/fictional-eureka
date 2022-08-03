package com.fe.items.CustomUnstackableItems.MythicUnstackableItems;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import com.fe.enchants.CustomEnchantManager;
import com.fe.enchants.CustomEnchantments;

public class Wabbajack extends MythicUnstackableItem implements Listener{

    public Wabbajack() {
        super(
            Material.BLAZE_ROD,
            CustomEnchantManager.formatString("#ff95ffL#ff8ef2O#ff86e6L", true, false, true) + "" + 
            CustomEnchantManager.formatString("#fb56c0T#fb50b2H#fb4aa5E #fb4497W#fc3d89A#fc377bB#fc316eB#fc2b60A#fc2552J#fc1f45A#fc1937C#fd1229K#fd0c1b!#fd060e!#fd0000!", true, false, false) + "" +
            CustomEnchantManager.formatString("#fd0f1aL#fd070dO#fd0000L", true, false, true),
            new String[] {CustomEnchantments.WABBAJACK},
            "What does it do..."
        );
    }
}
