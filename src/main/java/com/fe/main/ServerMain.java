package com.fe.main;

import org.bukkit.metadata.FixedMetadataValue;

import com.fe.enchants.CustomEnchantManager;
import com.fe.plugin.PluginMain;
import com.fe.util.RNG;
import com.google.inject.Inject;

// class containing resources and other helpful methods
public class ServerMain {

    private final PluginMain plugin;
    private final RNG rng;

    @Inject
    public ServerMain(final PluginMain plugin, final CustomEnchantManager customEnchantManager, final RNG rng) {
        this.plugin = plugin;
        this.rng = rng;
    }
    
}
