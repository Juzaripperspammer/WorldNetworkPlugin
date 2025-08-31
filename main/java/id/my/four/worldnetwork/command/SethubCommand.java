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

public final class SethubCommand implements CommandExecutor {

    File sFile = new File(Bukkit.getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "spawn.yml");
    YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            try {
                Player player = (Player) sender;
                Location loc = player.getLocation();
                spawn.set("hub.location", loc);
                spawn.save(sFile);
                sender.sendMessage(ChatColor.GREEN + "Hub has been set!");
                return true;
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to set hub because: " + e);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only player can execute this command");
            return false;
        }
        return true;
    }
}
