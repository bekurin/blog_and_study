package core.minesweeper.tobe;

import core.minesweeper.tobe.gamelevel.Beginner;
import core.minesweeper.tobe.gamelevel.GameLevel;
import core.minesweeper.tobe.io.ConsoleInputHandler;
import core.minesweeper.tobe.io.ConsoleOutputHandler;
import core.minesweeper.tobe.io.InputHandler;
import core.minesweeper.tobe.io.OutputHandler;

public class GameApplication {
    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner();
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        Minesweeper minesweeper = new Minesweeper(gameLevel, inputHandler, outputHandler);
        minesweeper.initialize();
        minesweeper.run();
    }
}
