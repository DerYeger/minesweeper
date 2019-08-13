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

    public Node instance() {
        final Game game = new ModelBuilder().buildGame(width, height, bombCount);
        new GameController(game, onGameWon, onGameLost);
        return new ViewBuilder(style, cellSize)
                .buildView(game);
    }
}
