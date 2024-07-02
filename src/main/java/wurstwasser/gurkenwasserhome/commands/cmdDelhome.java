package wurstwasser.gurkenwasserhome.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wurstwasser.gurkenwasserhome.messages.messages;
import wurstwasser.gurkenwasserhome.startup.GurkenwasserHome;

public class cmdDelhome implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;
        if (args.length == 0) {
            player.sendMessage(messages.getPrefix() + "Bitte gib den Namen des zu löschenden Homes an!");
            return true;
        }

        String homeName = args[0];
        FileConfiguration homesConfig = GurkenwasserHome.getPlugin(GurkenwasserHome.class).getHomesConfig();
        String path = "homes." + player.getUniqueId().toString() + "." + homeName;

        if (homesConfig.contains(path)) {
            homesConfig.set(path, null);
            GurkenwasserHome.getPlugin(GurkenwasserHome.class).saveHomesConfig();
            player.sendMessage(messages.getPrefix() + "Home " + homeName + " gelöscht!");
        } else {
            player.sendMessage(messages.getPrefix() + "Home mit diesem Namen existiert nicht!");
        }

        return true;
    }
}
