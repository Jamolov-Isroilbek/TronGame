package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import controller.GameController;
import controller.InputHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import model.Game;
import model.Level;
import model.Motorcycle;
import model.Player;
import persistence.Database;

/**
 * The MainWindow class represents the main window of the game.
 * It displays the game board and handles user input.
 * It also provides a menu for accessing the leader-board and restarting the game.
 * 
 * @author Isroilbek Jamolov
 */
public class MainWindow extends JFrame {

    private final GameController gameController;
    private final Board board;
    private Timer gameTimer;
    private final JLabel levelLabel;
    private final JLabel timerLabel;

    public MainWindow() {
        Game gameInstance = new Game();
        board = new Board(gameInstance);
        for (int i = 1; i <= 2; i++) {
            String playerName = JOptionPane.showInputDialog("Please enter the name for Player " + i + ":");

            if (playerName != null && !playerName.trim().isEmpty()) {
                Color playerColor = JColorChooser.showDialog(null, "Choose a Color for Player " + i, Color.BLACK);
        
                Point startPosition;
                Motorcycle.Direction startDirection;

                if (i == 1) {
                    startPosition = new Point(0, 0);
                    startDirection = Motorcycle.Direction.RIGHT;
                } else {
                    startPosition = new Point(gameInstance.getMaxWidth(), gameInstance.getMaxHeight() - 10);
                    startDirection = Motorcycle.Direction.LEFT;
                }

                Player player = new Player(playerName, playerColor, startPosition, startDirection);
                gameInstance.addPlayer(player);
            } else {
                JOptionPane.showMessageDialog(null, "You must enter a name for Player " + i + ".");
                i--; 
            }
        }
        
        JPanel statusPanel = new JPanel();
        levelLabel = new JLabel("Level: 1");
        timerLabel = new JLabel("Time: 0");
        statusPanel.add(levelLabel);
        statusPanel.add(timerLabel);
        statusPanel.setBackground(new Color(245, 245, 245));
        add(statusPanel, BorderLayout.SOUTH);

        gameController = new GameController(gameInstance, board);

        setTitle("Tron Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(board);

        InputHandler inputHandler = new InputHandler(gameController);
        addKeyListener(inputHandler);

        board.addKeyListener(inputHandler);
        board.setFocusable(true);

        setupGameLoop();
        createMenuBar();
        
        pack();
        
        setResizable(false);

        setVisible(true);
    }

    /**
     * Set up the game loop for continuous updates.
     */
    private void setupGameLoop() {
        gameController.getGame().getCurrentLevel().startLevel();
        gameTimer = new Timer(1, e -> {
            if (!gameController.getGame().isGameOver()) {
                gameController.updateGame();
                board.setSize(gameController.getGame().getMaxWidth(), gameController.getGame().getMaxHeight());
                this.pack();
                board.repaint();
                updateStatusPanel();
            } else {
                gameTimer.stop();
            }
        });

        gameTimer.start();
    }
    
    /**
     * Update the status panel with the current level and timer.
     */
    private void updateStatusPanel() {
        levelLabel.setText("Level: " + (gameController.getGame().getCurrentLevelIndex() + 1));
        long elapsedTime = gameController.getGame().getCurrentLevel().getLevelTime();
        timerLabel.setText("Time: " + elapsedTime / 1000 + "s");
    }

    /**
     * Create a menu bar with options for leader-board and restarting the game.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        
        JMenuItem leaderboardItem = new JMenuItem("Show Leaderboard");
        leaderboardItem.addActionListener(e -> showLeaderboard());
        
        JMenuItem restartGameItem = new JMenuItem("Restart Game");
        restartGameItem.addActionListener(e -> restartGame());
        
        menu.add(leaderboardItem);
        menu.add(restartGameItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    
    /**
     * Restart the game by resetting game state and starting a new game.
     */
    private void restartGame() {
        Game game = gameController.getGame();
        
        game.reset();
        game.start();
        gameTimer.restart();
        
        Level currentLevel = game.getCurrentLevel();
        board.updateLevel(currentLevel);
        pack();
        
        board.repaint();
        
        gameController.getGame().getCurrentLevel().startLevel();
    }

    /**
     * Show the leader-board by retrieving and displaying high scores.
     */
    public void showLeaderboard() {
        Database db = new Database();
        List<String> highScores = db.getTopHighScores();
        
        JOptionPane.showMessageDialog(null, String.join("\n", highScores), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Entry point for starting the game.
     * 
     * @param args command-line arguments (unused).
     */
    public static void main(String[] args) {
        new MainWindow();
    }
}
