package dbhelper.guiStuff;

import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.CardLayout;

import dbhelper.notification;
import dbhelper.serverRequests;

/**
 * JFrame of the program, with a thread for api requests
 * @author James Dai
 * @version 5/29/24
 */
public class loggedIn extends javax.swing.JFrame implements Runnable {
    private Thread programThread;
    private serverRequests sr;
    private homeScreen h;
    private ApiKeysEditor a;
    private settings se;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private String sound = "boom.wav";
    private boolean isSound = true;
    private boolean logginout = false;

    /**
     * starts the api spam thread
     */
    public void startProgramThread(){
        programThread = new Thread(this);
        programThread.start();
        updateThread u = new updateThread(h);
        u.startProgramThread();
    }

    /**
     * turns on/off sound effects
     */
    public void toggleSound(){
        isSound = !isSound;
    }

    /**
     * sets the sound file of notification
     * @param soundName name of sound file
     */
    public void setSound(String soundName){
        sound = soundName;
    }

    /**
     * constructs a loggedIn jPanel, showing homeScreen and hiding the other panels
     * @param s passed to use serverRequests w/cookies
     */
    public loggedIn(serverRequests s){
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        sr = s;
        a = new ApiKeysEditor(this, sr);
        h = new homeScreen(this, sr);
        se = new settings(this, sr);

        mainPanel.add(h, "homeScreen");
        mainPanel.add(a, "apiKeysEditor");
        mainPanel.add(se, "settings");
        this.add(mainPanel);
        this.setVisible(true);

        startProgramThread();
    }

    /**
     * displays apiKeyEditor page
     */
    public void switchToApiPage(){
        cardLayout.show(mainPanel, "apiKeysEditor");
    }

    /**
     * displays settings page
     */
    public void switchToSettings(){
        cardLayout.show(mainPanel, "settings");
    }

    /**
     * displays home page
     */
    public void switchToHomePage(){
        cardLayout.show(mainPanel, "homeScreen");
    }

    /**
     * logs out after completing cycle
     */
    public void logout(){
        logginout = true;
    }
    /**
     * closes this JFrame and starts login JFrame
     */
    @SuppressWarnings("deprecation")
    public void forcelogout(){
        LoginJFrame l = new LoginJFrame();
        programThread.stop();
        l.setVisible(true);
        l.pack();
        l.setLocationRelativeTo(null);
        l.setResizable(false);
        this.dispose();
    }

    /**
     * notifies user by calling notify, sending a popup & sound effect
     */
    public void notifyUsers(){
        notification n = new notification();
        if(isSound){
            n.notify(sound);
        }
        else{
            n.notifying();
        }
    }

    /**
     * thread that gets list of uuids from server side and processes them then
     * returns the processed info to the server side
     */
    @Override
    public void run() {
        //double drawInterval = 7000000000L;
        //double nextDrawTime = System.nanoTime() + drawInterval;
        while(programThread != null){
            ArrayList<String> keys = sr.getKeys();
            ArrayList<String> list = sr.getList();
            ArrayList<String> result = new ArrayList<String>();
            if(list == null){
                try {
                    Thread.sleep(10000);
                    System.out.println("wowie");
                    run();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
            //System.outprintln(keys.size() + " " + list.size());
                if(keys.size() > 0){
                    for(int i = 0; i < keys.size(); i++){
                        ArrayList<String> snippet = new ArrayList<String>();
                        for(int j = 0; j < 60; j++){
                            snippet.add(list.get(i * 60 + j));  
                        }
                        Thread t = new Thread(new ScanThread(keys.get(i), snippet, result));
                        t.run();
                    }
                }
                int buh = 0;
                int updateCounter = 0;
                while(result.size() != keys.size() * 60 && buh < 40){
                    buh++;
                    updateCounter++;
                    if(updateCounter == 2){
                        //System.out.println("THIS IS AN UPDATE PELASE HELP");
                        //h.updateAuctions();
                        updateCounter = 0;
                    }
                    try{
                        Thread.sleep(7000);
                    }catch(Exception e){
                        e.printStackTrace();
                        
                    }
                }
                h.updateTokens();
                h.updateKeyAmount();
                sr.addList(result);
                if(logginout){
                    forcelogout();
                }
            }
            //System.outprintln("can this work plz im tired its 1am");
        }
    }
    
}
