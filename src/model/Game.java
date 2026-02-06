package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import persistence.Database;

/**
 * The Game class represents the game state.
 *
 * @author Isroilbek Jamolov
 */
public final class Game {

    private final List<Player> players;
    private final Level[] levels;
    private int currentLevelIndex = 0;
    private int maxWidth = 600;
    private int maxHeight = 600;
    private boolean gameOver;

    public Game() {
        levels = new Level[10];
        int speed = 3;
        for (int i = 0; i < 10; i++) {
            Dimension screenSize = new Dimension(600 - i * 25, 600 - i * 25);
            boolean allowTrailTouch = i > 6;
            if (i % 3 == 0) {
                speed++;
            }
            levels[i] = new Level(speed, screenSize, allowTrailTouch);
        }

        players = new ArrayList<>();
        gameOver = false;
    }

    /**
     * Advances the game to the next level.
     */
    public void nextLevel() {
        if (currentLevelIndex < levels.length - 1) {
            currentLevelIndex++;
        } else {
            currentLevelIndex = 0;
        }
        
        Level currentLevel = levels[currentLevelIndex];
        maxWidth = currentLevel.getScreenSize().width;
        maxHeight = currentLevel.getScreenSize().height;

        for (Player player : players) {
            player.getMotorcycle().setSpeed(currentLevel.getSpeed());
            player.getMotorcycle().updatePosition(currentLevel.getScreenSize());
        }

        resetPlayerPositionsAndDirections();
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Adds a player to the game.
     *
     * @param player the player to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Starts the game.
     */
    public void start() {
        if (!players.isEmpty()) gameOver = false;
    }

    /**
     * Ends the game.
     */
    public void end() {
        if (gameOver) return;
        Player loser = checkCollision();
        Player winner = loser.equals(players.get(0)) ? players.get(1) : players.get(0);
        gameOver = true;

        updateDatabase(winner);
        showEndGameDialog(winner.getName());
    }

    /**
     * Resets the game to is initial state.
     */
    public void reset() {
        for (Player player : players) {
            player.reset();
        }
        gameOver = false;

        resetPlayerPositionsAndDirections();
    }
    
    /**
     * Updates the game state.
     */
    public void update() {
        for (Player player : players) {
            player.getMotorcycle().move();
            if (checkCollision() != null) {
                end();
            }
        }
    }
    
    /**
     * Checks for collisions between players' motorcycles and trails, or with boundaries of the board.
     * 
     * @return the player who collided or null if no collision.
     */
    public Player checkCollision() {
        int size = 10;

        for (Player player : players) {
            Point currentPosition = player.getMotorcycle().getPosition();
            if (!isInBounds(currentPosition)) {
                return player;
            }
            for (Player otherPlayer : players) {
                if (otherPlayer != player) {
                    List<Trail> trails = otherPlayer.getMotorcycle().getTrails();
                    for (Trail trail : trails) {
                        Point trailPosition = trail.getPosition();
                        if (Math.abs(trailPosition.x - currentPosition.x) < size 
                                && Math.abs(trailPosition.y - currentPosition.y) < size) {
                            return player;
                        }
                    }
                } else if (otherPlayer == player && !player.getMotorcycle().getAllowTrailTouch()) {
                    List<Trail> trails = otherPlayer.getMotorcycle().getTrails();
                    for (int i = 0; i < trails.size(); i++) {
                        Trail trail = trails.get(i);
                        Point trailPosition = trail.getPosition();
                        if (i == trails.size() - 1) {
                            continue;
                        }
                        if (currentPosition.equals(trailPosition)) {
                            return player;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Checks if a point is within the game's bounds.
     * 
     * @param position the point to check.
     * @return true if the point is within bounds, false otherwise.
     */
    private boolean isInBounds(Point position) {
        return position.x >= 0 && position.x <= maxWidth && position.y >= 0 && position.y <= maxHeight;
    }
    
    /**
     * Resets player positions and directions to their initial states.
     */
    private void resetPlayerPositionsAndDirections() {
        if (players.size() >= 2) {
            Player player1 = players.get(0);
            Player player2 = players.get(1);

            // Resetting to initial positions
            player1.getMotorcycle().setStartPosition(new Point(0, 0));
            player2.getMotorcycle().setStartPosition(new Point(maxWidth, maxHeight - 10));

            // Resetting to initial directions
            player1.getMotorcycle().setStartDirection(Motorcycle.Direction.RIGHT);
            player2.getMotorcycle().setStartDirection(Motorcycle.Direction.LEFT);

            // Applying the reset
            player1.getMotorcycle().reset();
            player2.getMotorcycle().reset();
        }
        levels[currentLevelIndex].startLevel();
    }
    
    
    /**
     * Updates the database with the winner's score.
     * 
     * @param winner the winning player.
     */
    private void updateDatabase(Player winner) {
        Database db = new Database();
        db.storeHighScore(winner);
        db.closeConnection();
    }

    /**
     * Shows an end game dialog with the winner's name.
     * 
     * @param winnerName the name of the winner.
     */
    private void showEndGameDialog(String winnerName) {
        int option = JOptionPane.showConfirmDialog(null, winnerName + " wins! " + "Do you want to continue?", "End of the Game", 
                JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            reset();
            nextLevel();
        } else {
            resetPlayerPositionsAndDirections();
            currentLevelIndex = 0;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public Level getCurrentLevel() {
        return levels[currentLevelIndex];
    }
    
    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
