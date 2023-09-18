package jcn.findthecake.Listener;

import jcn.findthecake.DataBase.MySQLManager;
import jcn.findthecake.DataBase.MySQLRequests;
import jcn.findthecake.Main.FindTheCake;
import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.Main.Logic;
import org.bukkit.Bukkit;
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

    public FTHListener(GameManager gameManager, FindTheCake plugin, Connection connection){
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.connection = connection;
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) throws SQLException {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && clickedBlock.getType() == Material.CAKE) {
            if (clickedBlock.hasMetadata("Event Block")) {
                player.sendMessage("Поздравляю, вы нашли торт! Вы получили + 1 очко!");
                Bukkit.broadcastMessage("Игрок: " + player.getName() + " нашел торт!");

                clickedBlock.setType(Material.AIR);

                MySQLRequests mySQLRequests = new MySQLRequests(connection);
                if(mySQLRequests.playerExist(player.getName())){
                    mySQLRequests.playerAddPoint(player.getName());
                }
                else {
                    mySQLRequests.addPlayer(player.getName());
                    mySQLRequests.playerAddPoint(player.getName());
                }

                Logic logic = Logic.getInstance(gameManager, plugin);
                logic.Start();
            }
        }
    }
}
