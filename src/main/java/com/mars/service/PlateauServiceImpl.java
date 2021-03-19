package com.mars.service;

import com.mars.model.Plateau;
import com.mars.model.PlateauField;
import com.mars.model.Rover;
import com.mars.singleton.Mars;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlateauServiceImpl implements PlateauService {
    Mars mars = Mars.getInstance();

    @Override
    public Plateau createPlateau(Integer width, Integer height) {
        Plateau plateau = new Plateau(height, width);
        mars.setHeight(height);
        mars.setWidth(width);
        mars.setPlateau(plateau);
        List<Rover> rovers = new ArrayList<>();
        mars.setRovers(rovers);
        return mars.getPlateau();
    }

    @Override
    public Plateau readPlateau() {
        return mars.getPlateau();
    }

    @Override
    public String printPlateau() {
        Plateau plateau = mars.getPlateau();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < mars.getHeight(); i++) {
            for (int j = 0; j < mars.getWidth(); j++) {
                if (plateau.getPlateau()[i][j].getRover() != null) {
                    output.append("[").append(plateau.getPlateau()[i][j].getRover().getOrientation().toString().substring(0,1)).append("]");
                } else {
                    output.append("[-]");
                }
            }
            output.append("\n");
        }
        return output.toString();
    }

    @Override
    public PlateauField readPlateauField(Integer x, Integer y) {
        Plateau plateau = mars.getPlateau();
        return plateau.getPlateau()[x][y];
    }

    @Override
    public PlateauField updatePlateauField(Integer x, Integer y, Rover rover) {
        Plateau plateau = mars.getPlateau();
        PlateauField plateauField = plateau.getPlateau()[x][y];
        plateauField.setRover(rover);
        plateau.getPlateau()[x][y] = plateauField;
        mars.setPlateau(plateau);
        return plateauField;
    }
}
