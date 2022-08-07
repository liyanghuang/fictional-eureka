package com.fe.util;


import java.util.Random;

import org.bukkit.NamespacedKey;
import org.bukkit.metadata.FixedMetadataValue;

import com.fe.plugin.PluginMain;

public class Constants {
    public static final class ServerConstants {
        public static final String ALL_LISTENERS = "AllListeners";
        public static final String DONT_DUPE = "DONT_DUPE";
        public static final NamespacedKey AIR_STRIKE_IDENTIFIER = new NamespacedKey(PluginMain.getPlugin(PluginMain.class), "AIR_STRIKE_IDENTIFIER");
        public static final NamespacedKey KAMI_KAZE_IDENTIFIER = new NamespacedKey(PluginMain.getPlugin(PluginMain.class), "KAMI_KAZE_IDENTIFIER");
        public static final NamespacedKey STAFF_OF_ICE_IDENTIFIER = new NamespacedKey(PluginMain.getPlugin(PluginMain.class), "STAFF_OF_ICE_IDENTIFIER");
        public static final int PERSISTENT_DATA_TYPE_TRUE = 1;
        public static final int PERSISTENT_DATA_TYPE_FALSE = 0;
        public static final FixedMetadataValue FIXED_METADATA_TRUE = new FixedMetadataValue(PluginMain.getPlugin(PluginMain.class), true);
        public static final Random RANDOM = new Random();
        public static final NamespacedKey WABBAJACK_IDENTIFIER = new NamespacedKey(PluginMain.getPlugin(PluginMain.class), "WABBAJACK_IDENTIFIER");
    }
    public static final class ITEM_CONSTANTS {
        public static final String DONT_DO_BLOCK_EVENTS = "DONT_DO_BLOCK_EVENTS";
    }
}
