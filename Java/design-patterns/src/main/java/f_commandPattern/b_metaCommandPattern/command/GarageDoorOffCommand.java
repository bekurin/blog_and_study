package f_commandPattern.b_metaCommandPattern.command;

import f_commandPattern.Command;
import f_commandPattern.object.GarageDoor;

public class GarageDoorOffCommand implements Command {
    private GarageDoor garageDoor;

    public GarageDoorOffCommand(GarageDoor garage) {
        this.garageDoor = garage;
    }

    @Override
    public void execute() {
        garageDoor.down();
    }

    @Override
    public void undo() {
        garageDoor.up();
    }
}
