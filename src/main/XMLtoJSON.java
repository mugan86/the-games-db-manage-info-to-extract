package main;


import model.Platform;
import db.Request;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author anartzmugika
 */
public class XMLtoJSON {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public static String TEST_XML_STRING =
        "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>";
    private static ArrayList<Platform> platforms;
    public static void main(String[] args) throws IOException {
        try {
            
            platforms = new ArrayList<>();
            JSONObject xmlJSONObj = XML.toJSONObject(Request.getHttpGETAPI("http://thegamesdb.net/api/GetPlatformsList.php"));
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            System.out.println(jsonPrettyPrintString);
            
            JSONObject data = (JSONObject) xmlJSONObj.getJSONObject("Data");
            JSONObject Platforms = (JSONObject) data.getJSONObject("Platforms");
            JSONArray array = (JSONArray) Platforms.getJSONArray("Platform");
            System.out.println("Platform count: " + array.length());
            for (int i = 0; i < array.length(); i++)
            {
                try
                {
                    JSONObject object = (JSONObject) array.get(i);
                    
                    Platform p = new Platform (String.valueOf((Integer) object.get("id")), 
                                                (String)object.get("alias"), 
                                                (String)object.get("name"));
             
                    
                    String values = "'" + p.getId() + "'," + 
                                    "'" + p.getSlug() + "'," +
                                    "'" + p.getName()+ "');";
                    
                    System.out.println("INSERT INTO `platforms` (`id`, `slug`, `name`) VALUES (" + values );
                    platforms.add(p);
                    
                    System.out.println("http://thegamesdb.net/api/GetPlatformGames.php?platform=" + p.getId());
                    
                    xmlJSONObj = XML.toJSONObject(Request.getHttpGETAPI("http://thegamesdb.net/api/GetPlatformGames.php?platform=" + p.getId()));
                    jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
                    System.out.println(jsonPrettyPrintString);   
                    createFile("files/" + p.getSlug() + "_games.json", jsonPrettyPrintString);
                }
                catch(JSONException e)
                {
                   
                }
                
            }
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    }
    
    private static void createFile(String file_name, String json)
    {
        try {
           
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file_name, false));
            
            //JSON Array Start
            bfw.write("[");
            //race_list.size()
            
            bfw.write(json);

            bfw.newLine();
            
            bfw.write("]");
            bfw.close();
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
