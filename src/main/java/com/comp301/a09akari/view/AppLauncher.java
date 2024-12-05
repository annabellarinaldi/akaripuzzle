package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.PuzzleLibraryImpl;
import com.comp301.a09akari.SamplePuzzles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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

    BorderPane root = new BorderPane();
    root.setCenter(puzzleView.render());
    root.setBottom(controlView.render());
    root.setTop(messageView.render());

    model.addObserver(
        updatedModel -> {
          root.setCenter(puzzleView.render());
          root.setBottom(controlView.render());
          root.setTop(messageView.render());
        });

    Scene scene = new Scene(root, 600, 800);
    stage.setScene(scene);
    stage.setTitle("Akari Puzzle Game");
    stage.show();
  }
}
