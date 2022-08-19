package l_statePattern;

import org.junit.jupiter.api.Test;

public class StatePatternTest {

    @Test
    void gumballMachineTest() {
        GumballMachine gumballMachine = new GumballMachine(5);
        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        System.out.println(gumballMachine);
        gumballMachine.turnCrank();
        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        System.out.println(gumballMachine);
        gumballMachine.turnCrank();
        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        System.out.println(gumballMachine);
        gumballMachine.turnCrank();
        System.out.println(gumballMachine);
    }

    @Test
    void notEnoughQuarterTest() {
        GumballMachine gumballMachine = new GumballMachine(50);

        for (int i = 0; i < 50; i++) {
            gumballMachine.insertQuarter();
            gumballMachine.turnCrank();
        }
    }
}
