package net.point7845.bedrockpingfix;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.retrooper.packetevents.PacketEvents;

public final class Bedrockpingfix extends JavaPlugin {

    private BedRockPing bedRockPing;

    @Override
    public void onEnable() {
        try {
            bedRockPing = new BedRockPing(this);
            PacketEvents.getAPI().getEventManager().registerListener(bedRockPing);
        } catch (Exception e) {
            getLogger().severe("Failed to initialize BedrockPingFix plugin: " + e.getMessage());
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (bedRockPing != null) {
            PacketEvents.getAPI().getEventManager().unregisterListener(bedRockPing);
        }
    }
}
