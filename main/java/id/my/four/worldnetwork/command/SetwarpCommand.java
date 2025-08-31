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

public final class SetwarpCommand implements CommandExecutor {
    File wfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "warp.yml");
    YamlConfiguration warp = YamlConfiguration.loadConfiguration(wfile);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Location loc;
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only player can execute this command");
            return false;
        } else {
            if (!(args.length == 1)) {
                return false;
            } else {
                Player p = (Player) sender;
                loc = p.getLocation();
                String w = p.getWorld().getName();
                String g = GroupHandler.GetGroup(w);

                if (!(g == null)) {
                    try {
                        warp.set(g + "." + args[0] + ".location", loc);
                        warp.save(wfile);
                        p.sendMessage(ChatColor.GREEN + "Warp " + args[0] + " set for group " + g);
                        return true;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "This world is not in any group on group.yml, maybe a typo?");
                    return true;
                }
            }
        }
    }
}
