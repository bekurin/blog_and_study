package l_statePattern;

import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

public class StatePatternTest {

    @Test
    void gumballMachineTest() throws RemoteException {
        GumballMachine gumballMachine = new GumballMachine("seoul", 5);
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
    void notEnoughQuarterTest() throws RemoteException {
        GumballMachine gumballMachine = new GumballMachine("seoul", 50);

        for (int i = 0; i < 50; i++) {
            gumballMachine.insertQuarter();
            gumballMachine.turnCrank();
        }
    }
}
