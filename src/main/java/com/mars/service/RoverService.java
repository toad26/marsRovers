package com.mars.service;

import com.mars.model.Rover;

public interface RoverService {
    Rover createRover(String[] command) throws Exception;

    Rover moveRover(String[] command) throws Exception;

    String getDirections(String[] command) throws Exception;

    String printRoute(String[] command) throws Exception;
}
