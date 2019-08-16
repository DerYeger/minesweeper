package eu.yeger.minesweeper;

import eu.yeger.minesweeper.controller.GameController;
import eu.yeger.minesweeper.model.Game;
import eu.yeger.minesweeper.model.DefaultModelBuilder;
import eu.yeger.minesweeper.model.ModelBuilder;
import eu.yeger.minesweeper.view.ViewBuilder;
import javafx.scene.Node;
import lombok.Builder;

@Builder
public class Minesweeper {

    @Builder.Default
    private int width = 10;
    @Builder.Default
    private int height = 10;
    @Builder.Default
    private int bombCount = 20;
    @Builder.Default
    private int cellSize = 40;
    @Builder.Default
    private String style = "/default.css";
    @Builder.Default
    private String flagImage = "/flag.png";
    @Builder.Default
    private String bombImage = "/bomb.png";

    private Runnable onGameWon;
    private Runnable onGameLost;

    private ModelBuilder modelBuilder;
    private ViewBuilder viewBuilder ;

    public Node instance() {
        defaultBuilderFallback();
        final Game game = modelBuilder.build();
        new GameController(game, onGameWon, onGameLost);
        return viewBuilder.buildView(game);
    }

    private void defaultBuilderFallback() {
        if (modelBuilder == null) modelBuilder = new DefaultModelBuilder(width, height, bombCount);
        if (viewBuilder == null) viewBuilder = new ViewBuilder(style, cellSize, flagImage, bombImage);
    }
}
