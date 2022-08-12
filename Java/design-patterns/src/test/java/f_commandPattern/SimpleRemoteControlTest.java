package f_commandPattern;

import f_commandPattern.a_simpleCommandPattern.SimpleRemoteControl;
import f_commandPattern.a_simpleCommandPattern.command.GarageDoorOnCommand;
import f_commandPattern.a_simpleCommandPattern.command.LightOnCommand;
import f_commandPattern.object.GarageDoor;
import f_commandPattern.object.Light;
import org.junit.jupiter.api.Test;

public class SimpleRemoteControlTest {

    @Test
    void RemoteControlTest() {
        SimpleRemoteControl remote = new SimpleRemoteControl();
        Light light = new Light("거실등");

        remote.setCommand(new LightOnCommand(light));
        remote.buttonWasPressed();

        remote.setCommand(new GarageDoorOnCommand(new GarageDoor()));
        remote.buttonWasPressed();
    }


}
