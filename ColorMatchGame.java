import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ColorMatchGame extends JFrame {

    private JLabel colorLabel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JButton yesButton;
    private JButton noButton;
    private JButton startButton;

    private String[] colorNames = {"RED", "BLUE", "GREEN", "YELLOW", "ORANGE"};
    private Color[] visualColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};

    private int currentScore = 0;
    private int timeLeft = 30;
    private boolean isGameRunning = false;
    private Timer gameTimer;

    private int currentNameIndex;
    private int currentColorIndex;

    public ColorMatchGame() {
        setTitle("Color Match Reaction");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(20, 20, 100, 30);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel);

        timeLabel = new JLabel("Time: 30");
        timeLabel.setBounds(280, 20, 100, 30);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(timeLabel);

        colorLabel = new JLabel("READY?");
        colorLabel.setBounds(50, 80, 300, 100);
        colorLabel.setFont(new Font("Arial", Font.BOLD, 50));
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(colorLabel);

        yesButton = new JButton("MATCH");
        yesButton.setBounds(40, 220, 140, 50);
        yesButton.setBackground(Color.GREEN);
        yesButton.setForeground(Color.BLACK);
        yesButton.setEnabled(false);
        add(yesButton);

        noButton = new JButton("NO MATCH");
        noButton.setBounds(200, 220, 140, 50);
        noButton.setBackground(Color.RED);
        noButton.setForeground(Color.WHITE);
        noButton.setEnabled(false);
        add(noButton);

        startButton = new JButton("START GAME");
        startButton.setBounds(100, 300, 200, 40);
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(true);
            }
        });

        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(false);
            }
        });

        gameTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
    }

    private void startGame() {
        currentScore = 0;
        timeLeft = 30;
        isGameRunning = true;
        
        scoreLabel.setText("Score: " + currentScore);
        timeLabel.setText("Time: " + timeLeft);
        startButton.setEnabled(false);
        yesButton.setEnabled(true);
        noButton.setEnabled(true);

        nextRound();
        gameTimer.start();
    }

    private void updateTimer() {
        timeLeft--;
        timeLabel.setText("Time: " + timeLeft);

        if (timeLeft <= 0) {
            gameOver();
        }
    }

    private void nextRound() {
        Random rand = new Random();
        currentNameIndex = rand.nextInt(colorNames.length);
        
        if (rand.nextBoolean()) {
            currentColorIndex = currentNameIndex;
        } else {
            currentColorIndex = rand.nextInt(visualColors.length);
        }

        colorLabel.setText(colorNames[currentNameIndex]);
        colorLabel.setForeground(visualColors[currentColorIndex]);
    }

    private void checkAnswer(boolean userSaidMatch) {
        if (!isGameRunning) return;

        boolean isActualMatch = (currentNameIndex == currentColorIndex);

        if (userSaidMatch == isActualMatch) {
            currentScore++;
            getContentPane().setBackground(Color.WHITE);
        } else {
            currentScore--;
            getContentPane().setBackground(Color.LIGHT_GRAY);
        }

        scoreLabel.setText("Score: " + currentScore);
        nextRound();
    }

    private void gameOver() {
        isGameRunning = false;
        gameTimer.stop();
        yesButton.setEnabled(false);
        noButton.setEnabled(false);
        startButton.setEnabled(true);
        colorLabel.setText("GAME OVER");
        colorLabel.setForeground(Color.BLACK);
        JOptionPane.showMessageDialog(this, "Time's up! Final Score: " + currentScore);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ColorMatchGame game = new ColorMatchGame();
                game.setVisible(true);
            }
        });
    }
}