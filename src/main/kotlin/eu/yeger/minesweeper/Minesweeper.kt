package eu.yeger.minesweeper

import eu.yeger.minesweeper.controller.GameController
import eu.yeger.minesweeper.model.DefaultModelBuilder
import eu.yeger.minesweeper.model.ModelBuilder
import eu.yeger.minesweeper.view.GameView
import javafx.scene.Node
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

    lateinit var modelBuilder: ModelBuilder
    private lateinit var gameView: GameView

    fun instance(): Node {
        defaultIconFallback()
        defaultBuilderFallback()
        val game = modelBuilder.build()
        GameController(game, onGameWon, onGameLost)
        return gameView.build(game).instance()
    }

    private fun defaultIconFallback() {
        if (!::flagIcon.isInitialized)
            flagIcon = Image("/flag.png", cellSize.toDouble(), cellSize.toDouble(), true, true)
        if (!::mineIcon.isInitialized)
            mineIcon = Image("/mine.png", cellSize.toDouble(), cellSize.toDouble(), true, true)
    }

    private fun defaultBuilderFallback() {
        if (!::modelBuilder.isInitialized)
            modelBuilder = DefaultModelBuilder(width, height, mineCount)
        if (!::gameView.isInitialized)
            gameView = GameView(cellSize, flagIcon, mineIcon)
    }
}
