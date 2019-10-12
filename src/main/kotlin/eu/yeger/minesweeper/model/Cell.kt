package eu.yeger.minesweeper.model

import eu.yeger.kofx.property.booleanProperty
import eu.yeger.kofx.property.integerProperty

class Cell(
        val game: Game,
        val x: Int,
        val y: Int
) {
    val mineProperty = booleanProperty(false)
    var hasMine by mineProperty

    val flagProperty = booleanProperty(false)
    var hasFlag by flagProperty

    val unveiledProperty = booleanProperty(false)
    var unveiled by unveiledProperty

    val numberProperty = integerProperty(0)
    var number by numberProperty

    val neighbors = ArrayList<Cell>()

    init {
        game.withCell(this)
    }

    fun withNeighbors(neighbors: Collection<Cell>) {
        neighbors.forEach { this.withNeighbor(it) }
    }

    private fun withNeighbor(neighbor: Cell) {
        if (neighbor !in neighbors) {
            neighbors.add(neighbor)
            neighbor.doAddNeighbor(this)
        }
    }

    private fun doAddNeighbor(neighbor: Cell) {
        neighbors.add(neighbor)
    }
}
