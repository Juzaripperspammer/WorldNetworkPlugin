package id.my.four.worldnetwork.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.*;
import org.bukkit.event.Listener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupHandler implements Listener {

    static File groupf = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "group.yml");
    static YamlConfiguration gr = YamlConfiguration.loadConfiguration(groupf);

    public static String GetGroup(String world) {
        if (groupf.exists()) {
            for (String g : gr.getConfigurationSection("group").getKeys(false)) {
                for (String gp : gr.getStringList("group." + g)) {
                    if (gp.equalsIgnoreCase(world)) {
                        return g;
                    }
                }
            }
        }
        return null;
    }
}

