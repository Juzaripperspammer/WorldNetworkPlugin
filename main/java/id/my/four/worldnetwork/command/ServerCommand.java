package id.my.four.worldnetwork.command;

import id.my.four.worldnetwork.handler.GroupHandler;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;

public final class ServerCommand implements CommandExecutor {
    File sFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "spawn.yml");
    YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);
    File cFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(cFile);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0){
            return false;
        }
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only player can use that command");
            } else {
                Player player = (Player) sender;
                String world = player.getWorld().getName();
                String gr = GroupHandler.GetGroup(world);

                for (String gets : config.getConfigurationSection("server-name").getKeys(false)) {
                    String sname = config.getString("server-name." + gets);
                    if (args[0].equals(sname)) {
                        Boolean mt = config.getBoolean("maintenance." + gets);
                        if (mt.equals(true)) {
                            sender.sendMessage(ChatColor.RED + "That server is under maintenance");
                            return true;
                        }
                        if (gets.equals(gr)) {
                            sender.sendMessage(ChatColor.RED + "You already in this server");
                            return true;
                        }

                        Location loc = (Location) spawn.get(gets + ".location");

                        if (!(loc == null)) {
                            player.teleport(loc);
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "That server didn't exist");
                            return true;
                        }
                    }
                }
                sender.sendMessage(ChatColor.RED + "That server didn't exist");
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("worldnetwork.serversend")) {
                Player player = null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Player ps = (Player) p;
                    String np = ps.getName();
                    if (np.equals(args[1])) {
                        player = ps;
                    }
                }
                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "That player is not online or never join");
                    return true;
                }
                String world = player.getWorld().getName();
                String gr = GroupHandler.GetGroup(world);

                for (String gets : config.getConfigurationSection("server-name").getKeys(false)) {
                    String sname = config.getString("server-name." + gets);
                    if (args[0].equals(sname)) {
                        Boolean mt = config.getBoolean("maintenance." + gets);
                        if (mt.equals(true)) {
                            sender.sendMessage(ChatColor.RED + "That server is under maintenance");
                            return true;
                        }
                        if (gets.equals(gr)) {
                            sender.sendMessage(ChatColor.RED + "That player already in the server");
                            return true;
                        }

                        Location loc = (Location) spawn.get(gets + ".location");

                        if (!(loc == null)) {
                            player.teleport(loc);
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "That server didn't exist");
                            return true;
                        }
                    }
                }
                sender.sendMessage(ChatColor.RED + "That server didn't exist");
            } else {
                return false;
            }
        }
        return true;
    }
}
