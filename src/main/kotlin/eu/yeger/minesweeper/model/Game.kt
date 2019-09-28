package eu.yeger.minesweeper.model

import eu.yeger.kotlin.javafx.*
import javafx.beans.property.SimpleObjectProperty

class Game(val width: Int, val height: Int) {
    val stateProperty = SimpleObjectProperty<State>(State.IN_PROGRESS)
    var state: State by stateProperty.delegation()

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
