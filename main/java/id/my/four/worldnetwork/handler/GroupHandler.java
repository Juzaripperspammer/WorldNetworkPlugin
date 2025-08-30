package id.my.four.worldnetwork.handler;

import id.my.four.worldnetwork.handler.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.*;
import org.bukkit.event.Listener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupHandler {

    static YamlConfiguration gr = FileHandler.get("group");

    public static String GetGroup(String world) {
        for (String g : gr.getConfigurationSection("group").getKeys(false)) {
            for (String gp : gr.getStringList("group." + g)) {
                if (gp.equals(world)) {
                    return g;
                }
            }
        }
        return null;
    }
}

