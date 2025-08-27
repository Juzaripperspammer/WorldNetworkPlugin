package id.my.four.worldnetwork.handler;

import id.my.four.worldnetwork.WorldNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.*;
import org.bukkit.event.Listener;

import java.io.*;

public class GroupHandler implements Listener {

    static File groupf = new File(WorldNetwork.getInstance().getDataFolder(), "group.yml");
    static YamlConfiguration group = YamlConfiguration.loadConfiguration(groupf);

    public static void GetGroup(Player player) {
        if (!groupf.exists()) {
            String world = player.getWorld().getName();

            for (String g : group.getConfigurationSection("group").getKeys(false)) {
                player.sendMessage(ChatColor.GREEN + "group: " + g);
            }

        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Group file didn't found Restaring");
            WorldNetwork.getInstance().reload();
        }
    }

}