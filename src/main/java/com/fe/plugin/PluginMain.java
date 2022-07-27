package com.fe.plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.fe.enchants.CustomEnchantManager;
import com.fe.modules.DependencyModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PluginMain extends JavaPlugin{   

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new DependencyModule());
        getServer().getPluginManager().registerEvents(new MasterListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
