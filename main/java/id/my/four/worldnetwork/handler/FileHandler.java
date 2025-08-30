package id.my.four.worldnetwork.handler;

import id.my.four.worldnetwork.WorldNetwork;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

public final class FileHandler {


    FileConfiguration config;
    File cFile;

    FileConfiguration group;
    File gFile;

    FileConfiguration kits;
    File kFile;

    FileConfiguration spawn;
    File sFile;

    FileConfiguration warp;
    File wFile;

    public void createYml(WorldNetwork plugin) {
        cFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(),"config.yml");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        if (!cFile.exists()) {
            try {
                Bukkit.getLogger().log(Level.INFO, "Creating config.yml");
                InputStream configLink = getClass().getResourceAsStream("/config.yml");
                Files.copy(configLink, cFile.toPath());
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to create the config file", e);
            }
        }

        gFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "group.yml");

        if (!gFile.exists()) {
            try {
                Bukkit.getLogger().log(Level.INFO, "Creating group.yml");
                InputStream groupLink = getClass().getResourceAsStream("/group.yml");
                Files.copy(groupLink, gFile.toPath());
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to create the group file", e);
            }
        }


        kFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "kits.yml");
        if (!kFile.exists()) {
            try {
                Bukkit.getLogger().log(Level.INFO, "Creating kits.yml");
                InputStream kitLink = getClass().getResourceAsStream("/kits.yml");
                Files.copy(kitLink, kFile.toPath());
            } catch (IOException e){
                Bukkit.getLogger().log(Level.SEVERE, "Failed to create the kit file", e);
            }
        }

        sFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(),"spawn.yml");
        if (!sFile.exists()) {
            try {
                Bukkit.getLogger().log(Level.INFO, "Creating spawn.yml");
                InputStream sLink = getClass().getResourceAsStream("/spawn.yml");
                Files.copy(sLink, sFile.toPath());
            } catch (IOException e){
                Bukkit.getLogger().log(Level.SEVERE, "Failed to create the spawn file", e);
            }
        }

        wFile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "warp.yml");
        if (!wFile.exists()) {
            try {
                Bukkit.getLogger().log(Level.INFO, "Creating warp.yml");
                InputStream wLink = getClass().getResourceAsStream("/warp.yml");
                Files.copy(wLink, wFile.toPath());
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to create the warp file", e);
            }
        }

        config = YamlConfiguration.loadConfiguration(cFile);
        group = YamlConfiguration.loadConfiguration(gFile);
        kits = YamlConfiguration.loadConfiguration(kFile);
        spawn = YamlConfiguration.loadConfiguration(sFile);
        warp = YamlConfiguration.loadConfiguration(wFile);


    }

    public void saveYml() {
        try {
            config.save(cFile);
            group.save(gFile);
            kits.save(kFile);
            spawn.save(sFile);
            warp.save(wFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to save the world network yml file", e);
        }
    }

    public void reloadYml() {
        config = YamlConfiguration.loadConfiguration(cFile);
        group = YamlConfiguration.loadConfiguration(gFile);
        kits = YamlConfiguration.loadConfiguration(kFile);
        spawn = YamlConfiguration.loadConfiguration(sFile);
        warp = YamlConfiguration.loadConfiguration(wFile);
    }

    public static YamlConfiguration get(String file) {
        File f = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), file + ".yml");
        YamlConfiguration fl = YamlConfiguration.loadConfiguration(f);
        return fl;
    }

}