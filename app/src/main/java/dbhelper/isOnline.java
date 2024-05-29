
package dbhelper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.ArrayList;
/**
 * class that checks if uuid is online 
 */
public class isOnline {

    //private ArrayList<String> apiKeys = new ArrayList<String>();
    //private int curKey;
    private String apiKey;
    public isOnline(String apiK){
        apiKey = apiK;
        //curKey = 0;
    }
    public boolean getHypixelStatus(String uuid) {
        //return true;
        try {
            // Construct the URL for the Hypixel API
            String urlString = "https://api.hypixel.net/status?key=" + apiKey + "&uuid=" + uuid;
            URL url = new URL(urlString);
            
            // Open a connection to the API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //curKey++;
            //if(curKey <= apiKeys.size()){
            //    curKey = 0;
            //}
            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // Return the response
            //System.out.println(response.toString());
            return response.toString().contains("\"online\":true");
            
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error occurred
        }
    }
    
    //public static void main(String[] args) {
    //    boolean status = getHypixelStatus("6dd3ae544c4048d792b72a4a2b0d7f55");
    //    
    //        System.out.println("Hypixel Status: " + status);
    //}
}
