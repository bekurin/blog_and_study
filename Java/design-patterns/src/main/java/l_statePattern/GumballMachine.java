package l_statePattern;

import l_statePattern.state.*;

public class GumballMachine {
    private State soldOutState;
    private State noQuarterState;
    private State hasQuarterState;
    private State soldState;
    private State winnerState;

    private State state = soldOutState;
    private String location;
    private int count = 0;

    public GumballMachine(String location, int count) {
        this.location = location;
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);
        winnerState = new WinnerState(this);

        this.count = count;
        if (count > 0) {
            state = noQuarterState;
        } else {
            state = soldOutState;
        }
    }

    public void insertQuarter() {
        state.insertQuarter();
    }

    public void ejectQuarter() {
        state.ejectQuarter();
    }

    public void turnCrank() {
        state.turnCrank();
        state.dispense();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void releaseBall() {
        System.out.println("알맹이를 내보내고 있습니다.");
        if (count > 0) {
            count -= 1;
        }
    }

    public int getCount() {
        return count;
    }

    public String getState() {
        if (hasQuarterState.equals(state)) {
            return "동전이 들어있습니다.";
        } else if (noQuarterState.equals(state)) {
            return "동전이 들어있지 않습니다.";
        } else if (soldOutState.equals(state)) {
            return "알맹이가 매진되었습니다.";
        } else if (soldState.equals(state)) {
            return "동전 투입 대기 중...";
        } else if (winnerState.equals(state)) {
            return "한번 더 당첨!!!";
        }
        return "지원하지 않는 상태입니다.";
    }

    public String getLocation() {
        return location;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getWinnerState() {
        return winnerState;
    }

    @Override
    public String toString() {
        return "GumballMachine{" +
                "state=" + state.getClass().getSimpleName() +
                ", count=" + count +
                '}';
    }
}
