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

    var flagIcon: Image? = null
    var mineIcon: Image? = null

    var onGameWon: (() -> Unit)? = null
    var onGameLost: (() -> Unit)? = null

    var modelBuilder: ModelBuilder? = null
    var gameView: GameView? = null

    fun instance(): Node {
        defaultIconFallback()
        defaultBuilderFallback()
        val game = modelBuilder!!.build()
        GameController(game, Runnable { onGameWon }, Runnable { onGameLost })
        return gameView!!.build(game).instance()
    }

    private fun defaultIconFallback() {
        if (flagIcon == null)
            flagIcon = Image("/flag.png", cellSize.toDouble(), cellSize.toDouble(), true, true)
        if (mineIcon == null)
            mineIcon = Image("/mine.png", cellSize.toDouble(), cellSize.toDouble(), true, true)
    }

    private fun defaultBuilderFallback() {
        if (modelBuilder == null)
            modelBuilder = DefaultModelBuilder(width, height, mineCount)
        if (gameView == null)
            gameView = GameView(cellSize, flagIcon!!, mineIcon!!)
    }
}
