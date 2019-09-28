package eu.yeger.minesweeper.model

import eu.yeger.kotlin.javafx.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty

class Cell(val x: Int,
           val y: Int) {
    val mineProperty = SimpleBooleanProperty(false)
    var hasMine by mineProperty.delegation()

    val flagProperty = SimpleBooleanProperty(false)
    var hasFlag by flagProperty.delegation()

    val unveiledProperty = SimpleBooleanProperty(false)
    var unveiled by unveiledProperty.delegation()

    val numberProperty = SimpleIntegerProperty(0)
    var number by numberProperty.delegation()

    val neighbors = ArrayList<Cell>()
    lateinit var game: Game

    fun withNeighbors(neighbors: Collection<Cell>) {
        neighbors.forEach { this.withNeighbor(it) }
    }

    fun withNeighbor(neighbor: Cell) {
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor)
            neighbor.doAddNeighbor(this)
        }
    }

    private fun doAddNeighbor(neighbor: Cell) {
        neighbors.add(neighbor)
    }

    fun setGame(game: Game): Cell {
        game.withCell(this)
        return this
    }

    internal fun doSetGame(game: Game) {
        this.game = game
    }
}
