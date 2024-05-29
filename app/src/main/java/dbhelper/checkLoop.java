package dbhelper;
import java.util.*;

/**
 * loops through arraylist input of uuids, processes it
 * @author James Dai
 * @version 5/29/24
 */
public class checkLoop {
    //private static notification notis = new notification();
    //private ArrayList<String> apiKeys = new ArrayList<String>();
    private String apiKey;
    private isOnline tester;
    //private ArrayList<String> apiKeys = new ArrayList<String>();

    /**
     * initiate object with api key set
     * @param apiK api key
     */
    public checkLoop(String apiK){
        apiKey = apiK;
        tester = new isOnline(apiK);
    }
    
    /**
     * goes through ids arraylist and processes each id
     * @param ids ids arraylist
     * @return processed arraylist
     */
    public ArrayList<String> check(ArrayList<String> ids){
        ArrayList<String> result = new ArrayList<String>();
        
        for(int i = 0; i < ids.size(); i++){
            long curTime = System.currentTimeMillis();
            //System.out.println(ids.get(i));
            if(tester.getHypixelStatus(ids.get(i))){
                
                result.add(ids.get(i) + ":" + "true");
                //System.out.println(ids.get(i) + ":" + "true");
            }
            else{
                result.add(ids.get(i) + ":" + "false");
                //System.out.println(ids.get(i) + ":" + "false");

            }
            //System.out.println(i);
            long newTime = curTime + 1000 - System.currentTimeMillis();
            if(newTime < 0){
                newTime = 0;
            }
            try {
                Thread.sleep(newTime); // Sleep for 1000 milliseconds (1 second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
