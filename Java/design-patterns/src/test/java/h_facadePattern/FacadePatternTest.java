package h_facadePattern;

import h_facadePattern.HomeTheaterFacade;
import h_facadePattern.object.*;
import org.junit.jupiter.api.Test;

public class FacadePatternTest {

    @Test
    void homeTheaterTest() {
        //given
        Amplifier amp = new Amplifier("앰프");
        PopcornPopper popper = new PopcornPopper("팝콘 기계");
        StreamingPlayer player = new StreamingPlayer("스트리밍 플레이어", amp);
        Projector projector = new Projector("프로젝터", player);
        Screen screen = new Screen("스크린");
        TheaterLights lights = new TheaterLights("조명");
        Tuner tuner = new Tuner("터너", amp);

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(
                amp, tuner, player, projector, lights, screen, popper
        );

        homeTheater.watchMovie("토르: 러브앤썬더");
        homeTheater.endMovie();
    }
}
