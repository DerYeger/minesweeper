package eu.yeger.minesweeper.controller

import eu.yeger.minesweeper.model.Cell
import eu.yeger.minesweeper.model.Game

class GameController(private val game: Game,
                     private val onGameWon: () -> Unit,
                     private val onGameLost: () -> Unit) {

    init {
        addListeners()
    }

    private fun addListeners() {
        game.cells.forEach { this.addCellListener(it) }
        game.state.addListener { _, _, newValue ->
            when(newValue) {
                Game.State.WON -> onGameWon.invoke()
                Game.State.LOST -> onGameLost.invoke()
                else -> return@addListener
            }
        }
    }

    private fun addCellListener(cell: Cell) {
        cell.unveiled.addListener { _, _, newValue ->
            when {
                !newValue -> return@addListener
                cell.mine.get() -> game.state.set(Game.State.LOST)
                gameWon() -> game.state.set(Game.State.WON)
                else -> unveilNeighbors(cell)
            }
        }
    }

    private fun unveilNeighbors(cell: Cell) {
        cell.neighbors.forEach { neighbor ->
            if (!neighbor.mine.get()
                    && !neighbor.flagged.get()
                    && !neighbor.unveiled.get()
                    && (neighbor.number.get() == 0 || cell.number.get() == 0)) {
                neighbor.unveiled.set(true)
            }
        }
    }

    private fun gameWon() =
            game.cells.all { it.mine.get() xor it.unveiled.get() }
}
