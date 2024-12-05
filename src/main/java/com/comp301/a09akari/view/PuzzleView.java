package com.comp301.a09akari.view;

import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;

public class PuzzleView implements FXComponent {
  private final AlternateMvcController controller;

  public PuzzleView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(5);
    grid.setVgap(5);

    int rows = controller.getActivePuzzle().getHeight();
    int cols = controller.getActivePuzzle().getWidth();

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Button cellButton = new Button();
        cellButton.setMinSize(40, 40);
        CellType cellType = controller.getActivePuzzle().getCellType(r, c);

        switch (cellType) {
          case CORRIDOR:
            if (controller.isLamp(r, c)) {
              cellButton.setText("💡");
            } else if (controller.isLit(r, c)) {
              cellButton.setStyle("-fx-background-color: yellow;");
            } else {
              cellButton.setStyle("-fx-background-color: white;");
            }
            int finalR = r;
            int finalC = c;
            cellButton.setOnAction(e -> controller.clickCell(finalR, finalC));
            break;

          case CLUE:
            cellButton.setText(String.valueOf(controller.getActivePuzzle().getClue(r, c)));
            cellButton.setStyle("-fx-background-color: lightgray;");
            break;

          case WALL:
            cellButton.setStyle("-fx-background-color: gray;");
            break;
        }

        grid.add(cellButton, c, r);
      }
    }
    return grid;
  }
}
