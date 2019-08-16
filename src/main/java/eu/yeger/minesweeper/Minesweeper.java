package eu.yeger.minesweeper;

import eu.yeger.minesweeper.controller.GameController;
import eu.yeger.minesweeper.model.Game;
import eu.yeger.minesweeper.model.DefaultModelBuilder;
import eu.yeger.minesweeper.model.ModelBuilder;
import eu.yeger.minesweeper.view.ViewBuilder;
import javafx.scene.Node;
import javafx.scene.image.Image;
import lombok.Builder;

@Builder
public class Minesweeper {

    @Builder.Default
    private int width = 10;
    @Builder.Default
    private int height = 10;
    @Builder.Default
    private int mineCount = 20;
    @Builder.Default
    private int cellSize = 40;

    private Image flagIcon;
    private Image mineIcon;

    private Runnable onGameWon;
    private Runnable onGameLost;

    private ModelBuilder modelBuilder;
    private ViewBuilder viewBuilder ;

    public Node instance() {
        defaultIconFallback();
        defaultBuilderFallback();
        final Game game = modelBuilder.build();
        new GameController(game, onGameWon, onGameLost);
        return viewBuilder.buildView(game);
    }

    private void defaultIconFallback() {
        if (flagIcon == null)
            flagIcon = new Image("/flag.png", cellSize, cellSize, true, true);
        if (mineIcon == null)
            mineIcon = new Image("/flag.png", cellSize, cellSize, true, true);
    }

    private void defaultBuilderFallback() {
        if (modelBuilder == null)
            modelBuilder = new DefaultModelBuilder(width, height, mineCount);
        if (viewBuilder == null)
            viewBuilder = new ViewBuilder(cellSize, flagIcon, mineIcon);
    }
}
