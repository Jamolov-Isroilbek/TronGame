package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * The Trail class represents a trail left by a motorcycle.
 * 
 * @author Isroilbek Jamolov DXFV5Y
 */
public class Trail {
    private final Point position;
    private final Color color;
    
    public Trail(Point startPosition, Color color) {
        position = startPosition;
        this.color = color;
    }
    
    /**
     * Draw the trail on the board.
     * 
     * @param g to draw the shape and it's color.
     * @param width the width of the shape of the trail.
     * @param height the height of the shape of the trail.
     */
    public void draw(Graphics g, int width, int height) {
        g.setColor(color);
        g.fillRect(position.x, position.y, width, height);
    }

    public Point getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }
}
