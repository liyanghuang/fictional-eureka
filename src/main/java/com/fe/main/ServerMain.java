package com.fe.main;

import com.fe.enchants.CustomEnchantManager;
import com.fe.plugin.PluginMain;
import com.fe.util.RNG;
import com.google.inject.Inject;

// class containing resources and other helpful methods
public class ServerMain {

    private PluginMain plugin;
    private CustomEnchantManager customEnchantManager;
    private RNG rng;

    @Inject
    public ServerMain(PluginMain plugin, CustomEnchantManager customEnchantManager, RNG rng) {
        this.plugin = plugin;
        this.customEnchantManager = customEnchantManager;
        this.rng = rng;
    }

    public CustomEnchantManager getCustomEnchantManager() {
        return this.customEnchantManager;
    }
    
}
