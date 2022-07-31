package com.fe.main;

import com.fe.plugin.PluginMain;
import com.google.inject.Inject;

public class InitializerImpl implements Initializer{

    private final PluginMain plugin;
    private final ListenerRegistrar listenerRegistrar;

    @Inject
    public InitializerImpl(final PluginMain plugin, final ListenerRegistrar listenerRegistrar){
        this.plugin = plugin;
        this.listenerRegistrar = listenerRegistrar;
    }

    @Override
    public void initialize() {
        this.listenerRegistrar.registerAllListeners(plugin); // plugin registration
    }


    
    
}
