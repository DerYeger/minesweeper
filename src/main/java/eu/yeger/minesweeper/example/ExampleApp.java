package eu.yeger.minesweeper.example;

import eu.yeger.minesweeper.Minesweeper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class ExampleApp extends Application {

    private Scene scene;
    private Minesweeper minesweeper;

    @Override
    public void start(final Stage stage)  {
        minesweeper = Minesweeper
                .builder()
                .width(16)
                .height(9)
                .bombCount(15)
                .onGameWon(() -> showNewGameDialog("Game won!"))
                .onGameLost(() -> showNewGameDialog("Game lost!"))
                .build();

        scene = new Scene(newGame());
        stage.setScene(scene);
        stage.show();
    }

    private Parent newGame() {
        return (Parent) minesweeper.instance();
    }

    private void showNewGameDialog(final String title) {
        final Alert gameOverAlert = new Alert(Alert.AlertType.CONFIRMATION);
        gameOverAlert.setTitle(title);
        gameOverAlert.setHeaderText(null);
        gameOverAlert.setContentText("Start a new game?");

        final Optional<ButtonType> result = gameOverAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            scene.setRoot(newGame());
        } else {
            Platform.exit();
        }
    }
}
