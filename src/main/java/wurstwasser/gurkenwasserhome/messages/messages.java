package wurstwasser.gurkenwasserhome.messages;

import org.bukkit.configuration.file.FileConfiguration;

public class messages {

    public static String PREFIX;

    public static void loadMessages(FileConfiguration config) {
        PREFIX = config.getString("prefix", "§8[§2GurkenwasserHomes§8] §f");
    }

    public static String getPrefix() {
        return PREFIX;
    }
}