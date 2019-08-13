package eu.yeger.minesweeper.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Collection;

public class Game {

    public final int width;
    public final int height;
    public final ObjectProperty<State> state = new SimpleObjectProperty<>(State.IN_PROGRESS);
    public final ArrayList<Cell> cells = new ArrayList<>();

    public Game(final int width,
                final int height) {
        this.width = width;
        this.height = height;
    }

    public Game withCells(final Collection<Cell> cells) {
        cells.forEach(this::withCell);
        return this;
    }

    public Game withCell(final Cell cell) {
        if (!cells.contains(cell)) {
            cells.add(cell);
            cell.doSetGame(this);
        }
        return this;
    }

    public enum State {
        IN_PROGRESS,
        WON,
        LOST
    }
}
