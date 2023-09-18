package jcn.findthecake.Commands;

import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.GameManager.GameState;
import jcn.findthecake.Main.Logic;
import jcn.findthecake.Utilities.CakeRemover;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MainCommand implements CommandExecutor {
    private GameManager gameManager;
    private Plugin plugin;
    private String PREFIX;

    public MainCommand(GameManager gameManager, Plugin plugin, String PREFIX){
        this.gameManager = gameManager;
        this.plugin = plugin;
        this.PREFIX = PREFIX;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (!(player.hasPermission("ftc.adm"))) {
            return false;
        }
        if (strings[0].equalsIgnoreCase("start")) {
            gameManager.setGameState(GameState.Active);
            player.sendMessage(PREFIX + ChatColor.LIGHT_PURPLE + "Ивент запущен!");
            Logic logic = Logic.getInstance(gameManager, plugin, PREFIX);
            logic.Start();
            return true;
        }
        if (strings[0].equalsIgnoreCase("stop")) {
            player.sendMessage(PREFIX + ChatColor.LIGHT_PURPLE + "Ивент остановлен!");
            gameManager.setGameState(GameState.Nothing);
            CakeRemover cakeRemover = new CakeRemover(plugin);
            cakeRemover.removeCakeInRadius(100, player.getWorld());
        }
        return false;
    }
}
