package dbhelper.guiStuff;

import java.util.ArrayList;

import dbhelper.checkLoop;

/**
 * thread to scan snippet with 1 api key
 * @author James Dai
 * @version 5/29/24
 * 
 */
class ScanThread extends Thread {
    private String apiKey;
    private ArrayList<String> l;
    checkLoop loop;
    private ArrayList<String> result;

    /**
     * initiates a ScanThread obj with access to arraylist to output, 
     * arraylist to process, and api Key
     * @param apiK api key
     * @param list list input
     * @param r list output
     */
    public ScanThread(String apiK, ArrayList<String> list, ArrayList<String> r){
        apiKey = apiK;
        l = list;
        result = r;
        loop = new checkLoop(apiKey);
    }

    /**
     * iterates through input list, adds processed info to result list
     */
    public void run()
    {
        try {
            ArrayList<String> temp = loop.check(l);
            //System.outprintln("wtf");
            for(int i = 0; i < temp.size(); i++){
                result.add(temp.get(i));
            }
        }
        catch (Exception e) {
            // Throwing an exception
            e.printStackTrace();
        }
    }
}
