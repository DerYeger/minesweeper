package eu.yeger.minesweeper;

import eu.yeger.minesweeper.controller.GameController;
import eu.yeger.minesweeper.model.Game;
import eu.yeger.minesweeper.model.ModelBuilder;
import eu.yeger.minesweeper.view.ViewBuilder;
import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Minesweeper {

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_BOMB_COUNT = 10;
    public static final int DEFAULT_CELL_SIZE = 40;
    public static final String DEFAULT_STYLE = "/default.css";

    @Builder.Default
    private int width = DEFAULT_WIDTH;
    @Builder.Default
    private int height = DEFAULT_HEIGHT;
    @Builder.Default
    private int bombCount = DEFAULT_BOMB_COUNT;
    @Builder.Default
    private int cellSize = DEFAULT_CELL_SIZE;
    @Builder.Default
    private String style = DEFAULT_STYLE;

    private Runnable onGameWon;
    private Runnable onGameLost;

    private ModelBuilder modelBuilder;
    private ViewBuilder viewBuilder ;

    public Node instance() {
        initCheck();
        final Game game = modelBuilder.buildGame();
        new GameController(game, onGameWon, onGameLost);
        return viewBuilder.buildView(game);
    }

    private void initCheck() {
        if (modelBuilder == null) modelBuilder = new ModelBuilder(width, height, bombCount);
        if (viewBuilder == null) viewBuilder = new ViewBuilder(style, cellSize);
    }
}
