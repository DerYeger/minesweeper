package eu.yeger.minesweeper.model

import eu.yeger.kofx.property.objectProperty

class Game(
        val width: Int,
        val height: Int
) {
    val stateProperty = objectProperty(State.IN_PROGRESS)
    var state: State by stateProperty

    val cells = ArrayList<Cell>()

    internal fun withCell(cell: Cell) {
        if (cell !in cells) {
            cells.add(cell)
        }
    }

    enum class State {
        IN_PROGRESS,
        WON,
        LOST
    }
}
