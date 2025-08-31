package id.my.four.worldnetwork.command;

import id.my.four.worldnetwork.handler.GroupHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;

public class DelwarpCommand implements CommandExecutor {
    File wfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "warp.yml");
    YamlConfiguration warp = YamlConfiguration.loadConfiguration(wfile);
    String g;

    @Override
    public boolean onCommand(CommandSender sender, Command smd, String labe, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Only player can execute this command");
                return false;
            }
        }
        if (args.length == 0) {
            return false;
        } else {
            if (!(args.length == 1)) {
                g = GroupHandler.GroupValidator(args[1]);
                if (g == null) {
                    sender.sendMessage(ChatColor.RED + "That group didn't exist");
                    return true;
                }
            } else {
                Player player = (Player) sender;
                String world = player.getWorld().getName();
                g = GroupHandler.GetGroup(world);
                if (g == null) {
                    sender.sendMessage(ChatColor.RED + "This world is not in any group on group.yml, maybe a typo?");
                    return true;
                }
            }

            for (String wn : warp.getConfigurationSection(g).getKeys(false)) {
                if (wn.equals(args[0])) {
                    try {
                        warp.set(g + "." + args[0], null);
                        warp.save(wfile);
                        sender.sendMessage(ChatColor.GREEN + "Warp " + wn + " has been removed from group " + g);
                        return true;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            sender.sendMessage(ChatColor.RED + "That warp didn't exist");
        }
    return true;
    }
}
