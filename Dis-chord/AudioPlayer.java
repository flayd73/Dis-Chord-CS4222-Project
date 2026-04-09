import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

public class AudioPlayer {

    public void playNote(String noteName) {
        try {
            // This builds the file path. E.g., if noteName is "C4", it looks for "wav/C4.wav"
            File soundFile = new File("wav/" + noteName + ".wav");
            
            if (soundFile.exists()) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } else {
                System.out.println("Could not find file: " + soundFile.getPath());
            }
            
        } catch (Exception e) {
            System.out.println("Audio Error: " + e.getMessage());
        }
    }
}