package model;

import java.awt.Color;
import java.awt.Point;
import model.Motorcycle.Direction;

/**
 * The Player class represents a player in the game.
 * 
 * @author Isroilbek Jamolov
 */
public class Player {
    private final String name;
    private final Motorcycle motorcycle;
    private final int score;
    
    public Player(String name, Color color, Point initialPosition, Direction initialDirection) {
        this.name = name;
        this.motorcycle = new Motorcycle(initialPosition, initialDirection, color);
        this.score = 1;
    }   
    
    /**
     * Resets the player's motorcycles.
     */
    public void reset() {
        motorcycle.reset();
    }

    public String getName() {
        return name;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }
    
    public int getScore() {
        return score;
    }
}
