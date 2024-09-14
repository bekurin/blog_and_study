package core.minesweeper.tobe.io;

import core.minesweeper.tobe.position.CellPosition;

public interface InputHandler {

    String getUserInput();

    CellPosition getCellPositionFromUser();
}
