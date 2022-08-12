package f_commandPattern.b_metaCommandPattern;

import f_commandPattern.Command;
import f_commandPattern.b_metaCommandPattern.command.NoCommand;

public class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command undoCommand;

    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void undoButtonWasPushed() {
        undoCommand.undo();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n--------리모컨--------\n");
        for (int i = 0; i < onCommands.length; i++) {
            buffer.append("[slot " + i + "] " + onCommands[i].getClass().getSimpleName() + ", " + offCommands[i].getClass().getSimpleName() + "\n");
        }
        buffer.append("[undo] " + undoCommand.getClass().getSimpleName());
        return buffer.toString();
    }
}
