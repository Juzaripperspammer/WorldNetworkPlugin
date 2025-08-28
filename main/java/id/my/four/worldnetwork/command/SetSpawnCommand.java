package id.my.four.worldnetwork.command;

import id.my.four.worldnetwork.WorldNetwork;
import id.my.four.worldnetwork.handler.GroupHandler;
import id.my.four.worldnetwork.handler.FileHandler;
import com.sun.tools.javac.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.World;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.spi.AbstractResourceBundleProvider;

public final class SetSpawnCommand implements CommandExecutor {

    FileHandler Filehandler = new FileHandler();

    File sFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "spawn.yml");
    YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);

    public SetSpawnCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        if (sender.hasPermission("worldnetwork.setspawn")){
            Player player = (Player) sender;
            String world = player.getWorld().getName();
            Location loc = player.getLocation();

            try {
                String g;
                g = GroupHandler.GetGroup(world);
                if (g == null) {
                    player.sendMessage(ChatColor.RED + "This world is not in any group on group.yml, maybe a typo?");
                    return false;
                }
                spawn.set(g + ".location", loc);
                spawn.save(sFile);
                sender.sendMessage(ChatColor.GREEN + "Spawn set for group: " + g);
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to save spawn location", e);;
            }

        }
        return true;
    }

}