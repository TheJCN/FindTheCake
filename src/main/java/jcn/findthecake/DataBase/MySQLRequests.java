package jcn.findthecake.DataBase;

import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLRequests {
    private String PREFIX;
    private Connection connection;
    public MySQLRequests(Connection connection, String PREFIX){
        this.connection = connection;
        this.PREFIX = PREFIX;
    }

    public boolean playerExist(String playerName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Stats Where playerName = ?");
        preparedStatement.setString(1, playerName);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public void playerAddPoint(String playrName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Stats SET points = points + 1 WHERE playername = ?");
        preparedStatement.setString(1, playrName);
        preparedStatement.executeUpdate();
    }

    public List<String> getTopPlayersByPoint(int limit) {
        List<String> topPlayers = new ArrayList<>();

        String query = "SELECT playername, points FROM Stats ORDER BY points DESC LIMIT ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, limit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String playerName = resultSet.getString("playername");
                    int points = resultSet.getInt("points");
                    String playerStats =  ChatColor.GREEN + "Ник: " + ChatColor.GOLD + playerName + ChatColor.GREEN  + " Очков: " + ChatColor.GOLD +  points;
                    topPlayers.add(playerStats);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPlayers;
    }

    public void addPlayer(String playerName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Stats (playername, points) VALUES (?, 0)");
        preparedStatement.setString(1, playerName);
        preparedStatement.executeUpdate();
    }
}
