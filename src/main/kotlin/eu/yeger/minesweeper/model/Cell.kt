package eu.yeger.minesweeper.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty

import java.util.ArrayList
import java.util.Objects

class Cell(val x: Int,
           val y: Int) {
    val mine: BooleanProperty = SimpleBooleanProperty(false)
    val flagged: BooleanProperty = SimpleBooleanProperty(false)
    val unveiled: BooleanProperty = SimpleBooleanProperty(false)
    val number: IntegerProperty = SimpleIntegerProperty(0)

    val neighbors = ArrayList<Cell>()
    var game: Game? = null

    fun withNeighbors(neighbors: Collection<Cell>): Cell {
        neighbors
                .stream()
                .filter { Objects.nonNull(it) }
                .forEach { this.withNeighbor(it) }
        return this
    }

    fun withNeighbor(neighbor: Cell): Cell {
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor)
            neighbor.doAddNeighbor(this)
        }
        return this
    }

    private fun doAddNeighbor(neighbor: Cell) {
        neighbors.add(neighbor)
    }

    fun setGame(game: Game): Cell {
        Objects.requireNonNull(game)
        game.withCell(this)
        return this
    }

    internal fun doSetGame(game: Game) {
        Objects.requireNonNull(game)
        this.game = game
    }
}
