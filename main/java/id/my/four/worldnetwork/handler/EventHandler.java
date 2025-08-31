package id.my.four.worldnetwork.handler;

import id.my.four.worldnetwork.WorldNetwork;
import id.my.four.worldnetwork.command.SetSpawnCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.UUID;


public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public Void onJoin(PlayerJoinEvent event) {
        String none = "none";
        Boolean t = true;
        Boolean f = false;
        Player player = event.getPlayer();
        UUID puid = player.getUniqueId();
        String p = player.getName();
        String ip = player.getAddress().getAddress().toString();
        File pfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "UserData/" + puid + ".yml");
        YamlConfiguration playerfile = YamlConfiguration.loadConfiguration(pfile);
        File gfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "group.yml");
        YamlConfiguration group = YamlConfiguration.loadConfiguration(gfile);

        if (!(pfile.exists())) {
            try {
                playerfile.set("Player-name", p);
                playerfile.set("ip", ip);
                playerfile.set("tpa-from", none);
                playerfile.set("toggle.tpa", t);
                playerfile.set("toggle.tpahere", t);
                playerfile.set("toggle.msg", t);
                for (String s : group.getConfigurationSection("group").getKeys(false)) {
                    playerfile.set("player-nickname." + s, p);
                    playerfile.set("fly." + s, f);
                    playerfile.set("god." + s, none);
                }
                playerfile.save(pfile);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Failed to create player file " + e);
            }
        }
        return null;
    }
}
