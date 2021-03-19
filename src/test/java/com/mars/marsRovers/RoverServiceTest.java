package com.mars.marsRovers;

import com.mars.enums.Orientation;
import com.mars.model.Plateau;
import com.mars.model.Rover;
import com.mars.service.RoverServiceImpl;
import com.mars.singleton.Mars;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RoverServiceTest  {
    @Autowired
    RoverServiceImpl roverService;

    @Test
    public void createRover_happyPath() throws Exception {
        Plateau plateau = new Plateau(5, 5);
        Mars mars = Mars.getInstance();
        mars.setPlateau(plateau);

        Rover mockRover = new Rover(0, 0, 0, Orientation.EAST);


        String[] command = new String[5];
        command[0] = "0";
        command[1] = "0";
        command[2] = "E";


        Rover createdRover = roverService.createRover(command);

        assertEquals(mockRover, createdRover);
    }


    @Test
    public void createRover_badCommand_thenNumberFormatException() {
        Plateau plateau = new Plateau(5, 5);
        Mars mars = Mars.getInstance();
        mars.setPlateau(plateau);

        String[] command = new String[5];
        command[0] = "ESsAD";
        command[1] = "0";
        command[2] = "E";

        assertThrows(NumberFormatException.class, () -> {
            roverService.createRover(command);
        });
    }
}
