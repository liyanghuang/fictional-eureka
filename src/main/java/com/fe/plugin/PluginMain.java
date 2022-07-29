package com.fe.plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.fe.main.Initializer;
import com.fe.modules.DependencyModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PluginMain extends JavaPlugin{

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new DependencyModule(this));
        Initializer initializer = injector.getInstance(Initializer.class);
        initializer.initialize();
    }

    @Override
    public void onDisable() {
    }
}
