package wurstwasser.gurkenwasserhome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wurstwasser.gurkenwasserhome.messages.messages;
import wurstwasser.gurkenwasserhome.startup.GurkenwasserHome;

public class cmdHomes implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;
        FileConfiguration homesConfig = GurkenwasserHome.getPlugin(GurkenwasserHome.class).getHomesConfig();
        String path = "homes." + player.getUniqueId().toString();

        if (homesConfig.contains(path)) {
            player.sendMessage(messages.getPrefix() + "Deine Homes:");
            for (String homeName : homesConfig.getConfigurationSection(path).getKeys(false)) {
                player.sendMessage("- " + homeName);
            }
        } else {
            player.sendMessage(messages.getPrefix() + "Du hast keine Homes gesetzt!");
        }

        return true;
    }
}