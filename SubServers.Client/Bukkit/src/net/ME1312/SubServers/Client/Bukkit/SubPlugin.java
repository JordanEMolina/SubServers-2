package net.ME1312.SubServers.Client.Bukkit;

import net.ME1312.SubServers.Client.Bukkit.Graphic.UIListener;
import net.ME1312.SubServers.Client.Bukkit.Library.Config.YAMLConfig;
import net.ME1312.SubServers.Client.Bukkit.Library.Config.YAMLSection;
import net.ME1312.SubServers.Client.Bukkit.Library.UniversalFile;
import net.ME1312.SubServers.Client.Bukkit.Library.Util;
import net.ME1312.SubServers.Client.Bukkit.Library.Version.Version;
import net.ME1312.SubServers.Client.Bukkit.Network.SubDataClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;

/**
 * SubServers Client Plugin Class
 */
public final class SubPlugin extends JavaPlugin {
    public YAMLConfig pluginconf;
    public YAMLSection lang = null;
    public SubDataClient subdata = null;

    public UIListener gui = null;
    public final Version version;
    public final Version bversion = new Version(2);
    
    //public final SubAPI api = new SubAPI(this);

    public SubPlugin() {
        super();
        version = new Version(getDescription().getVersion());
    }

    /**
     * Enable Plugin
     */
    @Override
    public void onEnable() {
        try {
            Bukkit.getLogger().info("SubServers > Loading SubServers v" + version.toString() + " Libraries... ");
            getDataFolder().mkdirs();
            if (!(new UniversalFile(getDataFolder(), "config.yml").exists())) {
                Util.copyFromJar(SubPlugin.class.getClassLoader(), "config.yml", new UniversalFile(getDataFolder(), "config.yml").getPath());
                Bukkit.getLogger().info("SubServers > Created ~/plugins/SubServers/config.yml");
            } else if ((new Version((new YAMLConfig(new UniversalFile(getDataFolder(), "config.yml"))).get().getSection("Settings").getString("Version", "0")).compareTo(new Version("2.11.2a+"))) != 0) {
                Files.move(new UniversalFile(getDataFolder(), "config.yml").toPath(), new UniversalFile(getDataFolder(), "config.old" + Math.round(Math.random() * 100000) + ".yml").toPath());

                Util.copyFromJar(SubPlugin.class.getClassLoader(), "config.yml", new UniversalFile(getDataFolder(), "config.yml").getPath());
                Bukkit.getLogger().info("SubServers > Updated ~/plugins/SubServers/config.yml");
            }
            pluginconf = new YAMLConfig(new UniversalFile(getDataFolder(), "config.yml"));
            subdata = new SubDataClient(this, pluginconf.get().getSection("Settings").getSection("SubData").getString("Name", "undefined"),
                    InetAddress.getByName(pluginconf.get().getSection("Settings").getSection("SubData").getString("Address", "127.0.0.1:4391").split(":")[0]),
                    Integer.parseInt(pluginconf.get().getSection("Settings").getSection("SubData").getString("Address", "127.0.0.1:4391").split(":")[1]));

            gui = new UIListener(this);
            SubCommand cmd = new SubCommand(this);
            getCommand("subservers").setExecutor(cmd);
            getCommand("subserver").setExecutor(cmd);
            getCommand("sub").setExecutor(cmd);
        } catch (IOException e) {
            setEnabled(false);
            e.printStackTrace();
        }
    }

    /**
     * Disable Plugin
     */
    @Override
    public void onDisable() {
        if (subdata != null)
            try {
                subdata.destroy(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
