package m_proxyPattern;

import l_statePattern.GumballMachine;

import java.rmi.RemoteException;

public class GumballMonitor {
    private GumballMachineRemote gumballMachine;

    public GumballMonitor(GumballMachineRemote gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void report() {
        try {
            System.out.println("----------------------------------------");
            System.out.println("뽑기를 뽑은 위치: " + gumballMachine.getLocation());
            System.out.println("현재 재고 상태: " + gumballMachine.getCount());
            System.out.println("현재 상태: " + gumballMachine.getState());
            System.out.println("----------------------------------------");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
