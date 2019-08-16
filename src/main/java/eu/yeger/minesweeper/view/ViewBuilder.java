package eu.yeger.minesweeper.view;

import eu.yeger.minesweeper.controller.CellController;
import eu.yeger.minesweeper.model.Cell;
import eu.yeger.minesweeper.model.Game;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class ViewBuilder {

    private final String style;
    private final int cellSize;
    private final Image flagIcon;
    private final Image mineIcon;

    public ViewBuilder(final String style,
                       final int cellSize,
                       final String flagIcon,
                       final String mineIcon) {
        this.style = style;
        this.cellSize = cellSize;
        this.flagIcon = new Image(flagIcon);
        this.mineIcon = new Image(mineIcon);
    }

    public GridPane buildView(final Game game) {
        final GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("container");
        for (final Cell cell : game.cells) {
            final CellController cellController = new CellController(cell, cellSize, flagIcon, mineIcon);
            final Node cellNode = cellController.initialize();
            gridPane.add(cellNode, cell.x, cell.y);
        }
        gridPane.setGridLinesVisible(true);
        gridPane.getStylesheets().add(style);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }
}
