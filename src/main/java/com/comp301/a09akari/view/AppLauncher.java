package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.PuzzleLibraryImpl;
import com.comp301.a09akari.SamplePuzzles;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibraryImpl library = new PuzzleLibraryImpl();
    library.addPuzzle(new com.comp301.a09akari.model.PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new com.comp301.a09akari.model.PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new com.comp301.a09akari.model.PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new com.comp301.a09akari.model.PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new com.comp301.a09akari.model.PuzzleImpl(SamplePuzzles.PUZZLE_05));

    Model model = new ModelImpl(library);
    AlternateMvcController controller = new ControllerImpl(model);

    PuzzleView puzzleView = new PuzzleView(controller);
    ControlView controlView = new ControlView(controller);
    MessageView messageView = new MessageView(controller);

    Label indexLabel = new Label();
    indexLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px;");
    updateIndexLabel(indexLabel, controller);

    VBox topBox = new VBox();
    topBox.setSpacing(5); // Add spacing between components
    topBox.setAlignment(Pos.CENTER);
    topBox.getChildren().addAll(messageView.render(), indexLabel);

    BorderPane root = new BorderPane();
    root.setCenter(puzzleView.render());
    root.setBottom(controlView.render());
    root.setTop(topBox);

    model.addObserver(
        updatedModel -> {
          updateIndexLabel(indexLabel, controller);
          topBox.getChildren().set(0, messageView.render());
          root.setCenter(puzzleView.render());
          root.setBottom(controlView.render());
        });

    Scene scene = new Scene(root, 600, 800);
    stage.setScene(scene);
    stage.setTitle("Akari");
    stage.show();
  }

  private void updateIndexLabel(Label label, AlternateMvcController controller) {
    label.setText(
        "Puzzle "
            + (controller.getActivePuzzleIndex() + 1)
            + " of "
            + controller.getPuzzleLibrarySize());
  }
}
