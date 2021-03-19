package com.mars.model;

import java.util.Arrays;

public class Plateau {
    private PlateauField[][] plateau;

    public Plateau(Integer height, Integer width) {
        this.plateau = new PlateauField[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.plateau[i][j] = new PlateauField(i, j, null);
            }
        }
    }

    public PlateauField[][] getPlateau() {
        return plateau;
    }

    public void setPlateau(PlateauField[][] plateau) {
        this.plateau = plateau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plateau plateau1 = (Plateau) o;
        return Arrays.equals(plateau, plateau1.plateau);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(plateau);
    }

    @Override
    public String toString() {
        return "Plateau{" +
                "plateau=" + Arrays.toString(plateau) +
                '}';
    }
}
