package id.my.four.worldnetwork.handler;

import id.my.four.worldnetwork.WorldNetwork;
import id.my.four.worldnetwork.command.SetSpawnCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


public class EventHandler implements Listener {

    @EventHandler
    public void Handler() {

        BukkitTask task = new task(SetSpawnCommand.class.getClass("loc")).runTaskLater(this.plugin, 20);
    }
}
