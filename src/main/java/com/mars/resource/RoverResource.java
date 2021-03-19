package com.mars.resource;

import com.mars.model.Rover;
import com.mars.service.RoverService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.mars.decoder.RoverDecoder.*;

@RestController
@RequestMapping(path = "/rover")
public class RoverResource {
    private final RoverService roverService;

    public RoverResource(RoverService roverService) {
        this.roverService = roverService;
    }

    @RequestMapping(value = "/create",
            method = RequestMethod.POST,
            consumes = {"text/plain"}, produces = { "application/json" })
    public Rover createRover(@RequestBody String command) throws Exception {
        String[] decoded = decodeRoverCreate(command);
        return roverService.createRover(decoded);
    }

    @RequestMapping(value = "/move",
            method = RequestMethod.POST,
            consumes = {"text/plain"}, produces = { "application/json" })
    public Rover moveRover(@RequestBody String command) throws Exception {
        String[] decoded = decodeRoverMove(command);
        return roverService.moveRover(decoded);
    }

    @RequestMapping(value = "/getDirections",
            method = RequestMethod.GET,
            produces = { "text/plain" })
    public String getDirection(@RequestBody String command) throws Exception {
        String[] decoded = decodeGetDirections(command);
        return roverService.getDirections(decoded);
    }

    @RequestMapping(value = "/printRoute",
            method = RequestMethod.GET,
            produces = { "text/plain" })
    public String printDirection(@RequestBody String command) throws Exception {
        String[] decoded = decodeGetDirections(command);
        return roverService.printRoute(decoded);
    }
}
