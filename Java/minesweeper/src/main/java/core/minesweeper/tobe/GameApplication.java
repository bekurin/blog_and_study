package core.minesweeper.tobe;

import core.minesweeper.tobe.gamelevel.GameLevel;
import core.minesweeper.tobe.gamelevel.VeryBeginner;

public class GameApplication {
    public static void main(String[] args) {
        GameLevel gameLevel = new VeryBeginner();
        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.initialize();
        minesweeper.run();
    }
}
