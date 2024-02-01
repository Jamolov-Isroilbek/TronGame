// GameController.java
package controller;

import model.Game;
import view.Board;

public class GameController {
    private final Game game;
    private final Board gameBoard;

    public GameController(Game game, Board gameBoard) {
        this.game = game;
        this.gameBoard = gameBoard;
    }

    public void updateGame() {
        game.update();
        gameBoard.repaint();
    }

    public Game getGame() {
        return game;
    }
}
