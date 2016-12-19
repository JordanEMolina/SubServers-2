package net.ME1312.SubServers.Client.Bukkit.Network;

import net.ME1312.SubServers.Client.Bukkit.Library.Version.Version;
import org.json.JSONObject;

/**
 * PacketOut Layout Class
 *
 * @author ME1312
 */
public interface PacketOut {
    /**
     * Generate JSON Packet Contents
     *
     * @return Packet Contents
     */
    JSONObject generate();

    /**
     * Get Packet Version
     *
     * @return Packet Version
     */
    Version getVersion();
}
