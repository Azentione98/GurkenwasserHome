package wurstwasser.gurkenwasserhome.startup;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import wurstwasser.gurkenwasserhome.commands.cmdDelhome;
import wurstwasser.gurkenwasserhome.commands.cmdHome;
import wurstwasser.gurkenwasserhome.commands.cmdHomes;
import wurstwasser.gurkenwasserhome.commands.cmdSethome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wurstwasser.gurkenwasserhome.messages.messages;

import java.io.File;
import java.io.IOException;

public final class GurkenwasserHome extends JavaPlugin {

    private File configFile;
    private File homesFile;
    private FileConfiguration config;
    private FileConfiguration homesConfig;

    @Override
    public void onEnable() {
        // Laden der Konfigurationsdateien
        reloadConfigurations();

        // Load messages
        messages.loadMessages(getConfig());

        // Weitere Initialisierungen
        setupCommands();
    }

    @Override
    public void onDisable() {
        // Speichern der Konfigurationsdateien
        saveConfigurations();
    }

    private void reloadConfigurations() {
        createConfig();
        createHomesConfig();

        // Konfigurationsdateien laden
        reloadConfig();
        reloadHomesConfig();
    }

    private void saveConfigurations() {
        // Konfigurationsdateien speichern
        saveConfig();
        saveHomesConfig();
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == this) {
            saveConfig();
        }
    }

    private void setupCommands() {
        if (getCommand("home") != null) {
            getCommand("home").setExecutor(new cmdHome());
        }
        if (getCommand("sethome") != null) {
            getCommand("sethome").setExecutor(new cmdSethome());
        }
        if (getCommand("homes") != null) {
            getCommand("homes").setExecutor(new cmdHomes());
        }
        if (getCommand("delhome") != null) {
            getCommand("delhome").setExecutor(new cmdDelhome());
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public FileConfiguration getHomesConfig() {
        if (homesConfig == null) {
            reloadHomesConfig();
        }
        return homesConfig;
    }

    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            config.save(configFile);
        } catch (IOException ex) {
            getLogger().severe("Could not save config to " + configFile);
        }
    }

    public void saveHomesConfig() {
        if (homesConfig == null || homesFile == null) {
            return;
        }
        try {
            homesConfig.save(homesFile);
        } catch (IOException ex) {
            getLogger().severe("Could not save homes config to " + homesFile);
        }
    }

    public void reloadHomesConfig() {
        if (homesFile == null) {
            homesFile = new File(getDataFolder(), "homes.yml");
        }
        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
    }

    private void createConfig() {
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void createHomesConfig() {
        homesFile = new File(getDataFolder(), "homes.yml");
        if (!homesFile.exists()) {
            homesFile.getParentFile().mkdirs();
            saveResource("homes.yml", false);
        }
        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
    }
    public int getMaxHomes(Player player) {
        FileConfiguration config = getConfig();
        int maxHomes = config.getInt("permissions.default");

        if (player.hasPermission("gurkenwasserhome.admin")) {
            maxHomes = config.getInt("permissions.admin");
        } else if (player.hasPermission("gurkenwasserhome.vip")) {
            maxHomes = config.getInt("permissions.vip");
        }

        return maxHomes;
    }
}
