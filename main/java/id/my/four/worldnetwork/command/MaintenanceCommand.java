package id.my.four.worldnetwork.command;

import id.my.four.worldnetwork.handler.GroupHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.ChannelNameTooLongException;

import java.io.*;

public final class MaintenanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File cfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(cfile);
        File gfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "group.yml");
        YamlConfiguration group = YamlConfiguration.loadConfiguration(gfile);
        String g;
        if (args.length > 1) {
                if (!(args.length == 2)) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        String w = p.getWorld().getName();
                        g = GroupHandler.GetGroup(w);
                    } else {
                        return false;
                    }
                } else {
                    g = (String) args[1];
                    for (String gv : group.getConfigurationSection("group").getKeys(false)) {
                        if (!(g == gv)) {
                            sender.sendMessage(ChatColor.RED + "That group didn't exist");
                            return false;
                        }
                    }
                }

                Boolean mt = config.getBoolean("maintenance." + g);

                if (args[0].equals("on")) {
                    if (!(mt == true)) {
                        sender.sendMessage(ChatColor.RED + "That server is already under maintenance");
                        return true;
                    } else {
                        try {
                            config.set("maintenance." + g, true);
                            config.save(cfile);
                            sender.sendMessage(ChatColor.GREEN + g + " is now under maintenance");
                            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                String w = p.getWorld().getName();
                                String gs = GroupHandler.GetGroup(w);
                                if (gs == g) {
                                    p.kickPlayer("The server is under maintenance");
                                }
                            }
                            return true;
                        } catch (IOException e) {
                            Bukkit.getConsoleSender().sendMessage("Failed to maintenance" + g + "cause by: " + e);
                        }
                    }
                } else if (args[0].equals("off")) {
                    if (!(mt == false)) {
                        sender.sendMessage(ChatColor.RED + "That server is not under maintenance");
                        return true;
                    } else {
                        try {
                            config.set("maintenance." + g, false);
                            config.save(cfile);
                            sender.sendMessage(ChatColor.GREEN + g + " is now online!");
                            return true;
                        } catch (IOException e) {
                            Bukkit.getConsoleSender().sendMessage("Failed to unmaintenance" + g + "cause by: " + e);
                        }
                    }
                }
        } else {
            return false;
        }
        return true;
    }
}
