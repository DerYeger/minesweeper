package eu.yeger.minesweeper.controller;

import eu.yeger.minesweeper.model.Cell;
import eu.yeger.minesweeper.model.Game;
import javafx.scene.layout.GridPane;

public class GameController {

    public final Game game;
    private final Runnable onGameWon;
    private final Runnable onGameLost;

    public GameController(final Game game,
                          final Runnable onGameWon,
                          final Runnable onGameLost) {
        this.game = game;
        this.onGameWon = onGameWon;
        this.onGameLost = onGameLost;
        addListeners();
    }

    private void addListeners() {
        game.cells.forEach(this::addCellListener);
        game.state.addListener((observableValue, oldValue, newValue) -> {
            if (onGameWon != null && newValue.equals(Game.State.WON)) {
                onGameWon.run();
            } else if (onGameLost != null && newValue.equals(Game.State.LOST)) {
                onGameLost.run();
            }
        });
    }

    private void addCellListener(final Cell cell) {
        cell.unveiled.addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) return;
            if (cell.bomb.get()) {
                System.out.println("Lost");
                game.state.set(Game.State.LOST);
            } else if (gameWon()) {
                System.out.println("Won");
                game.state.set(Game.State.WON);
            } else {
                unveilNeighbors(cell);
            }
        });
    }

    private void unveilNeighbors(final Cell cell) {
        cell.getNeighbors().forEach(neighbor -> {
            if (!neighbor.bomb.get()
                    && !neighbor.flag.get()
                    && !neighbor.unveiled.get()
                    && (neighbor.number.get() == 0 || cell.number.get() == 0)) {
                neighbor.unveiled.set(true);
            }
        });
    }

    private boolean gameWon() {
        return game
                .cells
                .stream()
                .allMatch(cell -> cell.bomb.get() ^ cell.unveiled.get());
    }
}
