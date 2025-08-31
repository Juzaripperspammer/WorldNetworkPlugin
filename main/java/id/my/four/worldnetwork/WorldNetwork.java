package id.my.four.worldnetwork;

import id.my.four.worldnetwork.command.*;
import id.my.four.worldnetwork.handler.FileHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public final class WorldNetwork extends JavaPlugin {

    public static WorldNetwork instance;
    public static WorldNetwork getInstance() {
        return instance;
    }
    FileHandler FileHandler = new FileHandler();

    @Override
    public void onEnable() {

        BukkitScheduler scheduler = Bukkit.getScheduler();

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

        FileHandler.createYml(this);
        FileHandler.reloadYml();
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "You can ignore the error above if the plugin work perfectly");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "------------------------");

        new BukkitRunnable() {
            @Override
            public void run() {
                CommandRegisterer();
            }
        }.runTaskTimer(this, 0, 10);
    }

    public void reload() {
        FileHandler.createYml(this);
        FileHandler.reloadYml();
        FileHandler.saveYml();
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Saving yml file");
        FileHandler.reloadYml();
        FileHandler.saveYml();
        // Plugin shutdown logic
    }

    public void CommandRegisterer() {
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand());
        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("server").setExecutor(new ServerCommand());
        this.getCommand("maintenance").setExecutor(new MaintenanceCommand());
        this.getCommand("sethub").setExecutor(new SethubCommand());
        this.getCommand("hub").setExecutor(new HubCommand());
        this.getCommand("setwarp").setExecutor(new SetwarpCommand());
        this.getCommand("warp").setExecutor(new WarpCommand());
        this.getCommand("delwarp").setExecutor(new DelwarpCommand());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("worldnetwork")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "Unknown command use /worldnetwork help");
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(ChatColor.GREEN + "Reloading world network...");
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
        }
        return true;

    }

}