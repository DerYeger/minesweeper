package eu.yeger.minesweeper.controller

import eu.yeger.kotlin.javafx.*
import eu.yeger.minesweeper.ViewConfiguration
import eu.yeger.minesweeper.model.Cell
import eu.yeger.minesweeper.model.Game
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent

object CellController {

    fun initialize(cell: Cell, viewConfiguration: ViewConfiguration) = stackPane {
            maxWidth = viewConfiguration.cellSize
            maxHeight = viewConfiguration.cellSize
            styleClass.add("cell")
            child {
                    if (cell.hasMine) {
                        imageView {
                            image = viewConfiguration.mineIcon
                            fitWidth = viewConfiguration.cellSize
                            fitHeight = viewConfiguration.cellSize
                            styleClass.add("mine")
                            visibleProperty().bindBidirectional(cell.unveiledProperty)
                        }
                    } else {
                        label {
                            textProperty().bind(cell.numberProperty.asString())
                            styleClass.addAll("number", asWord(cell.number))
                            visibleProperty().bindBidirectional(cell.unveiledProperty)
                        }
                    }
            }
        child {
            rectangle {
                width = viewConfiguration.cellSize
                height = viewConfiguration.cellSize
                setOnMouseClicked { handleClick(cell, it) }
                styleClass.add("blocker")
                visibleProperty().bind(cell.unveiledProperty.not())
            }
        }
        child {
            imageView {
                image = viewConfiguration.flagIcon
                fitWidth = viewConfiguration.cellSize
                fitHeight = viewConfiguration.cellSize
                setOnMouseClicked { event -> handleClick(cell, event) }
                styleClass.add("flag")
                visibleProperty().bindBidirectional(cell.flagProperty)
            }
        }
    }

    private fun handleClick(cell: Cell, event: MouseEvent) {
        if (cell.unveiled || cell.game.state != Game.State.IN_PROGRESS) return
        if (event.button == MouseButton.PRIMARY && !cell.hasFlag) {
            cell.unveiled = true
        } else if (event.button == MouseButton.SECONDARY) {
            cell.hasFlag = cell.hasFlag.not()
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
