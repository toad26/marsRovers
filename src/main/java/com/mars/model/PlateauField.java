package com.mars.model;

import java.util.Objects;

public class PlateauField {
    private Integer x;
    private Integer y;
    private Rover rover;
    private boolean visited;

    public PlateauField(Integer x, Integer y, Rover rover) {
        this.x = x;
        this.y = y;
        this.rover = rover;
        this.visited = false;
    }

    public PlateauField(Integer x, Integer y, Rover rover, boolean visited) {
        this.x = x;
        this.y = y;
        this.rover = rover;
        this.visited = visited;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return "PlateauField{" +
                "x=" + x +
                ", y=" + y +
                ", rover=" + rover +
                ", visited=" + visited +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlateauField that = (PlateauField) o;
        return visited == that.visited &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(rover, that.rover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, rover, visited);
    }
}
