package model;

import java.awt.Dimension;

/**
 * The Level class represents a game level.
 * 
 * @author Isroilbek Jamolov DXFV5Y
 */
public class Level {
    private final int speed;
    private final Dimension screenSize;
    private final boolean allowTrailTouch;
    private long levelStartTime;
    
    public Level(int speed, Dimension screenSize, boolean allowTrailTouch) {
        this.speed = speed;
        this.screenSize = screenSize;
        this.allowTrailTouch = allowTrailTouch;
    }
    
    /**
     * Initializes the start of the Level's time.
     */
    public void startLevel() {
        levelStartTime = System.currentTimeMillis();
    }
    
    public long getLevelTime() {
        return System.currentTimeMillis() - levelStartTime;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public Dimension getScreenSize() {
        return screenSize;
    }
    
    public boolean isAllowTrailTouch() {
        return allowTrailTouch;
    }
}
