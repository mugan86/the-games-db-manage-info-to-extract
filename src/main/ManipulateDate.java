/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import db.ConnectionManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import model.Game;

/**
 *
 * @author anartzmugika
 */
public class ManipulateDate {
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection konexioa= DriverManager.getConnection("jdbc:mysql://localhost:8889/gamesdb?useUnicode=yes&characterEncoding=UTF-8", 
                                            "root", "root");
        
	try (Statement sententzia = (Statement) konexioa.createStatement()) {
            System.out.println("SELECT * FROM games LIMIT 0,10");
            ArrayList<Game> game = new ArrayList<>();
            
            ResultSet erantzuna=sententzia.executeQuery("SELECT * FROM games");
            while (erantzuna.next())
            {
                Game g = new Game();
                g.setId(erantzuna.getInt(2));
                String dateInString = erantzuna.getString(4);
                System.out.println(dateInString);
                String month = dateInString.substring(0,2);
                String day = dateInString.substring(3,5);
                String year = dateInString.substring(6);
                System.out.println(year+"-"+month+"-"+day);
                g.setReleaseDate(year+"-"+month+"-"+day);
                game.add(g);
                
                Statement stat2 = (Statement) konexioa.createStatement();
                stat2.executeUpdate("UPDATE games SET release_date='" + g.getReleaseDate()+"' WHERE id=" + g.getId());
                stat2.close();
                //sententzia.executeUpdate("UPDATE games SET release_date='" + g.getReleaseDate()+"' WHERE id=" + g.getId());
                
            }
            
            konexioa.close();
            sententzia.close();
        
        }
    }
}
