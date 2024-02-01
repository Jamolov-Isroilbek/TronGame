package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.Motorcycle;

/**
 * The InputHandler class handles keyboard input for player control.
 * 
 * @author Isroilbek Jamolov DXFV5Y
 */
public class InputHandler extends KeyAdapter {

    private final GameController gameController;

    public InputHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (gameController.getGame().getPlayers().size() < 2) {
            return;
        }

        Motorcycle.Direction currentDirection = gameController.getGame().getPlayers().get(0).getMotorcycle().getDirection();
        Motorcycle.Direction currentDirection2 = gameController.getGame().getPlayers().get(1).getMotorcycle().getDirection();
        switch (key) {
            // Player 1 controls
            case KeyEvent.VK_W -> {
                if (currentDirection != Motorcycle.Direction.DOWN) {
                    gameController.getGame().getPlayers().get(0).getMotorcycle().changeDirection(Motorcycle.Direction.UP);
                }
            }
            case KeyEvent.VK_S -> {
                if (currentDirection != Motorcycle.Direction.UP) {
                    gameController.getGame().getPlayers().get(0).getMotorcycle().changeDirection(Motorcycle.Direction.DOWN);
                }
            }
            case KeyEvent.VK_A -> {
                if (currentDirection != Motorcycle.Direction.RIGHT) {
                    gameController.getGame().getPlayers().get(0).getMotorcycle().changeDirection(Motorcycle.Direction.LEFT);
                }
            }
            case KeyEvent.VK_D -> {
                if (currentDirection != Motorcycle.Direction.LEFT) {
                    gameController.getGame().getPlayers().get(0).getMotorcycle().changeDirection(Motorcycle.Direction.RIGHT);
                }
            }

            // Player 2 controls
            case KeyEvent.VK_UP -> {
                if (currentDirection2 != Motorcycle.Direction.DOWN) {
                    gameController.getGame().getPlayers().get(1).getMotorcycle().changeDirection(Motorcycle.Direction.UP);
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (currentDirection2 != Motorcycle.Direction.UP) {
                    gameController.getGame().getPlayers().get(1).getMotorcycle().changeDirection(Motorcycle.Direction.DOWN);
                }
            }
            case KeyEvent.VK_LEFT -> {
                if (currentDirection2 != Motorcycle.Direction.RIGHT) {
                    gameController.getGame().getPlayers().get(1).getMotorcycle().changeDirection(Motorcycle.Direction.LEFT);
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (currentDirection2 != Motorcycle.Direction.LEFT) {
                    gameController.getGame().getPlayers().get(1).getMotorcycle().changeDirection(Motorcycle.Direction.RIGHT);
                }
            }
        }
    }
}
