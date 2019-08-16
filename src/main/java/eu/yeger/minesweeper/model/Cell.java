package eu.yeger.minesweeper.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Cell {

    public final int x;
    public final int y;
    public final BooleanProperty mine = new SimpleBooleanProperty(false);
    public final BooleanProperty flag = new SimpleBooleanProperty(false);
    public final BooleanProperty unveiled = new SimpleBooleanProperty(false);
    public final IntegerProperty number = new SimpleIntegerProperty(0);

    private final ArrayList<Cell> neighbors = new ArrayList<>();
    private Game game;

    public Cell(final int x,
                final int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }

    public Cell withNeighbors(final Collection<Cell> neighbors) {
        neighbors
                .stream()
                .filter(Objects::nonNull)
                .forEach(this::withNeighbor);
        return this;
    }

    public Cell withNeighbor(final Cell neighbor) {
        Objects.requireNonNull(neighbor);
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor);
            neighbor.doAddNeighbor(this);
        }
        return this;
    }

    private void doAddNeighbor(final Cell neighbor) {
        Objects.requireNonNull(neighbor);
        neighbors.add(neighbor);
    }

    public Game getGame() {
        return game;
    }

    public Cell setGame(final Game game) {
        Objects.requireNonNull(game);
        game.withCell(this);
        return this;
    }

    void doSetGame(final Game game) {
        Objects.requireNonNull(game);
        this.game = game;
    }
}
