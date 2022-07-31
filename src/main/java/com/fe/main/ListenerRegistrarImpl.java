package com.fe.main;

import java.util.List;

import org.bukkit.event.Listener;

import com.fe.plugin.PluginMain;
import com.fe.util.Constants;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ListenerRegistrarImpl implements ListenerRegistrar{

    private final List<Listener> allListeners;

    @Inject
    public ListenerRegistrarImpl(@Named(Constants.ServerConstants.ALL_LISTENERS) final List<Listener> allListeners) {
        this.allListeners = allListeners;
    }

    public void registerAllListeners(final PluginMain plugin) {
        for (final Listener listener : this.allListeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}

