package f_commandPattern;

import f_commandPattern.a_simpleCommandPattern.command.GarageDoorOnCommand;
import f_commandPattern.a_simpleCommandPattern.command.LightOnCommand;
import f_commandPattern.b_metaCommandPattern.RemoteControl;
import f_commandPattern.b_metaCommandPattern.command.*;
import f_commandPattern.object.CeilingFan;
import f_commandPattern.object.GarageDoor;
import f_commandPattern.object.Light;
import f_commandPattern.object.Stereo;
import org.junit.jupiter.api.Test;

public class MetaRemoteControlTest {

    @Test
    void remoteControlTest() {
        int slot = 0;
        Light light = new Light("주방등");
        GarageDoor garageDoor = new GarageDoor();
        Stereo stereo = new Stereo();
        CeilingFan ceilingFan = new CeilingFan("거실");

        RemoteControl remoteControl = new RemoteControl();
        remoteControl.setCommand(slot++, new LightOnCommand(light), new LightOffCommand(light));
        remoteControl.setCommand(slot++, new GarageDoorOnCommand(garageDoor), new GarageDoorOffCommand(garageDoor));
        remoteControl.setCommand(slot++, new StereoOnCommand(stereo), new StereoOffCommand(stereo));
        remoteControl.setCommand(slot++, new CeilingFanOnCommand(ceilingFan), new CeilingFanOffCommand(ceilingFan));

        for (int i = 0; i < slot; i++) {
            remoteControl.onButtonWasPushed(i);
            remoteControl.offButtonWasPushed(i);
            remoteControl.undoButtonWasPushed();
            System.out.println(remoteControl.toString() + "\n\n");
        }
    }
}
