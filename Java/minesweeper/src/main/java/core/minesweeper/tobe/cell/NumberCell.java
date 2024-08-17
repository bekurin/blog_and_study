package core.minesweeper.tobe.cell;

public class NumberCell extends Cell {
    private final int nearbyLandMineCount;

    public NumberCell(int count) {
        this.nearbyLandMineCount = count;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public String getSign() {
        if (isOpened()) {
            return String.valueOf(nearbyLandMineCount);
        }
        if (isFlagged) {
            return FLAG_SIGN;
        }
        return UNCHECKED_SIGN;
    }
}
