package net.point7845.bedrockpingfix;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;

import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.connection.GeyserConnection;

public class BedRockPing extends PacketListenerAbstract  {

    private final Plugin plugin;

    public BedRockPing(Plugin plugin) {
        this.plugin = plugin;
    }

    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.PLAYER_INFO_UPDATE) {
            WrapperPlayServerPlayerInfoUpdate packet = new WrapperPlayServerPlayerInfoUpdate(event);

            packet.getEntries().forEach(entry -> {
                Player bukkitPlayer = plugin.getServer().getPlayer(entry.getGameProfile().getUUID());
                if (bukkitPlayer != null) {
                    GeyserConnection conn = GeyserApi.api().connectionByUuid(bukkitPlayer.getUniqueId());
                    if (conn != null) {
                        entry.setLatency(conn.ping());
                    }
                }
            });
        }
    }
}
