package net.ME1312.SubServers.Sync.Network.Packet;

import net.ME1312.Galaxi.Library.Map.ObjectMap;
import net.ME1312.Galaxi.Library.NamedContainer;
import net.ME1312.Galaxi.Library.Util;
import net.ME1312.SubData.Client.Protocol.PacketObjectIn;
import net.ME1312.SubData.Client.Protocol.PacketOut;
import net.ME1312.SubData.Client.SubDataClient;
import net.ME1312.SubServers.Sync.Library.Compatibility.Logger;
import net.ME1312.SubServers.Sync.SubPlugin;

import java.util.Calendar;

/**
 * Download Lang Packet
 */
public class PacketDownloadLang implements PacketObjectIn<Integer>, PacketOut {
    private SubPlugin plugin;

    /**
     * New PacketDownloadLang (In)
     *
     * @param plugin SubServers.Client
     */
    public PacketDownloadLang(SubPlugin plugin) {
        if (Util.isNull(plugin)) throw new NullPointerException();
        this.plugin = plugin;
    }

    /**
     * New PacketDownloadLang (Out)
     */
    public PacketDownloadLang() {}

    @Override
    public void receive(SubDataClient client, ObjectMap<Integer> data) {
        try {
            Util.reflect(SubPlugin.class.getDeclaredField("lang"), plugin, new NamedContainer<>(Calendar.getInstance().getTime().getTime(), data.getObject(0x0001)));
            Logger.get("SubData").info("Lang Settings Downloaded");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int version() {
        return 0x0001;
    }
}
