package core.minesweeper.tobe.position;

import core.minesweeper.tobe.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {

    private final List<CellPosition> positions;

    private CellPositions(List<CellPosition> cellPositions) {
        this.positions = cellPositions;
    }

    public static CellPositions of(List<CellPosition> positions) {
        return new CellPositions(positions);
    }

    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositions.add(cellPosition);
            }
        }
        return of(cellPositions);
    }

    public List<CellPosition> extractRandomPositions(int count) {
        List<CellPosition> cellPositions = new ArrayList<>(positions);

        Collections.shuffle(cellPositions);
        return cellPositions.subList(0, count);
    }

    public List<CellPosition> subtract(List<CellPosition> positionListToSubtract) {
        List<CellPosition> cellPositions = new ArrayList<>(positions);
        CellPositions positionToSubtract = CellPositions.of(cellPositions);

        return cellPositions.stream()
                .filter(positionToSubtract::doesNotContain)
                .toList();
    }

    private boolean doesNotContain(CellPosition cellPosition) {
        return !positions.contains(cellPosition);
    }

    public List<CellPosition> getPositions() {
        return positions;
    }
}
