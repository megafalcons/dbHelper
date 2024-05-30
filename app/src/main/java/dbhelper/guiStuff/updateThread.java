package dbhelper.guiStuff;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;


/**
 * updates auctions displayed in client
 * @author James Dai
 * @version 5/29/24
 */
class updateThread implements Runnable {
    private homeScreen h;
    private Thread prgmThread;
    private int updateOld;

    /**
     * begins thread to update auctions
     * @param home to access home functions 
     */
    public updateThread(homeScreen home){
        h = home;
    }

    /**
     * starts auction update thread
     */
    public void startProgramThread(){
        prgmThread = new Thread(this);
        prgmThread.start();
    }

    /**
     * checks every 15 seconds for auction updates
     */
    public void run()
    {
        while(prgmThread != null){
            h.updateAuctions();
            System.out.println("wts");
            try {
                Thread.sleep(15000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

