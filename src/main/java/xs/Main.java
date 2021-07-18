package xs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xs.weapon.FreezeSword;

public class Main extends JavaPlugin {

    final static ConsoleCommandSender console = Bukkit.getConsoleSender();
    final static String PluginPrefix = "[" + ChatColor.AQUA + "NoobTool" + ChatColor.RESET + "]";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new FreezeSword(), this);
        console.sendMessage(PluginPrefix + " Plugin Enable!");
    }

    @Override
    public void onDisable() {
        console.sendMessage(PluginPrefix + " Disable Success!");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (sender.hasPermission("noobtool.admin.give.freezesword")) {
                    player.getInventory().addItem(new FreezeSword().create(0, 0, 0, 0, 0, 0, 0, 0));
                } else {
                    player.sendMessage(ChatColor.RED + "You have no permission!");
                }
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You must be a PLAYER!");
            }
        }
        return true;
    }

}
