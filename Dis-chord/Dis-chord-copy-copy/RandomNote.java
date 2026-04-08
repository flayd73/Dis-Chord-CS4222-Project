
/**
 * Write a description of class RandomNote here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.sound.sampled.*;
import java.io.File;
import java.util.Random;
public class RandomNote {
     private String[] notes = {
        "Dis-chord/wav/a1.wav",  "Dis-chord/wav/a1s.wav", 
        "Dis-chord/wav/b1.wav",  "Dis-chord/wav/c1.wav",
        "Dis-chord/wav/c1s.wav", "Dis-chord/wav/c2.wav", 
        "Dis-chord/wav/d1.wav",  "Dis-chord/wav/d1s.wav",
        "Dis-chord/wav/e1.wav",  "Dis-chord/wav/f1.wav", 
        "Dis-chord/wav/f1s.wav", "Dis-chord/wav/g1.wav",
        "Dis-chord/wav/g1s.wav"
    };

    private Random rand = new Random();

    // Call this method when a button is clicked
    public void playRandomNote() {
        String chosenFile = notes[rand.nextInt(notes.length)];

        try {
            File file = new File(chosenFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            // Optional: show a message dialog if there’s an error
            // JOptionPane.showMessageDialog(null, "Error playing note: " + e.getMessage());
            e.printStackTrace(); // or silently fail
        }
    }
}
