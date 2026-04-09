import java.util.Random;

public class NoteRandomizer {
    
    // An array containing the names of your notes. 
    // Make sure these match your audio file names exactly!
    private String[] notes = {"a1", "a1s", "b1", "c1", "c1s", "c2", "d1", "d1s", "e1", "f1", "f1s", "g1", "g1s"};
    private Random random;

    public NoteRandomizer() {
        random = new Random();
    }

    // This is the method your UI will call when the user clicks 'X' or '✓'
    public String getRandomNote() {
        int index = random.nextInt(notes.length);
        return notes[index];
    }
}