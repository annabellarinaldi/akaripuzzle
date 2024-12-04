package com.comp301.a09akari.view;

import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import com.comp301.a09akari.controller.AlternateMvcController;

public class ControlView implements FXComponent{
    private final AlternateMvcController controller;

    public ControlView(AlternateMvcController controller) {
        this.controller = controller;
    }

    @Override
    public Parent render() {
        HBox controlBox = new HBox();
        controlBox.setSpacing(10);
        controlBox.setAlignment(Pos.CENTER);

        Button prevButton = new Button("Previous Puzzle");
        prevButton.setOnAction(e -> controller.clickPrevPuzzle());

        Button nextButton = new Button("Next Puzzle");
        nextButton.setOnAction(e -> controller.clickNextPuzzle());

        Button randomButton = new Button("Random Puzzle");
        randomButton.setOnAction(e -> controller.clickRandPuzzle());

        Button resetButton = new Button("Reset Puzzle");
        resetButton.setOnAction(e -> controller.clickResetPuzzle());

        controlBox.getChildren().addAll(prevButton, randomButton, nextButton, resetButton);
        return controlBox;
    }
}
