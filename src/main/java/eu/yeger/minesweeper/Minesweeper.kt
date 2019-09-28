package eu.yeger.minesweeper

import eu.yeger.minesweeper.controller.GameController
import eu.yeger.minesweeper.model.DefaultModelBuilder
import eu.yeger.minesweeper.model.ModelBuilder
import eu.yeger.minesweeper.view.GameView
import javafx.scene.Node
import javafx.scene.image.Image
import lombok.Builder

@Builder
class Minesweeper {

    @Builder.Default
    private val width = 10
    @Builder.Default
    private val height = 10
    @Builder.Default
    private val mineCount = 20
    @Builder.Default
    private val cellSize = 40

    private var flagIcon: Image? = null
    private var mineIcon: Image? = null

    private val onGameWon: Runnable? = null
    private val onGameLost: Runnable? = null

    private var modelBuilder: ModelBuilder? = null
    private var gameView: GameView? = null

    fun instance(): Node {
        defaultIconFallback()
        defaultBuilderFallback()
        val game = modelBuilder!!.build()
        GameController(game, onGameWon, onGameLost)
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
