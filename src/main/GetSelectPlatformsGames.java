/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.ConnectionManager;
import db.Request;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Game;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author anartzmugika
 */
public class GetSelectPlatformsGames {
    public static void main (String [] args)
    {
        File folder = new File("files");
        File [] listOfFiles = folder.listFiles();
        System.out.println("listOfFiles: " + listOfFiles.length);
        ArrayList<String> files_to_read = new ArrayList<>();
        int count = 0;
        for (File listOfFile : listOfFiles) {
            
            if (listOfFile.isFile() && !listOfFile.getName().equals(".DS_Store")) {

                //if (type == 1 && listOfFile.getName().contains(String.valueOf(year)))
                files_to_read.add(listOfFile.getName());
                count++;
            } 
        }
        
        int file_number = 0;
        
        //more_info_tv/
        for (int i = 0; i < files_to_read.size(); i++)
        {
            System.out.println("\n*************************************************************\nFiles: " 
                                    + files_to_read.size() + " / Progress: "+ ((i*100)/files_to_read.size()) +"%\n*************************************************************");
            String filename = files_to_read.get(i);
                //Leemos el fichero y lo mostramos por pantalla
                try {
                    FileReader fr = new FileReader("files/" + filename);
                    //Leemos el fichero y lo mostramos por pantalla
                    int valor=fr.read();
                    String text = "";
                    while(valor!=-1){
                        //System.out.print((char)valor);
                        valor=fr.read();
                        text = text + (char)valor;

                    }   
                    
                    System.out.println("Open platform filename: " + filename);


                    JSONObject object = (JSONObject) new JSONObject(text.substring(0,text.length()-1));
                    
                    JSONObject data = (JSONObject) object.getJSONObject("Data");
                    
                    JSONArray games = (JSONArray) data.getJSONArray("Game");
                    
                    ArrayList<Game> game_objects = (ArrayList<Game>) fromJson(games.toString(),
                                    new TypeToken<ArrayList<Game>>() {}.getType());
                    
                    for (int j = 0; j < game_objects.size(); j ++)
                    {
                        try
                        {
                            /*String values = 
                            System.out.println("INSERT IGNORE INTO `platforms` (`id`, `slug`, `name`) VALUES (" + values );*/

                            System.out.println("http://thegamesdb.net/api/GetGame.php?id=" + game_objects.get(j).getId());

                            JSONObject xmlJSONObj = XML.toJSONObject(Request.getHttpGETAPI("http://thegamesdb.net/api/GetGame.php?id=" + game_objects.get(j).getId()));
                            //String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
                            //System.out.println("***************************************\n" + xmlJSONObj);   


                            data = (JSONObject) xmlJSONObj.getJSONObject("Data");

                            System.out.println(data.toString());

                            JSONObject game_obj = (JSONObject) data.getJSONObject("Game");

                            System.out.println(game_obj.toString());
                        
                            Game game = new Game();
                            game.setGameTitle((String)game_obj.getString("GameTitle"));
                            //game.setDeveloper((String)game_obj.getString("Developer"));
                            game.setId((Integer)game_obj.getInt("id"));
                            game.setReleaseDate((String)game_obj.getString("ReleaseDate"));
                            game.setPlatformId((Integer)game_obj.getInt("PlatformId"));
                            String platform = (String)game_obj.getString("Platform");
                            //Game game = (Game) fromJson(game_obj.toString(), new TypeToken<ArrayList<Game>>() {}.getType());
                            //System.out.println("Game: " + game.getGameTitle() + "Platform ID: " + game.getPlatformId());
                            
                            String values = "'" + game.getId() + "'," + 
                                    "'" + game.getGameTitle() + "'," +
                                    "'" + game.getReleaseDate() + "'," +
                                    "'" + game.getPlatformId()+ "');";
                    
                            System.out.println("INSERT IGNORE INTO `games` (`id`, `name`, `release_date`, `platform_id`) VALUES (" + values );
                            try {
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection konexioa= DriverManager.getConnection("jdbc:mysql://localhost:8889/gamesdb?useUnicode=yes&characterEncoding=UTF-8", 
                                            "root", "root");
                                
                                
                                //konexioa= DriverManager.getConnection("jdbc:mysql://localhost/csv_db", "root", "");
                                Statement sententzia= (Statement) konexioa.createStatement
                                            (ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                                //System.out.println ("INSERT IGNORE INTO `games` (`id`, `name`, `release_date`, `platform_id`) VALUES (" + values);
                                try
                                {

                                    int o=sententzia.executeUpdate("INSERT IGNORE INTO `games` (`id`, `name`, `release_date`, `platform_id`) VALUES (" + values);

                                    System.out.println ("Execute: "+ o);
                                }
                                catch (Exception e)
                                {
                                    System.out.println ("Execute: -1");
                                }


                                konexioa.close();
                                sententzia.close();
                            } catch (ClassNotFoundException | SQLException ex) {
                                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            createFile("sql/" + platform.replace(" ","_") + ".sql", "INSERT IGNORE INTO `games` (`id`, `name`, `release_date`, `platform_id`) VALUES (" + values, false);
                        
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    
                    }
                    
                    
                } 
                catch (FileNotFoundException ex) 
                {
                
                } 
                catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
    }
    
    private static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }
    
    private static void createFile(String file_name, String json, boolean json_val)
    {
        try {
           
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file_name, true));
            
            //JSON Array Start
            if (json_val) bfw.write("[");
            //race_list.size()
            
            bfw.write(json);

            bfw.newLine();
            
            if (json_val) bfw.write("]");
            bfw.close();
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
