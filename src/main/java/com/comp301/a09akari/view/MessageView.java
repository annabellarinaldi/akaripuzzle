package com.comp301.a09akari.view;

import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.comp301.a09akari.controller.AlternateMvcController;

public class MessageView implements FXComponent {
  private final AlternateMvcController controller;

  public MessageView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox messageBox = new VBox();
    messageBox.setAlignment(Pos.CENTER);

    if (controller.isSolved()) {
      Label messageLabel = new Label("Congratulations! You solved the puzzle!");
      messageLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: green;");
      messageBox.getChildren().add(messageLabel);
    }

    return messageBox;
  }
}
