package com.fe.modules.providers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.AirJordans;
import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.KamiKaze;
import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.RainBow;
import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.SpikeWave;
import com.fe.items.CustomUnstackableItems.EpicUnstackableItems.TrackingBow;
import com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems.AirStrike;
import com.fe.items.CustomUnstackableItems.LegendaryUnstackableItems.StaffOfIce;
import com.fe.items.CustomUnstackableItems.MythicUnstackableItems.DeathNote;
import com.fe.items.CustomUnstackableItems.MythicUnstackableItems.Wabbajack;
import com.fe.plugin.ServerEventsListener;
import com.fe.util.Constants;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

// use injection to get all listeners we need
public class ListenerProvider implements Provider<List<Listener>>{

    @Override
    @Named(Constants.ServerConstants.ALL_LISTENERS)
    public List<Listener> get() {
        List<Listener> allListeners = new ArrayList<Listener>();
        allListeners.add(new ServerEventsListener());
        allListeners.add(new TrackingBow());
        allListeners.add(new RainBow());
        allListeners.add(new AirStrike());
        allListeners.add(new AirJordans());
        allListeners.add(new Wabbajack());
        allListeners.add(new StaffOfIce());
        allListeners.add(new DeathNote());
        allListeners.add(new SpikeWave());
        allListeners.add(new KamiKaze());
        return allListeners;
    }
    
}
