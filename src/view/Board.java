package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import model.Game;
import model.Level;
import model.Motorcycle;
import model.Player;
import model.Trail;

/**
 * The Board class represents the game board where the game is displayed.
 * It updates and renders the game components.
 * 
 * @author Isroilbek Jamolov
 */
public class Board extends JPanel {
    private final Game game;
    
    public Board(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(game.getMaxWidth()+5, game.getMaxHeight()+5));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //updateLevel(game.getCurrentLevel());
        for (Player player : game.getPlayers()) {
            drawMotorcycle(g, player.getMotorcycle());
            for(Trail trail : player.getMotorcycle().getTrails()) {
                drawTrail(g, trail);
            }
        }
    }
    
    /**
     * Draw a motorcycle on the board.
     * 
     * @param g         the graphics context.
     * @param motorcycle the motorcycle to draw.
     */
    private void drawMotorcycle(Graphics g, Motorcycle motorcycle) {
        g.setColor(motorcycle.getTrailColor());
        Point pos = motorcycle.getPosition();
        g.fillRect(pos.x, pos.y, 10, 10);
    }
    
    /**
     * Draw a trail on the board.
     * 
     * @param g     the graphics context.
     * @param trail the trail to draw.
     */
    private void drawTrail(Graphics g, Trail trail) {
        g.setColor(trail.getColor());
        Point pos = trail.getPosition();
        g.fillRect(pos.x, pos.y, 10, 10);
    }
    
    @Override
    public void setSize(int width, int height) {
        this.setPreferredSize(new Dimension(width, height)); 
        revalidate();
        this.repaint();
    }
    
    /**
     * Update the current level of the game.
     * 
     * @param level the current level.
     */
    public void updateLevel(Level level) {
        setPreferredSize(new Dimension(level.getScreenSize()));
        revalidate();
        repaint();
    }
}
