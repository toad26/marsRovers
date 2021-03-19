package com.mars.service;

import com.mars.model.Plateau;
import com.mars.model.PlateauField;
import com.mars.model.Rover;

public interface PlateauService {
    Plateau createPlateau(Integer height, Integer width);

    Plateau readPlateau();

    PlateauField readPlateauField(Integer x, Integer y);

    PlateauField updatePlateauField(Integer x, Integer y, Rover rover);

    String printPlateau();
}
