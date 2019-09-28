package eu.yeger.minesweeper.view

import eu.yeger.kotlin.javafx.child
import eu.yeger.kotlin.javafx.gridPane
import eu.yeger.minesweeper.controller.CellController
import eu.yeger.minesweeper.model.Game
import javafx.geometry.Pos
import javafx.scene.image.Image

class GameView(private val cellSize: Int,
               private val flagIcon: Image,
               private val mineIcon: Image) {

    fun build(game: Game) = gridPane {
        styleClass.add("container")
        stylesheets.add("/default.css")
        alignment = Pos.CENTER
        for (cell in game.cells) {
            child(cell.x, cell.y) {
                CellController(cell, cellSize, flagIcon, mineIcon).initialize()
            }
        }
    }
}
