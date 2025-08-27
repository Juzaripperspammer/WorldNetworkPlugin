package id.my.four.worldnetwork.command;

import id.my.four.worldnetwork.WorldNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;

public final class SpawnCommand implements CommandExecutor{


    public SpawnCommand() {

    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage("Only player can use /spawn use /spawn <player> instead");
            }
        } else if (sender instanceof Player) {
            if (args.length == 0) {
                try {
                    Player player = (Player) sender;
                    World world = player.getWorld();
                    File sFile = new File(WorldNetwork.getInstance().getDataFolder(), "spawn.yml");
                    YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);
                    Location loc = (Location) spawn.get("default." + world + ".location");

                    if (!(loc == null)) {
                        player.teleport(loc);
                        spawn.save(sFile);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Spawn didn't set");
                    }

                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "Failed to teleport to spawn" + e);
                }
            }
        }
        return true;
    }
}

