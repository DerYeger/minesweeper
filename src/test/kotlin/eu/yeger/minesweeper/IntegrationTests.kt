package eu.yeger.minesweeper

import eu.yeger.minesweeper.model.Cell
import eu.yeger.minesweeper.model.Game
import eu.yeger.minesweeper.model.ModelBuilder
import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.MouseButton
import javafx.stage.Stage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.util.WaitForAsyncUtils

class IntegrationTests : ApplicationTest() {

    private lateinit var stage: Stage

    private var won: Boolean = false
    private var lost: Boolean = false

    override fun start(stage: Stage) {
        this.stage = stage
        won = false
        lost = false
    }

    private fun click(x: Int, y: Int, button: MouseButton) {
        clickOn(stage.x + x, stage.y + y, button)
    }

    private fun setScene(scene: Scene) {
        Platform.runLater {
            stage.apply {
                hide()
                this.scene = scene
                sizeToScene()
                toFront()
                show()
            }
        }
        WaitForAsyncUtils.waitForFxEvents()
    }

    private fun testGameAlphaBuilder() = object: ModelBuilder {
        override fun build(): Game {
            val game = Game(2, 2)
            val tl = Cell(0, 0)
            val tr = Cell(1, 0)
            val bl = Cell(0, 1)
            val br = Cell(1, 1)
            tl.withNeighbors(listOf(tr, bl, br))
            game.withCells(listOf(tl, tr, bl, br))
            br.hasMine = true

            return game
        }
    }

    private fun testGameBetaBuilder() = object: ModelBuilder {
        override fun build(): Game {
            val game = Game(2, 2)
            val tl = Cell(0, 0)
            val tr = Cell(1, 0)
            val bl = Cell(0, 1)
            val br = Cell(1, 1)
            tl.number = 1
            tr.number = 1
            bl.number = 1
            br.number = 1
            tl.withNeighbors(listOf(tr, bl, br))
            game.withCells(listOf(tl, tr, bl, br))
            br.hasMine = true

            return game
        }
    }

    private fun testGameGammaBuilder() = object: ModelBuilder {
        override fun build(): Game {
            val game = Game(10, 1)
            for (i in 0..9) {
                val cell = Cell(i, 0)
                cell.setGame(game).number = i
            }

            return game
        }
    }


    @Test
    fun testWin() {
        val minesweeper = Minesweeper().apply {
            width = 2
            height = 2
            mineCount = 0
            cellSize = 30
            onGameWon = { won = true }
            onGameLost = { lost = true }
        }
        setScene(Scene(minesweeper.instance() as Parent))
        click(15, 15, MouseButton.PRIMARY)
        WaitForAsyncUtils.waitForFxEvents()
        assertTrue(won)
        assertFalse(lost)
    }

    @Test
    fun testFlag() {
        val minesweeper = Minesweeper().apply {
            width = 1
            height = 1
            mineCount = 1
            cellSize = 20
            onGameWon = { won = true }
            onGameLost = { lost = true }
        }
        setScene(Scene(minesweeper.instance() as Parent))
        clickOn(stage, MouseButton.SECONDARY)
        clickOn(stage, MouseButton.PRIMARY)
        assertFalse(won)
        assertFalse(lost)
        clickOn(stage, MouseButton.SECONDARY)
        clickOn(stage, MouseButton.PRIMARY)
        assertFalse(won)
        assertTrue(lost)
    }

    @Test
    fun testUnveiling() {
        val minesweeper = Minesweeper().apply {
            cellSize = 30
            onGameWon = { won = true }
            onGameLost = { lost = true }
            this.modelBuilder = testGameAlphaBuilder()
        }
        setScene(Scene(minesweeper.instance() as Parent))
        click(15, 15, MouseButton.PRIMARY)
        WaitForAsyncUtils.waitForFxEvents()
        assertTrue(won)
        assertFalse(lost)
    }

    @Test
    fun testUnveilingBlocked() {
        val minesweeper = Minesweeper().apply {
            cellSize = 30
            onGameWon = { won = true }
            onGameLost = { lost = true }
            this.modelBuilder = testGameBetaBuilder()
        }
        setScene(Scene(minesweeper.instance() as Parent))
        click(15, 15, MouseButton.PRIMARY)
        WaitForAsyncUtils.waitForFxEvents()
        assertFalse(won)
        assertFalse(lost)
    }

    @Test
    fun testNumbers() {
        val minesweeper = Minesweeper().apply {
            cellSize = 30
            onGameWon = { won = true }
            onGameLost = { lost = true }
            this.modelBuilder = testGameGammaBuilder()
        }
        setScene(Scene(minesweeper.instance() as Parent))
        for (i in 0..9) {
            assertFalse(won)
            click(15 + 30 * i, 15, MouseButton.PRIMARY)
        }
        WaitForAsyncUtils.waitForFxEvents()
        assertTrue(won)
        assertFalse(lost)
        assertNotNull(lookup("#zero"))
        assertNotNull(lookup("#one"))
        assertNotNull(lookup("#two"))
        assertNotNull(lookup("#three"))
        assertNotNull(lookup("#four"))
        assertNotNull(lookup("#five"))
        assertNotNull(lookup("#six"))
        assertNotNull(lookup("#seven"))
        assertNotNull(lookup("#eight"))
        assertNotNull(lookup("#null"))
    }
}
