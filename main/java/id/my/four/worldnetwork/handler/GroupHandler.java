package id.my.four.worldnetwork.handler;

import org.bukkit.configuration.file.*;

public final class GroupHandler {

    static YamlConfiguration gr = FileHandler.get("group");

    public static String GetGroup(String world) {
        for (String g : gr.getConfigurationSection("group").getKeys(false)) {
            for (String gp : gr.getStringList("group." + g)) {
                if (gp.equals(world)) {
                    return g;
                }
            }
        }
        return null;
    }

    public static String GroupValidator(String group) {
        for (String g : gr.getConfigurationSection("group").getKeys(false)) {
            if (g.equals(group)) {
                return g;
            }
        }
        return null;
    }
}

