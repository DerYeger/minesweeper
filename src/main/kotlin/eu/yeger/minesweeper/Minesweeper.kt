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

    var flagIcon: (size: Double) -> Image = { size -> Image("/flag.png", size, size, true, true) }
    var mineIcon: (size: Double) -> Image = { size -> Image("/mine.png", size, size, true, true) }

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
                    CellController(
                            cell,
                            cellSize,
                            flagIcon.invoke(cellSize.toDouble()),
                            mineIcon.invoke(cellSize.toDouble())
                    ).initialize()
                }
            }
        }
    }

    fun instance(): Node {
        val game = modelBuilder.build(width, height, mineCount)
        GameController(game, onGameWon, onGameLost)
        return fragmentBuilder.invoke(game).instance()
    }
}
