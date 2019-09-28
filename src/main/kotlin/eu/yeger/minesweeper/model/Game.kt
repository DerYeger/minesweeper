package eu.yeger.minesweeper.model

import eu.yeger.kotlin.javafx.*
import javafx.beans.property.SimpleObjectProperty

class Game(val width: Int, val height: Int) {
    val stateProperty = SimpleObjectProperty<State>(State.IN_PROGRESS)
    var state: State by stateProperty.delegation()

    val cells = ArrayList<Cell>()

    internal fun withCell(cell: Cell) {
        if (!cells.contains(cell)) {
            cells.add(cell)
        }
    }

    enum class State {
        IN_PROGRESS,
        WON,
        LOST
    }
}
