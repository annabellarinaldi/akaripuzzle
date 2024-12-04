package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelImpl implements Model{
    private final PuzzleLibrary library;
    private final List<ModelObserver> observers;
    private Set<Position> lamps;
    private int activePuzzleIndex;

    public ModelImpl(PuzzleLibrary library) {
        if (library == null || library.size() == 0) {
            throw new IllegalArgumentException();
        }
        this.library = library;
        this.observers = new ArrayList<>();
        this.lamps = new HashSet<>();
        this.activePuzzleIndex = 0;
    }

    @Override
    public void addLamp(int r, int c) {
        checkCorridorCell(r, c);
        Position position = new Position(r, c);
        if (lamps.contains(position)) {
            return;
        }
        lamps.add(position);
        notifyObservers();
    }

    @Override
    public void removeLamp(int r, int c) {
        checkCorridorCell(r, c);
        Position position = new Position(r, c);
        lamps.remove(position);
        notifyObservers();
    }

    @Override
    public boolean isLit(int r, int c) {
        checkCorridorCell(r, c);
        Puzzle puzzle = getActivePuzzle();
        if (isLamp(r, c)) {
            return true;
        }
        for (Position lamp : lamps) {
            if (lamp.r == r || lamp.c == c) {
                if (isLineLitByLamp(lamp.r, lamp.c, r, c, puzzle)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isLamp(int r, int c) {
        checkCorridorCell(r, c);
        return lamps.contains(new Position(r, c));
    }

    @Override
    public boolean isLampIllegal(int r, int c) {
        if (!isLamp(r, c)) {
            throw new IllegalArgumentException();
        }
        Puzzle puzzle = getActivePuzzle();
        for (Position lamp : lamps) {
            if ((lamp.r == r || lamp.c == c) && !lamp.equals(new Position(r, c))) {
                if (isLineLitByLamp(lamp.r, lamp.c, r, c, puzzle)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Puzzle getActivePuzzle() {
        return library.getPuzzle(activePuzzleIndex);
    }

    @Override
    public int getActivePuzzleIndex() {
        return activePuzzleIndex;
    }

    @Override
    public void setActivePuzzleIndex(int index) {
        if (index < 0 || index >= library.size()) {
            throw new IndexOutOfBoundsException();
        }
        activePuzzleIndex = index;
        lamps = new HashSet<>();
        notifyObservers();
    }

    @Override
    public int getPuzzleLibrarySize() {
        return library.size();
    }

    @Override
    public void resetPuzzle() {
        lamps = new HashSet<>();
        notifyObservers();
    }

    @Override
    public boolean isSolved() {
        Puzzle puzzle = getActivePuzzle();
        for (int r = 0; r < puzzle.getHeight(); r++) {
            for (int c = 0; c < puzzle.getWidth(); c++) {
                CellType cellType = puzzle.getCellType(r, c);
                if (cellType == CellType.CORRIDOR && !isLit(r, c)) {
                    return false;
                }
                if (cellType == CellType.CLUE && !isClueSatisfied(r, c)) {
                    return false;
                }
            }
        }
        return lamps.stream().noneMatch(lamp -> isLampIllegal(lamp.r, lamp.c));
    }

    @Override
    public boolean isClueSatisfied(int r, int c) {
        if (getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
            throw new IllegalArgumentException();
        }
        int requiredLamps = getActivePuzzle().getClue(r, c);
        int count = 0;
        for (Position lamp: lamps) {
            if (Math.abs(lamp.r -r) + Math.abs(lamp.c - c) == 1) {
                count ++;
            }
        }
        return count == requiredLamps;
    }

    @Override
    public void addObserver(ModelObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(ModelObserver observer){
        observers.remove(observer);
    }

    private void notifyObservers(){
        for (ModelObserver observer: observers) {
            observer.update(this);
        }
    }

    private void checkCorridorCell(int r, int c) {
        Puzzle puzzle = getActivePuzzle();
        if (r < 0 || r >= puzzle.getHeight() || c < 0 || c >= puzzle.getWidth()) {
            throw new IndexOutOfBoundsException();
        }
        if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isLineLitByLamp(int lampR, int lampC, int r, int c, Puzzle puzzle) {
        if (lampR == r) {
            for (int col = Math.min(lampC, c) + 1; col < Math.max(lampC, c); col++) {
                if (puzzle.getCellType(r, col) == CellType.WALL || puzzle.getCellType(r, col) == CellType.CLUE) {
                    return false;
                }
            }
        } else if (lampC == c) {
            for (int row = Math.min(lampR, r) + 1; row < Math.max(lampR, r); row++) {
                if (puzzle.getCellType(row, c) == CellType.WALL || puzzle.getCellType(row, c) == CellType.CLUE) {
                    return false;
                }
            }
        }
        return true;
    }

    private static class Position {
        final int r;
        final int c;

        Position(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Position)) {
                return false;
            }
            Position position = (Position) o;
            return r == position.r && c == position.c;
        }

        public int hashCode() {
            return 31 * r + c;
        }
    }
}
