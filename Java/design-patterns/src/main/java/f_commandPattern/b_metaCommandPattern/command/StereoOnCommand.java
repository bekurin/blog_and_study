package f_commandPattern.b_metaCommandPattern.command;

import f_commandPattern.Command;
import f_commandPattern.object.Stereo;

public class StereoOnCommand implements Command {
    private Stereo stereo;

    public StereoOnCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.volumeUp();
    }

    @Override
    public void undo() {
        stereo.volumeDown();
    }
}
