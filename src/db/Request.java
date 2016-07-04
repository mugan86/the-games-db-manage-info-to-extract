package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Anartz Muxika
 */
public class Request {

    public static String getHttpGETAPI (String request_url) throws IOException {
        URL url;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        System.out.println("Request URL: " + request_url);

        try
        {
            // create the HttpURLConnection
            url = new URL(request_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");
            
           
            connection.addRequestProperty("User-Agent", 
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);
            connection.setUseCaches(true);
            connection.addRequestProperty("Cache-Control", "max-age=1800");

            connection.setConnectTimeout(20*1000);
            // give it 40 seconds to respond
            connection.setReadTimeout(40*1000);
            connection.connect();

            System.out.println("Response code: " + connection.getResponseCode());
            System.out.println("Response message: " + connection.getResponseMessage());

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
    }

}
