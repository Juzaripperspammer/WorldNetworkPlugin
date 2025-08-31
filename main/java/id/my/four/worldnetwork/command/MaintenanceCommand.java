package id.my.four.worldnetwork.command;

import id.my.four.worldnetwork.handler.GroupHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.ChannelNameTooLongException;

import java.io.*;
import java.util.Set;

public final class MaintenanceCommand implements CommandExecutor {

    File cfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(cfile);
    File gfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "group.yml");
    YamlConfiguration group = YamlConfiguration.loadConfiguration(gfile);
    File sFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "spawn.yml");
    YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);
    Location loc;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String g;
        Boolean t = true;
        Boolean f = false;
        String gvalid = null;

        if (args.length > 0) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    String wo = p.getWorld().getName();
                    g = GroupHandler.GetGroup(wo);
                    if (g == null) {
                        sender.sendMessage(ChatColor.RED + "This world is not in any group on group.yml, maybe a typo?");
                        return true;
                    }
                } else {
                    sender.sendMessage("Only player can do this command");
                    return false;
                }
            } else {
                g = GroupHandler.GroupValidator(args[1]);
                if (g == null) {
                    sender.sendMessage(ChatColor.RED + "That group didn't exist");
                    return false;
                }
            }
            if (!(spawn.get("hub.location").equals("None"))) {
                loc = (Location) spawn.get("hub.location");
            }

            Boolean mt = config.getBoolean("maintenance." + g);

            if (args[0].equals("on")) {
                if (!(!mt)) {
                    sender.sendMessage(ChatColor.RED + "That server is already under maintenance");
                    return true;
                } else {
                    try {
                        config.set("maintenance." + g, t);
                        config.save(cfile);
                        sender.sendMessage(ChatColor.GREEN + g + " is now under maintenance");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            String w = p.getWorld().getName();
                            String gs = GroupHandler.GetGroup(w);
                            if (!(gs == null)) {
                                if (gs.equals(g)) {
                                    if (loc == null) {
                                        p.kickPlayer("The server is under maintenance");
                                    } else {
                                        p.teleport(loc);
                                        p.sendMessage(ChatColor.RED + gs + " is currently under maintenace");
                                    }
                                }
                            }
                        }
                        return true;
                    } catch (IOException e) {
                        Bukkit.getConsoleSender().sendMessage("Failed to maintenance" + g + "cause by: " + e);
                    }
                }
            } else if (args[0].equals("off")) {
                if (!(mt)) {
                    sender.sendMessage(ChatColor.RED + "That server is not under maintenance");
                    return true;
                } else {
                    try {
                        config.set("maintenance." + g, f);
                        config.save(cfile);
                        sender.sendMessage(ChatColor.GREEN + g + " is now online!");
                        return true;
                    } catch (IOException e) {
                        Bukkit.getConsoleSender().sendMessage("Failed to unmaintenance" + g + "cause by: " + e);
                    }
                }
            }
        } else if (args.length == 0) {
            return false;
        }
        return true;
    }
}
