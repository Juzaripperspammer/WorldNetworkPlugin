package id.my.four.worldnetwork.handler;

import id.my.four.worldnetwork.WorldNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.*;

import java.io.*;

public class GroupHandler {

    File groupf = new File(WorldNetwork.getInstance().getDataFolder(), "group.yml");
    FileConfiguration group;

    public boolean worldName(Player player) {
        if (!groupf.exists()) {

        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Group file didn't found Restaring");
            WorldNetwork.getInstance().reload();
        }
        return true;
    }

}