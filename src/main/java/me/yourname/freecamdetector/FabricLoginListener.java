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
import java.util.List;

public class FabricLoginListener {

    public FabricLoginListener(FreecamDetector plugin) {

        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin,
                        ListenerPriority.NORMAL,
                        PacketType.Login.Client.PLUGIN_RESPONSE) {

                    @Override
                    public void onPacketReceiving(PacketEvent event) {

                        Player player = event.getPlayer();
                        if (player == null) return;

                        try {
                            byte[] data = event.getPacket().getByteArrays().readSafely(0);
                            if (data == null || data.length == 0) return;

                            String payload = new String(data, StandardCharsets.UTF_8).toLowerCase();

                            List<String> blocked = plugin.getBlockedMods();

                            for (String mod : blocked) {
                                if (payload.contains(mod)) {

                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        player.kickPlayer("§cFreecam is not allowed on this SMP.");
                                    });

                                    plugin.getLogger().warning(
                                            "Blocked " + player.getName() +
                                            " for detected mod: " + mod
                                    );

                                    break;
                                }
                            }

                        } catch (Exception ignored) {
                        }
                    }
                });
    }
}
