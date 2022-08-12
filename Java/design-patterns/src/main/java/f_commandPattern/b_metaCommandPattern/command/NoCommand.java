package f_commandPattern.b_metaCommandPattern.command;

import f_commandPattern.Command;

public class NoCommand implements Command {
    @Override
    public void execute() {
        System.out.println("아무런 명령도 실행하지 않는 버튼입니다...");
    }

    @Override
    public void undo() {
        System.out.println("아무런 명령도 실행하지 않는 버튼입니다...");
    }
}
