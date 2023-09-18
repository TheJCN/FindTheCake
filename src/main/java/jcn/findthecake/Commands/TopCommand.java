package jcn.findthecake.Commands;

import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.DataBase.MySQLRequests;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;

public class TopCommand implements CommandExecutor {
    private GameManager gameManager;
    private Plugin plugin;
    private Connection connection;

    public TopCommand(GameManager gameManager, Plugin plugin, Connection connection){
        this.gameManager = gameManager;
        this.plugin = plugin;
        this.connection = connection;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (player.hasPermission("ftc.player")) {
            MySQLRequests mySQLRequests = new MySQLRequests(connection);
            player.sendMessage("Топ игроков!");
            for(String str : mySQLRequests.getTopPlayersByPoint(5)){
                player.sendMessage(str);
            }

        }
        return true;
    }
}
