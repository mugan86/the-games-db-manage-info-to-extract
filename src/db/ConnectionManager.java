package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anartzmugika
 */
public class ConnectionManager {
    
    private Connection konexioa;
    public ConnectionManager()
    {
        
    }
    public void setConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            konexioa= DriverManager.getConnection("jdbc:mysql://localhost/thegamesdb", 
                        "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Connection getConnection()
    {
        return konexioa;
    }
}
