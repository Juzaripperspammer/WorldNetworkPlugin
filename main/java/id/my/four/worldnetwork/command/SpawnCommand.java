package id.my.four.worldnetwork.command;

import id.my.four.worldnetwork.handler.GroupHandler;
import id.my.four.worldnetwork.handler.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;

public final class SpawnCommand implements CommandExecutor {

    FileHandler fileHandler = new FileHandler();

    public SpawnCommand() {

    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only player can use /spawn use /spawn <player> instead");
            }   else if (sender instanceof Player) {
                {
                    Player player = (Player) sender;
                    String world = player.getWorld().getName();
                    File sFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "spawn.yml");
                    YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);
                    String g = GroupHandler.GetGroup(world);
                    if (g == null) {
                        player.sendMessage(ChatColor.RED + "This world is not in any group on group.yml, maybe a typo?");
                        return true;
                    }
                    Location loc = (Location) spawn.get(g + ".location");

                    if (!(loc == null)) {
                        player.teleport(loc);

                    } else {
                        sender.sendMessage(ChatColor.RED + "Spawn didn't set");
                    }
                }
            }
        }else if (args.length == 1) {
            if (sender.hasPermission("worldnetwork.spawnplayer")) {
                Player player = null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Player ps = (Player) p;
                    String np = ps.getName();
                    if (np.equals(args[0])) {
                        player = ps;
                    }
                }
                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "That player is not online or never join");
                    return true;
                }
                String world = player.getWorld().getName();
                File sFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "spawn.yml");
                YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);
                String g = GroupHandler.GetGroup(world);
                if (g == null) {
                    sender.sendMessage(ChatColor.RED + "That player world is not in any group on group.yml, maybe a typo?");
                    return true;
                }
                Location loc = (Location) spawn.get(g + ".location");

                if (!(loc == null)) {
                    player.teleport(loc);

                } else {
                    sender.sendMessage(ChatColor.RED + "Spawn didn't set in that player group");
                }
            }
        }
        return true;
    }
}

