package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


/**
 * The Motorcycle class represents a player's motorcycle.
 * 
 * @author Isroilbek Jamolov
 */
public class Motorcycle {

    private Point position;
    private Direction direction;
    private Point startPosition;
    private Direction startDirection;
    private int speed;
    private final List<Trail> trails;
    private final Color trailColor;
    private boolean allowTrailTouch;

    public Motorcycle(Point startPosition, Direction startDirection, Color trailColor) {
        position = startPosition;
        direction = startDirection;
        this.startPosition = startPosition;
        this.startDirection = startDirection;
        speed = 3;
        trails = new ArrayList<>();
        this.trailColor = trailColor;
        allowTrailTouch = false;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Enum representing the directions the motorcycle can move.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Moves the motorcycle based on its current direction and speed.
     */
    public void move() {
        switch (direction) {
            case UP ->
                position.y -= speed;
            case DOWN ->
                position.y += speed;
            case LEFT ->
                position.x -= speed;
            case RIGHT ->
                position.x += speed;
        }
        trails.add(new Trail(new Point(position), trailColor));
    }

    /**
     * Updates the positions of motorcycles initial positions depending on the board's size.
     * 
     * @param screenSize 
     */
    public void updatePosition(Dimension screenSize) {
        position.x = screenSize.width;
        position.y = screenSize.height - 10;
    }

    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }

    public void setStartPosition(Point startPosition) {
        this.startPosition = startPosition;
    }

    public void setStartDirection(Direction startDirection) {
        this.startDirection = startDirection;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Resets the motorcycles to their initial positions.
     */
    public void reset() {
        position = new Point(startPosition);
        direction = startDirection;
        trails.clear();
    }

    public List<Trail> getTrails() {
        return trails;
    }

    public Point getPosition() {
        return position;
    }

    public int getSpeed() {
        return speed;
    }

    public Color getTrailColor() {
        return trailColor;
    }

    public boolean getAllowTrailTouch() {
        return allowTrailTouch;
    }

    public void setAllowTrailTouch(boolean allowTrailTouch) {
        this.allowTrailTouch = allowTrailTouch;
    }
}
