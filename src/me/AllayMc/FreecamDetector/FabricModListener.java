package me.yourname.freecamdetector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.nio.charset.StandardCharsets;

public class FabricModListener {

    public FabricModListener(Plugin plugin) {

        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin,
                        ListenerPriority.NORMAL,
                        PacketType.Login.Client.PLUGIN_RESPONSE) {

                    @Override
                    public void onPacketReceiving(PacketEvent event) {

                        if (event.getPlayer() == null) return;

                        try {
                            byte[] data = event.getPacket().getByteArrays().read(0);

                            if (data == null || data.length == 0) return;

                            String content = new String(data, StandardCharsets.UTF_8).toLowerCase();

                            if (content.contains("freecam")) {

                                Player player = event.getPlayer();

                                Bukkit.getScheduler().runTask(plugin, () -> {
                                    player.kickPlayer("§cFreecam is not allowed on this SMP.");
                                });

                                plugin.getLogger().warning("Blocked player "
                                        + player.getName()
                                        + " for using Freecam.");
                            }

                        } catch (Exception e) {
                            plugin.getLogger().warning("Error reading login packet: " + e.getMessage());
                        }
                    }
                });
    }
}
