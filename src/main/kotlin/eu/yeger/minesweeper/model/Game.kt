package eu.yeger.minesweeper.model

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty

class Game(val width: Int, val height: Int) {
    val state: ObjectProperty<State> = SimpleObjectProperty(State.IN_PROGRESS)
    val cells = ArrayList<Cell>()

    fun withCells(cells: Collection<Cell>): Game {
        cells.forEach { this.withCell(it) }
        return this
    }

    fun withCell(cell: Cell): Game {
        if (!cells.contains(cell)) {
            cells.add(cell)
            cell.doSetGame(this)
        }
        return this
    }

    enum class State {
        IN_PROGRESS,
        WON,
        LOST
    }
}
