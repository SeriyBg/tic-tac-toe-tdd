package mob.tdd;

import java.util.Arrays;

import static mob.tdd.Classic2DBoard.CellValue.EMPTY;

public class Classic2DBoard implements Board {

    private final CellValue[][] boardState;

    public Classic2DBoard() {
        boardState = new CellValue[][] {
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };
    }

    @Override
    public CellValue[][] getState() {
        CellValue[][] boardStateCopy = new CellValue[3][3];
        for(int i = 0; i< boardState.length; i++) {
            boardStateCopy[i] = Arrays.copyOf(boardState[i], boardState[i].length);
        }
        return boardStateCopy;
    }

    @Override
    public void changeCellValue(Coordinate coordinate, CellValue cellValue) {
        final int row = coordinate.getRow() - 1;
        final int column = coordinate.getColumn() - 1;
        if (!boardState[row][column].equals(CellValue.EMPTY)) {
            throw new IllegalArgumentException("You should not be able to override not EMPTY cell");
        }
        boardState[row][column] = cellValue;
    }

    @Override
    public boolean hasDrawnLineFor(CellValue cellValue) {
        final CellValue[] line = {cellValue, cellValue, cellValue};
        return checkOrthogonalLines(line) || checkDiagonalLines(line);
    }

    private boolean checkDiagonalLines(CellValue[] line) {
        CellValue[] diagonalLine = new CellValue[3];
        int j = 0;
        for(int i = 0; i < boardState.length; i++) {
            diagonalLine[i] = boardState[i][j++];
        }
        if(Arrays.equals(diagonalLine, line)) {
            return true;
        } else {
            diagonalLine = new CellValue[3];
            j = 2;
            for(int i = 0; i < boardState.length; i++) {
                diagonalLine[i] = boardState[i][j--];
            }
        }
        return Arrays.equals(diagonalLine, line);
    }

    private boolean checkOrthogonalLines(CellValue[] line) {
        for (int j = 0; j < 3; j++) {
            CellValue[] row = boardState[j];
            if(Arrays.equals(row, line)) {
                return true;
            }

            CellValue[] column = new CellValue[3];
            for (int i = 0; i < 3; i++) {
                column[i] = boardState[i][j];
            }
            if (Arrays.equals(column, line)) {
                return true;
            }
        }
        return false;
    }

    public enum CellValue {
        X, O, EMPTY
    }

}
