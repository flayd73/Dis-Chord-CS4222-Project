
/**
 * Write a description of class fiveSecondPopup here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;

public class CountdownPopup extends JDialog {

    private int counter = 5;
    private JLabel label;

    public CountdownPopup(JFrame parent) {
        super(parent, "Countdown", true);

        label = new JLabel("Closing in: " + counter, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        add(label);
        setSize(200, 120);
        setLocationRelativeTo(parent);
        setVisible(true);

        startCountdown();
    }

    private void startCountdown() {
        Timer timer = new Timer(1000, e -> {
            counter--;
            label.setText("Closing in: " + counter);

            if (counter <= 0) {
                ((Timer)e.getSource()).stop();
                dispose();
            }
        });

        timer.start();
    }
}
