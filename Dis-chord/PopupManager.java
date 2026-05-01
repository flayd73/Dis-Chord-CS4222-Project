import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.util.Random;

public class PopupManager {

    // Pool of annoying / fake messages.
    private static final String[] MESSAGES = {
        "Did you know? The average person rejects 73% of all notes they hear in a lifetime.",
        "Fun fact: B-flat is statistically the most heartbroken note.",
        "Studies show users who upgrade to Premium hear 40% more pleasing chords.",
        "Tip: Try rejecting fewer notes for better matches!",
        "ERROR 0x0FA1: Audio buffer underflow detected. (Just kidding!)",
        "Reminder: Your free trial expires in 3 days.",
        "12 notes near you are looking for a chord. Subscribe now!",
        "Did you know? Mozart never used Dis-Chord and his career suffered.",
        "Compatibility score: 23%. Have you tried Premium?",
        "WARNING: Your sequence has been flagged as 'tonally suspicious'.",
        "Sharing is caring! Tell three friends about Dis-Chord today.",
        "You're #4,712 in line for the next available G#.",
        "Did you accidentally swipe right on a friend's note? Upgrade to undo!",
        "Account verification required. (Just kidding! Click OK to continue.)"
    };

    private final Random random = new Random();
    private final JFrame parent;
    private final Timer timer;

    public PopupManager(JFrame parent) {
        this.parent = parent;
        // Initial wait of 20s, then we randomise
        this.timer = new Timer(25000, e -> showRandomPopup());
        this.timer.setInitialDelay(20000);
    }

    /** Begins the popup harassment loop. */
    public void start() {
        timer.start();
    }

    private void showRandomPopup() {
        String message = MESSAGES[random.nextInt(MESSAGES.length)];
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Dis-Chord",
            JOptionPane.INFORMATION_MESSAGE
        );
        // Pick a fresh delay (25-45s) for next time
        timer.setDelay(25000 + random.nextInt(20000));
    }
}
