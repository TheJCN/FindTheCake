package jcn.findthecake.Commands;

import jcn.findthecake.DataBase.MySQLRequests;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetTop implements CommandExecutor {
    private Connection connection;
    private String PREFIX;
    public ResetTop(Connection connection, String PREFIX){
        this.connection = connection;
        this.PREFIX = PREFIX;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (player.hasPermission("ftc.admin")) {
            MySQLRequests mySQLRequests = new MySQLRequests(connection, PREFIX);
            try {
                mySQLRequests.resetDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            player.sendMessage(PREFIX + ChatColor.GOLD + "Топ был успешно сброшен");
        }
        return false;
    }

}
