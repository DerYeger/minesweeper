package eu.yeger.minesweeper.controller

import eu.yeger.kotlin.javafx.*
import eu.yeger.minesweeper.model.Cell
import eu.yeger.minesweeper.model.Game
import javafx.scene.image.Image
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent

class CellController(private val cell: Cell,
                     private val cellSize: Int,
                     private val flagIcon: Image,
                     private val mineIcon: Image) {

    fun initialize() = stackPane {
            styleClass.add("cell")
            maxWidth = cellSize.toDouble()
            maxHeight = cellSize.toDouble()
            child {
                    if (cell.mine.get()) {
                        imageView {
                            image = mineIcon
                            visibleProperty().bindBidirectional(cell.unveiled)
                            styleClass.add("mine")
                            fitWidth = cellSize.toDouble()
                            fitHeight = cellSize.toDouble()
                        }
                    } else {
                        label {
                            textProperty().bind(cell.number.asString())
                            visibleProperty().bindBidirectional(cell.unveiled)
                            styleClass.addAll("number", asWord(cell.number.get()))
                        }
                    }
            }
        child {
            rectangle {
                visibleProperty().bind(cell.unveiled.not())
                styleClass.add("blocker")
                width = cellSize.toDouble()
                height = cellSize.toDouble()
                setOnMouseClicked { handleClick(cell, it) }
            }
        }
        child {
            imageView {
                image = flagIcon
                visibleProperty().bindBidirectional(cell.flagged)
                styleClass.add("flag")
                setOnMouseClicked { event -> handleClick(cell, event) }
                fitWidth = cellSize.toDouble()
                fitHeight = cellSize.toDouble()
            }
        }
    }

    private fun handleClick(cell: Cell, event: MouseEvent) {
        if (cell.unveiled.get() || cell.game.state.get() != Game.State.IN_PROGRESS) return
        if (event.button == MouseButton.PRIMARY && !cell.flagged.get()) {
            cell.unveiled.set(true)
        } else if (event.button == MouseButton.SECONDARY) {
            cell.flagged.set(!cell.flagged.get())
        }
    }

    private fun asWord(number: Int) = when(number) {
        0 -> "zero"
        1 -> "one"
        2 -> "two"
        3 -> "three"
        4 -> "four"
        5 -> "five"
        6 -> "six"
        7 -> "seven"
        8 -> "eight"
        else -> null
    }
}
