package me.yourname.freecamdetector;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class FreecamDetector extends JavaPlugin {

    private List<String> blockedMods;

    @Override
    public void onEnable() {

        blockedMods = Arrays.asList(
                "freecam",
                "freecam-fabric"
        );

        new FabricLoginListener(this);

        getLogger().info("FreecamDetector enabled.");
    }

    public List<String> getBlockedMods() {
        return blockedMods;
    }
}
