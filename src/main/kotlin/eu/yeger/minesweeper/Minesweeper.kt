package eu.yeger.minesweeper

import eu.yeger.kotlin.javafx.Fragment
import eu.yeger.kotlin.javafx.child
import eu.yeger.kotlin.javafx.gridPane
import eu.yeger.minesweeper.controller.CellController
import eu.yeger.minesweeper.controller.GameController
import eu.yeger.minesweeper.model.DefaultModelBuilder
import eu.yeger.minesweeper.model.Game
import eu.yeger.minesweeper.model.ModelBuilder
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.image.Image

class Minesweeper {

    var width = 10
    var height = 10
    var mineCount = 20
    var cellSize = 40

    lateinit var flagIcon: Image
    lateinit var mineIcon: Image

    var onGameWon: () -> Unit = {}
    var onGameLost: () -> Unit = {}

    var modelBuilder: ModelBuilder = DefaultModelBuilder
    private val fragmentBuilder: (Game) -> Fragment<Parent> = { game ->
        gridPane {
            alignment = Pos.CENTER
            styleClass.add("container")
            stylesheets.add("/default.css")
            for (cell in game.cells) {
                child(cell.x, cell.y) {
                    CellController(cell, cellSize, flagIcon, mineIcon).initialize()
                }
            }
        }
    }

    fun instance(): Node {
        defaultFallback()
        flagIcon = Image("/flag.png", cellSize.toDouble(), cellSize.toDouble(), true, true)
        mineIcon = Image("/mine.png", cellSize.toDouble(), cellSize.toDouble(), true, true)
        val game = modelBuilder.build(width, height, mineCount)
        GameController(game, onGameWon, onGameLost)
        return fragmentBuilder.invoke(game).instance()
    }

    private fun defaultFallback() {
        if (!::flagIcon.isInitialized)
            flagIcon = Image("/flag.png", cellSize.toDouble(), cellSize.toDouble(), true, true)
        if (!::mineIcon.isInitialized)
            mineIcon = Image("/mine.png", cellSize.toDouble(), cellSize.toDouble(), true, true)
    }
}
