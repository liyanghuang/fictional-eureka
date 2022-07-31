package com.fe.modules.providers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.RainBow;
import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.TrackingBow;
import com.fe.plugin.ServerEventsListener;
import com.fe.util.Constants;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

// use injection to get all listeners we need
public class ListenerProvider implements Provider<List<Listener>>{

    private final TrackingBow trackingBow;
    private final ServerEventsListener serverEventsListener;
    private final RainBow rainBow;

    @Inject
    public ListenerProvider(final ServerEventsListener serverEventsListener, final TrackingBow trackingBow, final RainBow rainBow) {
        this.serverEventsListener = serverEventsListener;
        this.trackingBow = trackingBow;
        this.rainBow = rainBow;
    }

    @Override
    @Named(Constants.ServerConstants.ALL_LISTENERS)
    public List<Listener> get() {
        List<Listener> allListeners = new ArrayList<Listener>();
        allListeners.add(this.serverEventsListener);
        allListeners.add(this.trackingBow);
        allListeners.add(this.rainBow);
        return allListeners;
    }
    
}
