package id.my.four.worldnetwork.handler;

import id.my.four.worldnetwork.WorldNetwork;
import id.my.four.worldnetwork.command.SetSpawnCommand;
import org.bukkit.Bukkit;
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

    private final WorldNetwork plugin;
    public EventRegisterer(WorldNetwork plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @org.bukkit.event.EventHandler
    public Void onJoin(PlayerJoinEvent event) {
        String none = "none";
        Boolean t = true;
        Player player = event.getPlayer();
        UUID puid = player.getUniqueId();
        File pfile = new File(Bukkit.getServer().getPluginManager().getPlugin("WorldNetwork").getDataFolder(), "UserData/" + puid + ".yml");
        YamlConfiguration playerile = YamlConfiguration.loadConfiguration(pfile);

        if (!(pfile.exists())) {
            try {
                playerile.set();
            }
        }
    }
}
