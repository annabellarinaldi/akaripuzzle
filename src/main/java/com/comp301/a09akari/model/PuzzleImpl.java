package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle{
    private final int[][] board;

    public PuzzleImpl(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0){
            throw new IllegalArgumentException();
        }
        this.board = board;
    }
    @Override
    public int getWidth() {
        return board[0].length;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public CellType getCellType(int r, int c) {
        checkCoordinates(r, c);
        int cellValue = board[r][c];
        switch (cellValue) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return CellType.CLUE;
            case 5:
                return CellType.WALL;
            case 6:
                return CellType.CORRIDOR;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getClue(int r, int c) {
        checkCoordinates(r, c);
        if (getCellType(r, c) != CellType.CLUE) {
            throw new IllegalArgumentException("Cell at (" + r + ", " + c + ") is not a CLUE cell");
        }
        return board[r][c];
    }

    private void checkCoordinates(int r, int c) {
        if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
            throw new IndexOutOfBoundsException();
        }
    }
}
