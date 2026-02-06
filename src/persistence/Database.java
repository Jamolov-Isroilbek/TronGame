package persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import model.Player;

/**
 * The Database class handles interactions with the game's database.
 *
 * @author Isroilbek Jamolov
 */
public class Database {

    private final String TABLE_NAME = "leader_board";
    private final Connection conn;

    public Database() {
        Connection c = null;
        
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("../../db.properties"));

            String host = props.getProperty("db.host");
            String port = props.getProperty("db.port");
            String dbName = props.getProperty("db.name");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            String url =
                "jdbc:mysql://" + host + ":" + port + "/" + dbName +
                "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(url, user, password);

            System.out.println("Database connection established");

        } catch (ClassNotFoundException | SQLException | IOException ex) {
            System.out.println("No connection");
            ex.printStackTrace();
        }
        this.conn = c;
    }

    /**
     * Stores the high score for a player.
     *
     * @param player the player whose score to store.
     */
    public void storeHighScore(Player player) {
        String playerName = player.getName();
        int newScore = player.getScore();

        try {
            // First, get the current score
            int currentScore = 0;
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT score FROM " + TABLE_NAME + " WHERE player_name = ?"
            )) {
                pstmt.setString(1, playerName);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    currentScore = rs.getInt("score");
                }
            }

            // pdate the score in the database
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE " + TABLE_NAME + " SET score = ? WHERE player_name = ?"
            )) {
                pstmt.setInt(1, currentScore + newScore);
                pstmt.setString(2, playerName);
                int affectedRows = pstmt.executeUpdate();

                // If no existing score, insert a new one
                if (affectedRows == 0) {
                    try (PreparedStatement insertStmt = conn.prepareStatement(
                            "INSERT INTO " + TABLE_NAME + " (player_name, score) VALUES (?, ?)"
                    )) {
                        insertStmt.setString(1, playerName);
                        insertStmt.setInt(2, newScore);
                        insertStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("storeHighScore error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the high scores from the database.
     * 
     * @return a list of high scores.
     */
    public List<String> getTopHighScores() {
        List<String> scores = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT player_name, SUM(score) AS total_score FROM " + TABLE_NAME + " GROUP BY player_name ORDER BY total_score DESC LIMIT 10"
        )) {
            ResultSet rs = pstmt.executeQuery();
            int rank = 1;
            while (rs.next()) {
                String playerName = rs.getString("player_name");
                int score = rs.getInt("total_score");
                scores.add(rank + ". " + playerName + ": " + score);
                rank++;
            }
        } catch (SQLException e) {
            System.out.println("getHighScores error: " + e.getMessage());
            e.printStackTrace();
        }
        return scores;
    }

    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("closeConnection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clearLeaderBoard() {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + TABLE_NAME)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("clearLeaderBoard error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
