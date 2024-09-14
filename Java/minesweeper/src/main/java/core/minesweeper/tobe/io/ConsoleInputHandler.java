package core.minesweeper.tobe.io;

import core.minesweeper.tobe.BoardIndexConverter;
import core.minesweeper.tobe.position.CellPosition;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {
    private static final Scanner SCANNER = new Scanner(System.in);
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public String getUserInput() {
        return SCANNER.nextLine();
    }

    @Override
    public CellPosition getCellPositionFromUser() {
        String userInput = SCANNER.nextLine();

        int rowIndex = boardIndexConverter.getSelectedRowIndex(userInput);
        int colIndex = boardIndexConverter.getSelectedColIndex(userInput);
        return CellPosition.of(rowIndex, colIndex);
    }
}
