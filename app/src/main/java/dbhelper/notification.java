package dbhelper;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
/**
 * 
 * notification class, supports sound notification and popup
 * @author James Dai
 * @version 5/29/24
 */
public class notification {
    
    /**
     * popup notification with sound as input
     * @param soundFileName input sound file
     */
    public void notify(String soundFileName) {
        //System.out.println("wtfasdfasdfafds");
        // Play sound effect
        playSound(soundFileName);
        
        //System.out.println("wtfasdfasdfafds");
        
        // Show pop-up window
        JOptionPane.showMessageDialog(null, "AN AUCTION HAS BEGUN" , "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * popup notification without sound
     */
    public void notifying() {
        // Play sound effect
        //playSound(soundFileName);
        
        
        
        // Show pop-up window
        JOptionPane.showMessageDialog(null, "AN AUCTION HAS BEGUN" , "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * plays sound 
     * @param soundFileName specific sound
     */
    private void playSound(String soundFileName) {
        try {
            // Load the sound file using a relative path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                getClass().getClassLoader().getResource(soundFileName)
            );
            
            // Get a Clip instance
            Clip clip = AudioSystem.getClip();
            
            // Open the audio input stream
            clip.open(audioInputStream);
            
            // Start playing the sound
            clip.start();
            //JOptionPane.showMessageDialog(null, "AN AUCTION HAS BEGUN" , "Notification", JOptionPane.INFORMATION_MESSAGE);
            
            // Wait for the sound to finish playing
            while (!clip.isRunning()) {
                Thread.sleep(10);
            }
            while (clip.isRunning()) {
                Thread.sleep(10);
            }
            
            // Clean up resources
            clip.close();
            audioInputStream.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
