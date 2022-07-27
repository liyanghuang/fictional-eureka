package com.fe.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.event.Listener;

import com.fe.main.ListenerRegistrar;
import com.fe.main.ListenerRegistrarImpl;
import com.fe.modules.providers.ListenerProvider;
import com.fe.util.RNG;
import com.fe.util.RNGImpl;
import com.fe.enchants.CustomEnchantManager;
import com.fe.main.Initializer;
import com.fe.main.InitializerImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class DependencyModule extends AbstractModule{
    
    public void configure() {
        bind(Initializer.class).to(InitializerImpl.class).in(Singleton.class);;
        bind(ListenerRegistrar.class).to(ListenerRegistrarImpl.class).in(Singleton.class);;
        bind(RNG.class).to(RNGImpl.class).in(Singleton.class);;
        bind(new TypeLiteral<List<Listener>> () {}).annotatedWith(Names.named("AllListeners")).toProvider(ListenerProvider.class).in(Singleton.class);
    }

    // provide Random to use
    @Provides
    @Singleton
    public Random provideRandom() {
        return new Random();
    }

    // provide enchantment manager
    @Provides
    @Singleton
    public CustomEnchantManager provideCustomEnchantManager() {
        return new CustomEnchantManager();
    }
}
