package wurstwasser.gurkenwasserhome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wurstwasser.gurkenwasserhome.messages.messages;
import wurstwasser.gurkenwasserhome.startup.GurkenwasserHome;
import org.bukkit.Location;

public class cmdHome implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;
        if (args.length == 0) {
            player.sendMessage(messages.getPrefix() + "Bitte gib den Namen des Homes an!");
            return true;
        }

        String homeName = args[0];
        FileConfiguration homesConfig = GurkenwasserHome.getPlugin(GurkenwasserHome.class).getHomesConfig();
        String path = "homes." + player.getUniqueId().toString() + "." + homeName;

        if (homesConfig.contains(path)) {
            player.teleport(homesConfig.getLocation(path));
            player.sendMessage(messages.getPrefix() + "Teleportiert zu " + homeName);
        } else {
            player.sendMessage(messages.getPrefix() + "Home mit diesem Namen existiert nicht!");
        }

        return true;
    }
}