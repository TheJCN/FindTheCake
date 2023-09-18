package jcn.findthecake.Listener;

import jcn.findthecake.DataBase.MySQLManager;
import jcn.findthecake.DataBase.MySQLRequests;
import jcn.findthecake.GameManager.GameState;
import jcn.findthecake.Main.FindTheCake;
import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.Main.Logic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.SQLException;

public class FTHListener implements Listener{
    private Plugin plugin;
    private GameManager gameManager;
    private Connection connection;
    private String PREFIX;

    public FTHListener(GameManager gameManager, FindTheCake plugin, Connection connection, String PREFIX){
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.connection = connection;
        this.PREFIX = PREFIX;
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) throws SQLException {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && clickedBlock.getType() == Material.CAKE) {
            if (clickedBlock.hasMetadata("Event Block")) {
                if(gameManager.getGameState() == GameState.Active) {
                    player.sendMessage(PREFIX + ChatColor.AQUA + "Поздравляю, вы нашли торт! Вы получили " + ChatColor.GREEN + "1 очко!");
                    Bukkit.broadcastMessage(PREFIX + ChatColor.GREEN + "Игрок " + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " нашел торт!");

                    clickedBlock.setType(Material.AIR);

                    MySQLRequests mySQLRequests = new MySQLRequests(connection, PREFIX);
                    if (mySQLRequests.playerExist(player.getName())) {
                        mySQLRequests.playerAddPoint(player.getName());
                    } else {
                        mySQLRequests.addPlayer(player.getName());
                        mySQLRequests.playerAddPoint(player.getName());
                    }

                    Logic logic = Logic.getInstance(gameManager, plugin, PREFIX);

                    logic.cancelTimer();

                    logic.Start();
                }
            }
        }
    }
}
