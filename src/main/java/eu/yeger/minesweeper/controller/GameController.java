package eu.yeger.minesweeper.controller;

import eu.yeger.minesweeper.model.Cell;
import eu.yeger.minesweeper.model.Game;

public class GameController {

    private final Game game;
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
        game.getCells().forEach(this::addCellListener);
        game.getState().addListener((observableValue, oldValue, newValue) -> {
            if (onGameWon != null && newValue.equals(Game.State.WON)) {
                onGameWon.run();
            } else if (onGameLost != null && newValue.equals(Game.State.LOST)) {
                onGameLost.run();
            }
        });
    }

    private void addCellListener(final Cell cell) {
        cell.getUnveiled().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) return;
            if (cell.getMine().get()) {
                game.getState().set(Game.State.LOST);
            } else if (gameWon()) {
                game.getState().set(Game.State.WON);
            } else {
                unveilNeighbors(cell);
            }
        });
    }

    private void unveilNeighbors(final Cell cell) {
        cell.getNeighbors().forEach(neighbor -> {
            if (!neighbor.getMine().get()
                    && !neighbor.getFlagged().get()
                    && !neighbor.getUnveiled().get()
                    && (neighbor.getNumber().get() == 0 || cell.getNumber().get() == 0)) {
                neighbor.getUnveiled().set(true);
            }
        });
    }

    private boolean gameWon() {
        return game.getCells()
                .stream()
                .allMatch(cell -> cell.getMine().get() ^ cell.getUnveiled().get());
    }
}
