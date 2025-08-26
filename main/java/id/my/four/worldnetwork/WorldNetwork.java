package id.my.four.worldnetwork;

import id.my.four.worldnetwork.command.SetSpawnCommand;
import id.my.four.worldnetwork.handler.FileHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class WorldNetwork extends JavaPlugin {

    public static WorldNetwork instance;
    public static WorldNetwork getInstance() {
        return instance;
    }
    FileHandler FileHandler = new FileHandler();

    @Override
    public void onEnable() {

        instance = this;

        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "|-------------------|");
        Bukkit.getConsoleSender().sendMessage( ChatColor.GRAY + "|                   |");
        Bukkit.getConsoleSender().sendMessage( ChatColor.GRAY + "|   " + ChatColor.GREEN + "WORLD NETWORK" + ChatColor.GRAY + "   |");
        Bukkit.getConsoleSender().sendMessage( ChatColor.GRAY + "| " + ChatColor.GREEN + "Made by: " + ChatColor.RED + "antopHD" + ChatColor.GRAY + "  |");
        Bukkit.getConsoleSender().sendMessage( ChatColor.GRAY + "|-------------------|");
        Bukkit.getConsoleSender().sendMessage( ChatColor.GREEN + "Version: " + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage( "");

        if (Bukkit.getPluginManager().isPluginEnabled("Luckperms")) {
            getLogger().log(Level.INFO, "Luckperms detected continue activation");
        } else {
            getPluginLoader().disablePlugin(this);
            getLogger().log(Level.INFO, "Luckperms not detected");
            getLogger().log(Level.INFO, "Disableing" + getDescription().getName());
        }

        this.getCommand("setspawn").setExecutor(new SetSpawnCommand());

        FileHandler.createYml(this);
        FileHandler.reloadYml();

    }

    public void reload() {
        getPluginLoader().disablePlugin(this);
        getPluginLoader().enablePlugin(this);
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Saving yml file");
        FileHandler.reloadYml();
        FileHandler.saveYml();
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("worldnetwork"))
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "Usage: /worldnetwork <command>");
        } else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(ChatColor.GREEN + "Realoading world network...");
            reload();
            sender.sendMessage(ChatColor.GREEN + "world network reloaded!");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(ChatColor.GREEN + "|--World Network--|");
            sender.sendMessage(ChatColor.GREEN + " version: " + getDescription().getVersion());
            sender.sendMessage(ChatColor.GREEN + " author: " + getDescription().getAuthors());
        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            if (sender.hasPermission("worldnetwork.command")) {
                sender.sendMessage(ChatColor.GREEN + "|---------- WorldNetwork ----------|");
                sender.sendMessage(ChatColor.GRAY + "/wn reload   - reload plugins");
                sender.sendMessage(ChatColor.GRAY + "/wn version  - WorldNetwork Version");
                sender.sendMessage(ChatColor.GRAY + "/wn help     - open help");
                sender.sendMessage(ChatColor.GRAY + "/wn spawn    - get spawn information");
                sender.sendMessage(ChatColor.GRAY + "/wn group    - add or remove group");
            } else {
                sender.sendMessage(ChatColor.GREEN + "|---------- WorldNetwork ----------|");
                sender.sendMessage(ChatColor.GRAY + "/wn help     - open help");
                sender.sendMessage(ChatColor.GRAY + "/wn version  - WorldNetwork Version");
            }
        }
        return true;

    }

}