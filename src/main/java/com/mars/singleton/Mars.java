package com.mars.singleton;

import com.mars.model.Plateau;
import com.mars.model.Rover;

import java.util.ArrayList;
import java.util.List;

public class Mars {
    private static Mars instance;

    private Plateau plateau;
    private Integer width;
    private Integer height;
    private List<Rover> rovers = new ArrayList();

    private Mars() {
    }

    public static Mars getInstance() {
        if(instance == null) {
            instance = new Mars();
        }

        return instance;
    }

    public static void setInstance(Mars instance) {
        Mars.instance = instance;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public List<Rover> getRovers() {
        return rovers;
    }

    public void setRovers(List<Rover> rovers) {
        this.rovers = rovers;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
