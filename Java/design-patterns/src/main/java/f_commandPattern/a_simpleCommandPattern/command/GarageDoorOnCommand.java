package f_commandPattern.a_simpleCommandPattern.command;

import f_commandPattern.Command;
import f_commandPattern.object.GarageDoor;

public class GarageDoorOnCommand implements Command {
    private GarageDoor garageDoor;

    public GarageDoorOnCommand(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute() {
        garageDoor.up();
    }

    @Override
    public void undo() {
        garageDoor.down();
    }
}
