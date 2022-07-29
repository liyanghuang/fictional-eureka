package com.fe.modules.providers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

import com.fe.main.ServerMain;
import com.fe.plugin.ServerEventsListener;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class ListenerProvider implements Provider<List<Listener>>{

    private ServerMain serverMain;

    @Inject
    public ListenerProvider(ServerMain serverMain) {
        this.serverMain = serverMain;
    }

    @Override
    @Named("AllListeners")
    public List<Listener> get() {
        List<Listener> allListeners = new ArrayList<Listener>();
        allListeners.add(new ServerEventsListener(serverMain));
        return allListeners;
    }
    
}
