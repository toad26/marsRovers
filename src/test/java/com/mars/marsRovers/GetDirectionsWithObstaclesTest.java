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

import java.util.ArrayList;
import java.util.List;

import static com.mars.decoder.RoverDecoder.decodeGetDirections;
import static com.mars.decoder.RoverDecoder.decodeRoverMove;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDirectionsWithObstaclesTest {
    @Autowired
    RoverServiceImpl roverService;


    @Test
    public void getDirections_happyPath() throws Exception {
        Plateau plateau = new Plateau(3, 3);
        Mars mars = Mars.getInstance();
        mars.setWidth(3);
        mars.setHeight(3);
        mars.setPlateau(plateau);

        Rover obstacleRover1 = new Rover(0, 1, 0, Orientation.NORTH);
        Rover obstacleRover2 = new Rover(1, 1, 1, Orientation.EAST);
        Rover roverToMove = new Rover(0, 2, 2, Orientation.NORTH);

        List<Rover> roverList = new ArrayList<>();
        roverList.add(obstacleRover1);
        roverList.add(obstacleRover2);
        roverList.add(roverToMove);

        mars.setRovers(roverList);

        String command = "2 0 0";
        String[] decodedCommand = decodeGetDirections(command);
        String response = roverService.getDirections(decodedCommand);

        //moving to check if rover goes to desired position with command from service



        String moveCommand = "2 " + response;
        String[] decodedMoveCommand = decodeRoverMove(moveCommand);
        Rover movedRover = roverService.moveRover(decodedMoveCommand);

        assertEquals("0", movedRover.getX().toString());
        assertEquals("0", movedRover.getY().toString());
    }
}
