package core.minesweeper.tobe.cell;

public class CellState {

    private boolean isFlagged;
    private boolean isOpened;

    private CellState(boolean isFlagged, boolean isOpened) {
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    public static CellState initialize() {
        return new CellState(false, false);
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void open() {
        isOpened = true;
    }

    public void flag() {
        isFlagged = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }
}
