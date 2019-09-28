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
            maxWidth = cellSize.toDouble()
            maxHeight = cellSize.toDouble()
            styleClass.add("cell")
            child {
                    if (cell.mine.get()) {
                        imageView {
                            image = mineIcon
                            fitWidth = cellSize.toDouble()
                            fitHeight = cellSize.toDouble()
                            styleClass.add("mine")
                            visibleProperty().bindBidirectional(cell.unveiled)
                        }
                    } else {
                        label {
                            textProperty().bind(cell.number.asString())
                            styleClass.addAll("number", asWord(cell.number.get()))
                            visibleProperty().bindBidirectional(cell.unveiled)
                        }
                    }
            }
        child {
            rectangle {
                width = cellSize.toDouble()
                height = cellSize.toDouble()
                setOnMouseClicked { handleClick(cell, it) }
                styleClass.add("blocker")
                visibleProperty().bind(cell.unveiled.not())
            }
        }
        child {
            imageView {
                image = flagIcon
                fitWidth = cellSize.toDouble()
                fitHeight = cellSize.toDouble()
                setOnMouseClicked { event -> handleClick(cell, event) }
                styleClass.add("flag")
                visibleProperty().bindBidirectional(cell.flagged)
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
