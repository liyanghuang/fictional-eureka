package com.fe.main;

import com.fe.plugin.PluginMain;
import com.google.inject.Inject;

public class InitializerImpl implements Initializer{

    private PluginMain plugin;
    private ListenerRegistrar listenerRegistrar;

    @Inject
    public InitializerImpl(PluginMain plugin, ListenerRegistrar listenerRegistrar){
        this.plugin = plugin;
        this.listenerRegistrar = listenerRegistrar;
    }

    @Override
    public void initialize() {
        this.listenerRegistrar.registerAllListeners(plugin); // plugin registration
    }


    
    
}
