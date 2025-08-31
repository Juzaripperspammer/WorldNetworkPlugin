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

import java.io.File;

public final class WarpCommand implements CommandExecutor {
    File wfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "warp.yml");
    YamlConfiguration warp = YamlConfiguration.loadConfiguration(wfile);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only player can execute that command");
            } else {
                Player player = (Player) sender;
                String w = player.getWorld().getName();
                String g = GroupHandler.GetGroup(w);
                Location loc = null;
                if (g == null) {
                    sender.sendMessage(ChatColor.RED + "This world is not in any group on group.yml, maybe a typo?");
                    return true;
                }
                for (String wn : warp.getConfigurationSection(g).getKeys(false)) {
                    if (wn.equals(args[0])) {
                        loc = (Location) warp.get(g + "." + args[0] + ".location");
                        break;
                    }
                }
                if (loc == null) {
                    sender.sendMessage(ChatColor.RED + "That warp didn't exist");
                    return true;
                } else {
                    player.teleport(loc);
                    return true;
                }
            }

        } else {
            return false;
        }
        return true;
    }
}
