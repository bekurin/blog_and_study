package f_commandPattern.b_metaCommandPattern.command;

import f_commandPattern.Command;
import f_commandPattern.object.Stereo;

public class StereoOffCommand implements Command {
    private Stereo stereo;

    public StereoOffCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.volumeDown();
    }

    @Override
    public void undo() {
        stereo.volumeUp();
    }
}
