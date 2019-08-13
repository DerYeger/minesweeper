package eu.yeger.minesweeper.model;

import java.util.ArrayList;
import java.util.Collections;

public class ModelBuilder {

    public Game buildGame(final int width,
                          final int height,
                          final int bombCount) {
        final Game game = new Game(width, height);
        initCells(game);
        initBombs(game, bombCount);
        initNumbers(game);
        return game;
    }

    private void initCells(final Game game) {
        final Cell[][] cells = new Cell[game.height][game.width];
        for (int y = 0; y < game.height; y++) {
            for (int x = 0; x < game.width; x++) {
                cells[y][x] = new Cell(x, y)
                        .setGame(game)
                        .withNeighbors(initNeighbors(cells, x, y));
            }
        }
    }

    private ArrayList<Cell> initNeighbors(final Cell[][] cells, final int x, final int y) {
        final ArrayList<Cell> neighbors = new ArrayList<>();
        neighbors.add(getCellAtCoordinates(cells,x - 1, y)); //left
        neighbors.add(getCellAtCoordinates(cells,x - 1, y - 1)); //top left
        neighbors.add(getCellAtCoordinates(cells,x, y - 1)); //top
        neighbors.add(getCellAtCoordinates(cells,x + 1, y - 1)); //top right
        return neighbors;
    }

    private Cell getCellAtCoordinates(final Cell[][] cells, final int x, final int y) {
        if (x < 0 || y < 0|| cells[y].length <= x || cells.length <= y) return null; //out of bounds
        return cells[y][x];
    }

    private void initBombs(final Game game,
                          final int bombCount) {
        ArrayList<Cell> cells = new ArrayList<>(game.cells);
        Collections.shuffle(cells);
        cells
                .stream()
                .limit(bombCount)
                .forEach(cell -> cell.bomb.set(true));
    }

    private void initNumbers(final Game game) {
        game
                .cells
                .forEach(cell -> cell.number.setValue(calculateNumber(cell)));
    }

    private int calculateNumber(final Cell cell) {
        return (int) cell
                .getNeighbors()
                .stream()
                .filter(c -> c.bomb.get())
                .count();
    }
}
