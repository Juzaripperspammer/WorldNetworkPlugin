package id.my.four.worldnetwork.command;

import id.my.four.worldnetwork.handler.FileHandler;
import com.sun.tools.javac.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

public final class SetSpawnCommand implements CommandExecutor {

    private Main main;

    public SetSpawnCommand(Main main) {
        this.main = main;
    }

    FileHandler Filehandler = new FileHandler();
    File sFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "spawn.yml");
    YamlConfiguration spawn = YamlConfiguration.loadConfiguration(sFile);

    public SetSpawnCommand() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        if (sender.hasPermission("worldnetwrok.setspawn")){
            Player player = (Player) sender;
            World world = player.getWorld();
            Location loc = player.getLocation();
            StringBuilder builder = new StringBuilder();

            try {
                spawn.set("default." + world + ".location", loc);
                spawn.save(sFile);
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to save spawn location", e);;
            }

            sender.sendMessage("world: " + world);
            sender.sendMessage("x: " + loc.getX());
            sender.sendMessage("y: " + loc.getY());
            sender.sendMessage("z: " + loc.getZ());
            sender.sendMessage("location: " + loc);

        }
        return true;
    }

}