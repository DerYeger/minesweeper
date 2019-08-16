package eu.yeger.minesweeper.model;

import java.util.ArrayList;
import java.util.Collections;

public class DefaultModelBuilder implements ModelBuilder {

    private final int width;
    private final int height;
    private final int bombCount;

    public DefaultModelBuilder(final int width,
                               final int height,
                               final int bombCount) {
        this.width = width;
        this.height = height;
        this.bombCount = bombCount;
    }

    public Game build() {
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

    private ArrayList<Cell> initNeighbors(final Cell[][] cells,
                                          final int x,
                                          final int y) {
        final ArrayList<Cell> neighbors = new ArrayList<>();
        neighbors.add(getCellAtCoordinates(cells,x - 1, y)); //left
        neighbors.add(getCellAtCoordinates(cells,x - 1, y - 1)); //top left
        neighbors.add(getCellAtCoordinates(cells,x, y - 1)); //top
        neighbors.add(getCellAtCoordinates(cells,x + 1, y - 1)); //top right
        return neighbors;
    }

    private Cell getCellAtCoordinates(final Cell[][] cells,
                                      final int x,
                                      final int y) {
        if (y < 0 || cells.length <= y || x < 0 || cells[y].length <= x) return null; //out of bounds
        return cells[y][x];
    }

    private void initBombs(final Game game,
                          final int bombCount) {
        ArrayList<Cell> cells = new ArrayList<>(game.cells);
        Collections.shuffle(cells);
        cells
                .stream()
                .limit(bombCount)
                .forEach(cell -> cell.mine.set(true));
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
                .filter(c -> c.mine.get())
                .count();
    }
}
