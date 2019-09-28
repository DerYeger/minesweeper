package eu.yeger.minesweeper.controller

import eu.yeger.minesweeper.model.Cell
import eu.yeger.minesweeper.model.Game

class GameController(
        private val game: Game,
        private val onGameWon: () -> Unit,
        private val onGameLost: () -> Unit
) {

    init {
        addListeners()
    }

    private fun addListeners() {
        game.cells.forEach { this.addCellListener(it) }
        game.stateProperty.addListener { _, _, newValue ->
            when(newValue) {
                Game.State.WON -> onGameWon.invoke()
                Game.State.LOST -> onGameLost.invoke()
                else -> return@addListener
            }
        }
    }

    private fun addCellListener(cell: Cell) {
        cell.unveiledProperty.addListener { _, _, newValue ->
            when {
                !newValue -> return@addListener
                cell.hasMine -> game.state = Game.State.LOST
                gameWon() -> game.state = Game.State.WON
                else -> unveilNeighbors(cell)
            }
        }
    }

    private fun unveilNeighbors(cell: Cell) {
        cell.neighbors.forEach { neighbor ->
            if (!neighbor.hasMine
                    && !neighbor.hasFlag
                    && !neighbor.unveiled
                    && (neighbor.number == 0 || cell.number == 0)) {
                neighbor.unveiled = true
            }
        }
    }

    private fun gameWon() =
            game.cells.all { it.hasMine xor it.unveiled}
}
