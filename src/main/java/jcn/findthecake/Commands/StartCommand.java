package jcn.findthecake.Commands;

import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.Main.Logic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class StartCommand implements CommandExecutor {
    private GameManager gameManager;
    private Plugin plugin;

    public StartCommand(GameManager gameManager, Plugin plugin){
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if(player.hasPermission("ftc.adm")){
            player.sendMessage("Ивент запущен!");
            Logic logic = Logic.getInstance(gameManager, plugin);
            logic.Start();
        }
        return true;
    }
}
