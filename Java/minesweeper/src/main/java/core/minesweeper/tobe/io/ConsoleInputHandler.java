package core.minesweeper.tobe.io;

import java.util.Scanner;

public class ConsoleInputHandler {
    private static final Scanner SCANNER = new Scanner(System.in);

    public String getUserInput() {
        return SCANNER.nextLine();
    }
}
