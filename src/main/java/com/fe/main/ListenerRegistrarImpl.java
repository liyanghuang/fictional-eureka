package com.fe.main;

import java.util.List;

import org.bukkit.event.Listener;

import com.fe.plugin.PluginMain;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ListenerRegistrarImpl implements ListenerRegistrar{

    private List<Listener> allListeners;

    @Inject
    public ListenerRegistrarImpl(@Named("AllListeners") List<Listener> allListeners) {
        this.allListeners = allListeners;
    }

    public void registerAllListeners(PluginMain plugin) {
        for (Listener listener : this.allListeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}

