package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

import java.util.Random;

public class ControllerImpl implements AlternateMvcController, ClassicMvcController {
  private final Model model;
  private final Random random;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.random = new Random();
  }

  @Override
  public void clickNextPuzzle() {
    int nextIndex = model.getActivePuzzleIndex() + 1;
    if (nextIndex >= model.getPuzzleLibrarySize()) {
      nextIndex = 0;
    }
    model.setActivePuzzleIndex(nextIndex);
  }

  @Override
  public void clickPrevPuzzle() {
    int prevIndex = model.getActivePuzzleIndex() - 1;
    if (prevIndex < 0) {
      prevIndex = model.getPuzzleLibrarySize() - 1;
    }
    model.setActivePuzzleIndex(prevIndex);
  }

  @Override
  public void clickRandPuzzle() {
    int currentIndex = model.getActivePuzzleIndex();
    int randomIndex;
    do {
      randomIndex = random.nextInt(model.getPuzzleLibrarySize());
    } while (randomIndex == currentIndex);
    model.setActivePuzzleIndex(randomIndex);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else {
      model.addLamp(r, c);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }

  @Override
  public int getActivePuzzleIndex() {
    return model.getActivePuzzleIndex();
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  public int getPuzzleLibrarySize() {
    return model.getPuzzleLibrarySize();
  }
}
