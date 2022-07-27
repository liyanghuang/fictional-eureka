package com.fe.modules.providers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

import com.fe.plugin.ServerEventsListener;
import com.google.inject.Provider;

public class ListenerProvider implements Provider<List<Listener>>{

    @Override
    public List<Listener> get() {
        List<Listener> allListeners = new ArrayList<Listener>();
        allListeners.add(new ServerEventsListener());
        return allListeners;
    }
    
}
