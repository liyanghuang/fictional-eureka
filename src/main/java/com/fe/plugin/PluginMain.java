package com.fe.plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.fe.main.Initializer;
import com.fe.modules.DependencyModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class PluginMain extends JavaPlugin implements Provider<PluginMain>{

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new DependencyModule());
        injector.getInstance(Initializer.class);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public PluginMain get() {
        return this;
    }
}
