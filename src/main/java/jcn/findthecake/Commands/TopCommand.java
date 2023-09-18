package jcn.findthecake.Commands;

import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.DataBase.MySQLRequests;
import org.bukkit.ChatColor;
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
    private String  PREFIX;

    public TopCommand(GameManager gameManager, Plugin plugin, Connection connection, String PREFIX){
        this.gameManager = gameManager;
        this.plugin = plugin;
        this.connection = connection;
        this.PREFIX = PREFIX;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (player.hasPermission("ftc.player")) {
            MySQLRequests mySQLRequests = new MySQLRequests(connection, PREFIX);
            player.sendMessage(PREFIX + ChatColor.LIGHT_PURPLE + "Топ игроков!");
            player.sendMessage("\n");
            for(String str : mySQLRequests.getTopPlayersByPoint(5)){
                player.sendMessage(str);
            }
            player.sendMessage("\n");

        }
        return true;
    }
}
