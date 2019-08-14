package eu.yeger.minesweeper;

import eu.yeger.minesweeper.model.Cell;
import eu.yeger.minesweeper.model.Game;
import eu.yeger.minesweeper.model.DefaultModelBuilder;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IntegrationTests extends ApplicationTest {

    private Stage stage;

    private boolean won;
    private boolean lost;

    @Override
    public void start(final Stage stage)  {
        this.stage = stage;
        won = false;
        lost = false;
    }

    private void click(final int x,
                       final int y,
                       final MouseButton button) {
        clickOn(stage.getX() + x, stage.getY() + y, button);
    }

    private void setScene(final Scene scene) {
        Platform.runLater(() -> {
            stage.hide();
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        });
       WaitForAsyncUtils.waitForFxEvents();
    }

    private Game buildTestGameAlpha() {
        final Game game = new Game(2, 2);
        final Cell tl = new Cell(0, 0);
        final Cell tr = new Cell(1, 0);
        final Cell bl = new Cell(0, 1);
        final Cell br = new Cell(1, 1);
        tl.withNeighbors(Arrays.asList(tr, bl, br));
        game.withCells(Arrays.asList(tl, tr, bl, br));
        br.bomb.set(true);

        return game;
    }

    private Game buildTestGameBeta() {
        final Game game = new Game(2, 2);
        final Cell tl = new Cell(0, 0);
        final Cell tr = new Cell(1, 0);
        final Cell bl = new Cell(0, 1);
        final Cell br = new Cell(1, 1);
        tl.number.set(1);
        tr.number.set(1);
        bl.number.set(1);
        br.number.set(1);
        tl.withNeighbors(Arrays.asList(tr, bl, br));
        game.withCells(Arrays.asList(tl, tr, bl, br));
        br.bomb.set(true);

        return game;
    }

    private Game buildTestGameGamma() {
        final Game game = new Game(10, 1);
        for (int i = 0; i <= 9; i++) {
            final Cell cell = new Cell(i, 0);
            cell.setGame(game).number.set(i);
        }
        return game;
    }

    @Test
    public void testWin() {
        final Minesweeper minesweeper = Minesweeper
                .builder()
                .width(2)
                .height(2)
                .bombCount(0)
                .cellSize(30)
                .onGameWon(() -> won = true)
                .onGameLost(() -> lost = true)
                .modelBuilder(null)
                .viewBuilder(null)
                .style("/default.css")
                .build();
        setScene(new Scene((Parent) minesweeper.instance()));
        click(15, 15, MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(won);
        assertFalse(lost);
    }

    @Test
    public void testFlag() {
        final Minesweeper minesweeper = Minesweeper
                .builder()
                .width(1)
                .height(1)
                .bombCount(1)
                .cellSize(20)
                .onGameWon(() -> won = true)
                .onGameLost(() -> lost = true)
                .build();
        setScene(new Scene((Parent) minesweeper.instance()));
        clickOn(stage, MouseButton.SECONDARY);
        clickOn(stage, MouseButton.PRIMARY);
        assertFalse(won);
        assertFalse(lost);
        clickOn(stage, MouseButton.SECONDARY);
        clickOn(stage, MouseButton.PRIMARY);
        assertFalse(won);
        assertTrue(lost);
    }

    @Test
    public void testUnveiling() {
        final DefaultModelBuilder modelBuilder = mock(DefaultModelBuilder.class);
        when(modelBuilder.build()).thenReturn(buildTestGameAlpha());
        final Minesweeper minesweeper = Minesweeper
                .builder()
                .cellSize(30)
                .onGameWon(() -> won = true)
                .onGameLost(() -> lost = true)
                .modelBuilder(modelBuilder)
                .build();
        setScene(new Scene((Parent) minesweeper.instance()));
        click(15, 15, MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(won);
        assertFalse(lost);
    }

    @Test
    public void testUnveilingBlocked() {
        final DefaultModelBuilder modelBuilder = mock(DefaultModelBuilder.class);
        when(modelBuilder.build()).thenReturn(buildTestGameBeta());
        final Minesweeper minesweeper = Minesweeper
                .builder()
                .cellSize(30)
                .onGameWon(() -> won = true)
                .onGameLost(() -> lost = true)
                .modelBuilder(modelBuilder)
                .build();
        setScene(new Scene((Parent) minesweeper.instance()));
        click(15, 15, MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(won);
        assertFalse(lost);
    }

    @Test
    public void testNumbers() {
        final DefaultModelBuilder modelBuilder = mock(DefaultModelBuilder.class);
        when(modelBuilder.build()).thenReturn(buildTestGameGamma());
        final Minesweeper minesweeper = Minesweeper
                .builder()
                .cellSize(30)
                .onGameWon(() -> won = true)
                .onGameLost(() -> lost = true)
                .modelBuilder(modelBuilder)
                .build();
        setScene(new Scene((Parent) minesweeper.instance()));
        for (int i = 0; i <= 9; i++) {
            assertFalse(won);
            click(15 + 30 * i, 15, MouseButton.PRIMARY);
        }
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(won);
        assertFalse(lost);
        assertNotNull(lookup("#zero"));
        assertNotNull(lookup("#one"));
        assertNotNull(lookup("#two"));
        assertNotNull(lookup("#three"));
        assertNotNull(lookup("#four"));
        assertNotNull(lookup("#five"));
        assertNotNull(lookup("#six"));
        assertNotNull(lookup("#seven"));
        assertNotNull(lookup("#eight"));
        assertNotNull(lookup("#null"));
    }
}
