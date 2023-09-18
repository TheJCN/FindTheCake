package jcn.findthecake.Main;

import jcn.findthecake.Commands.StartCommand;
import jcn.findthecake.Commands.TopCommand;
import jcn.findthecake.DataBase.MySQLManager;
import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.Listener.FTHListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public final class FindTheCake extends JavaPlugin {
    private Logger logger = Bukkit.getLogger();
    private GameManager gameManager;
    private MySQLManager mySQLManager;
    private Connection connection;

    @Override
    public void onEnable() {

        logger.info("Plugin made The_JCN");

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
        }

        if (!setupDatabase()) {
            logger.info("Не удалось подключиться к базе данных. Плагин будет отключен.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.gameManager = new GameManager(this);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Stats (id INTEGER PRIMARY KEY AUTO_INCREMENT, playername VARCHAR(255), points BIGINT)");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getCommand("ftc").setExecutor(new StartCommand(gameManager, this));
        getCommand("top").setExecutor(new TopCommand(gameManager, this, connection));

        Bukkit.getPluginManager().registerEvents(new FTHListener(gameManager, this, connection), this);

    }

    @Override
    public void onDisable() {
        logger.info("Thx for using this plugin");
    }

    private boolean setupDatabase() {
        String host = getConfig().getString("mysql.host");
        int port = getConfig().getInt("mysql.port");
        String database = getConfig().getString("mysql.database");
        String username = getConfig().getString("mysql.username");
        String password = getConfig().getString("mysql.password");
        mySQLManager = new MySQLManager(host, port, database, username, password);
        if (!mySQLManager.connect()) {
            return false;
        }
        connection = mySQLManager.getConnection();
        return true;
    }
}
