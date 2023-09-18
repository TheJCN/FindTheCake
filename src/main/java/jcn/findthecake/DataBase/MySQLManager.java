package jcn.findthecake.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLManager {
    private Connection connection;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    public MySQLManager(String host, int port, String database, String username, String password){
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }
    public boolean connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection(){
        return  connection;
    }
}
