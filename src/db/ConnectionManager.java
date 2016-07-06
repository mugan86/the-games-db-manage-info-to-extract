package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.XMLtoJSON.PRETTY_PRINT_INDENT_FACTOR;
import model.Game;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author anartzmugika
 */
public class ConnectionManager {
    
    private Connection konexioa;
    public ConnectionManager()
    {
        setConnection();
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
    
    public void insert(String sql)
    {
        setConnection();
        try (Statement sententzia = (Statement) konexioa.createStatement()) {
            System.out.println(sql);
            
            sententzia.executeUpdate(sql);
            
            konexioa.close();
            sententzia.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }  
    
    public ArrayList<Game> getGamesList(String sql)
    {
        setConnection();
        try (Statement sententzia = (Statement) konexioa.createStatement()) {
            System.out.println(sql);
            ArrayList<Game> game = new ArrayList<>();
            
            ResultSet erantzuna=sententzia.executeQuery(sql);
            while (erantzuna.next())
            {
                Game g = new Game();
                g.setPlatformId(erantzuna.getInt(1));
                g.setId(erantzuna.getInt(2));
                g.setGameTitle(erantzuna.getString(3));
                g.setReleaseDate(erantzuna.getString(4));
                
                
                try {
                    JSONObject xmlJSONObj = XML.toJSONObject(Request.getHttpGETAPI("http://thegamesdb.net/api/GetGame.php?id=" + g.getId()));
                    String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
                    System.out.println(jsonPrettyPrintString);
            
                    JSONObject data = (JSONObject) xmlJSONObj.getJSONObject("Data");
                    JSONObject games = (JSONObject) data.getJSONObject("Game");
                    try
                    {
                        g.setPlayers((Integer) games.getInt("Players"));
                    }
                    catch(Exception e)
                    {
                        g.setPlayers(-1);
                        System.out.println("No Players");
                    }
                    
                    try
                    {
                        JSONObject genre = (JSONObject) games.getJSONObject("Genres");
                        System.out.println(genre);
                        boolean isJsonArray = genre.toString().contains("[");
                        if(isJsonArray){
                           // Its a json array
                           System.out.println("*****");
                           JSONArray array_genres = (JSONArray) genre.getJSONArray("genre");
                           String genre1 = (String) array_genres.get(0);
                           String genre2 = (String) array_genres.get(1);
                           System.out.println("Genre 1 / " + genre1 + " genre2 " + genre2);
                           g.setGenre1(genre1);
                           g.setGenre2(genre2);
                        }
                        else{
                          String genre_str = (String)genre.getString("genre");
                          System.out.println(genre_str);
                          g.setGenre1(genre_str);
                        }  
                    }
                    catch(Exception e)
                    {
                        System.out.println("Ez dago generorik");
                    }
                    
                    try
                    {
                        JSONObject similar = (JSONObject) games.getJSONObject("Similar");
                        
                        
                        JSONArray GameSimilar = (JSONArray)similar.getJSONArray("Game");
                        
                        int similar1 = (Integer) ((JSONObject) GameSimilar.get(0)).getInt("id");
                        System.out.println("SIMILAR1: " + similar1);
                        g.setSimilar1(similar1);
                        
                        int similar2 = (Integer) ((JSONObject) GameSimilar.get(1)).getInt("id");
                        g.setSimilar2(similar2);
                        System.out.println("SIMILAR2: " + similar2);
                    }
                    catch(Exception e)
                    {
                        System.out.println("Ez antzekorik");
                    }
                    //System.out.println(games);
                   
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("**********************\n" + g.toString()+ " ---- Players " + g.getPlayers() + "\n *********************************");
                game.add(g);
                
                insert("UPDATE games SET `revised` = '1', " 
                        + "players=" + g.getPlayers() + ", "
                        + "genre1='" + g.getGenre1() + "', "
                        + "genre2='" + g.getGenre2() + "', "
                        + "similar1=" + g.getSimilar1() + ", "
                        + "similar2=" + g.getSimilar2() + " WHERE id = " + g.getId());
                
            }
            konexioa.close();
            sententzia.close();
            return game;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }      
        return null;
    }  
}
