package m_proxyPattern;

import l_statePattern.GumballMachine;
import org.junit.jupiter.api.Test;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ProxyPatternTest {

    @Test
    void gumballMonitorTest() throws RemoteException {
        GumballMachine gumballMachine = new GumballMachine("seoul", 112);
        GumballMonitor monitor = new GumballMonitor(gumballMachine);

        gumballMachine.insertQuarter();
        monitor.report();
        gumballMachine.turnCrank();
        monitor.report();
    }

    @Test
    void gumballMonitorRemoteTest() {
        String[] location = {"rmi://seoul.com/", "rmi://busan.com", "rmi://suwon.com"};
        GumballMonitor[] monitor = new GumballMonitor[location.length];

        for (int i = 0; i < location.length; i++) {
            try {
                GumballMachineRemote machine = (GumballMachineRemote) Naming.lookup(location[i]);
                monitor[i] = new GumballMonitor(machine);
                System.out.println(monitor[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < location.length; i++) {
            monitor[i].report();
        }
    }
}
