package id.my.four.worldnetwork.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public final class HubCommand implements CommandExecutor {

    File sFile = new File(Bukkit.getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "spawn.yml");
    YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);
    Location loc;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (!(spawn.get("hub.location").equals("None"))) {
            loc = (Location) spawn.get("hub.location");
        } else {
            loc = null;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only player can use this command");
            return false;
        } else {
            if (!(loc == null)) {
                p.teleport(loc);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Hub didn't set");
                return true;
            }
        }
    }
}
