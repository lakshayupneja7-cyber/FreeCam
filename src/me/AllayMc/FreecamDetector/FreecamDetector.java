package me.yourname.freecamdetector;

import org.bukkit.plugin.java.JavaPlugin;

public class FreecamDetector extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("FreecamDetector enabled.");
        new FabricModListener(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("FreecamDetector disabled.");
    }
}
