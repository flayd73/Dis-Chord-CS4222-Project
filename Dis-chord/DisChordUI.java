import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class DisChordUI {

    // --- BACKEND VARIABLES ---
    private NoteRandomizer randomizer;
    private AudioPlayer player;
    private ArrayList<String> sequence;
    private String currentNote;
    private int maxNotes = 8; // Default from the toggle buttons

    // --- UI COMPONENTS THAT NEED UPDATING ---
    private JLabel noteLabel;
    private JLabel counterLabel;
    private JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DisChordUI().createAndShowGUI();
        });
    }

    public DisChordUI() {
        // Initialize backend tools when UI is created
        randomizer = new NoteRandomizer();
        player = new AudioPlayer();
        sequence = new ArrayList<>();
        currentNote = randomizer.getRandomNote(); // Get the very first note
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("DIS-CHORD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // --- LEFT PANEL ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(new RoundedBorder(20, 5));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel titleLabel = new JLabel("DIS-CHORD");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize dynamic counter
        counterLabel = new JLabel("0 / " + maxNotes);
        counterLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        counterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(counterLabel);

        // Initialize dynamic note label with the first random note
        noteLabel = new JLabel(currentNote, SwingConstants.CENTER);
        noteLabel.setFont(new Font("SansSerif", Font.BOLD, 140));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 20));
        actionsPanel.setBackground(Color.WHITE);

        JButton wrongButton = createIconButton("\u2715", new Color(240, 80, 60), 60);
        JButton correctButton = createIconButton("\u2713", new Color(60, 200, 80), 60);

        // --- ACTION: Reject Button (X) ---
        wrongButton.addActionListener(e -> {
            currentNote = randomizer.getRandomNote(); // Get new note
            noteLabel.setText(currentNote);           // Update screen
        });

        // --- ACTION: Accept Button (Check) ---
        correctButton.addActionListener(e -> {
            if (sequence.size() < maxNotes) {
                sequence.add(currentNote); // Add to backend queue
                updateTextArea();          // Update text on screen
                
                counterLabel.setText(sequence.size() + " / " + maxNotes); // Update counter
                
                currentNote = randomizer.getRandomNote(); // Get new note
                noteLabel.setText(currentNote);           // Update screen
            } else {
                JOptionPane.showMessageDialog(frame, "Song is full! Press Play or Refresh.");
            }
        });

        actionsPanel.add(wrongButton);
        actionsPanel.add(correctButton);

        leftPanel.add(titlePanel, BorderLayout.NORTH);
        leftPanel.add(noteLabel, BorderLayout.CENTER);
        leftPanel.add(actionsPanel, BorderLayout.SOUTH);

        // --- RIGHT PANEL ---
        JPanel rightContainer = new JPanel(new BorderLayout(0, 15));
        rightContainer.setBackground(Color.WHITE);

        // Initialize dynamic text area
        textArea = new JTextArea(" Empty Sequence...");
        textArea.setFont(new Font("SansSerif", Font.BOLD, 24));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(15, 15, 15, 15));
        textArea.setEditable(false);

        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setBackground(Color.WHITE);
        textAreaPanel.setBorder(new RoundedBorder(20, 5));
        textAreaPanel.add(textArea, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        controlsPanel.setBackground(Color.WHITE);

        JButton refreshButton = createIconButton("\u21BB", Color.BLACK, 40);
        
        // --- ACTION: Refresh Button ---
        refreshButton.addActionListener(e -> {
            sequence.clear();
            textArea.setText(" Empty Sequence...");
            counterLabel.setText("0 / " + maxNotes);
        });

        JPanel numbersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        numbersPanel.setBackground(Color.WHITE);
        String[] numbers = {"8", "16", "32", "64"};
        ButtonGroup buttonGroup = new ButtonGroup();

        for (String num : numbers) {
            JToggleButton toggle = new JToggleButton(num);
            toggle.setFont(new Font("SansSerif", Font.BOLD, 20));
            toggle.setFocusPainted(false);
            toggle.setBackground(Color.WHITE);
            toggle.setOpaque(true);
            toggle.setPreferredSize(new Dimension(50, 40));
            toggle.setBorder(new RoundedBorder(15, 3));

            if (num.equals("8")) {
                toggle.setSelected(true);
                toggle.setBackground(Color.LIGHT_GRAY);
            }

            toggle.addActionListener(e -> {
                for (Component c : numbersPanel.getComponents()) {
                    c.setBackground(Color.WHITE);
                }
                toggle.setBackground(Color.LIGHT_GRAY);
                
                // Update max notes Fbased on selection
                maxNotes = Integer.parseInt(num);
                counterLabel.setText(sequence.size() + " / " + maxNotes);
            });

            buttonGroup.add(toggle);
            numbersPanel.add(toggle);
        }

        JButton playButton = createIconButton(">", Color.BLACK, 40);
        playButton.setBorder(new RoundedBorder(40, 4));
        playButton.setPreferredSize(new Dimension(50, 50));

        // --- ACTION: Play Button ---
        playButton.addActionListener(e -> {
            if (sequence.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Queue is empty! Accept some notes first.");
                return;
            }
            
            // Run audio on a background thread so the UI doesn't freeze
            new Thread(() -> {
                for (String note : sequence) {
                    player.playNote(note);
                    try {
                        Thread.sleep(500); // 500ms delay between notes. Adjust for speed!
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        });

        controlsPanel.add(refreshButton);
        controlsPanel.add(numbersPanel);
        controlsPanel.add(playButton);

        rightContainer.add(textAreaPanel, BorderLayout.CENTER);
        rightContainer.add(controlsPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel);
        mainPanel.add(rightContainer);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Helper method to update the right-side text area
    private void updateTextArea() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.size(); i++) {
            sb.append(sequence.get(i));
            if (i < sequence.size() - 1) {
                sb.append(" - ");
            }
        }
        textArea.setText(sb.toString());
    }

    // Helper method to create clean, icon-like buttons using text
    private static JButton createIconButton(String text, Color color, int size) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, size));
        button.setForeground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Custom Border class
    static class RoundedBorder implements Border {
        private int radius;
        private int thickness;

        RoundedBorder(int radius, int thickness) {
            this.radius = radius;
            this.thickness = thickness;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.thickness + 5, this.thickness + 5, this.thickness + 5, this.thickness + 5);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2, 
                             width - thickness, height - thickness, radius, radius);
            g2.dispose();
        }
    }
}