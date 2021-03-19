package com.mars.model;

import com.mars.enums.Orientation;

import java.util.Objects;

public class Rover {
    private Integer x;
    private Integer y;
    private Integer id;
    private Orientation orientation;

    public Rover(Integer x, Integer y, Integer id, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.orientation = orientation;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Rover{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", orientation=" + orientation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rover rover = (Rover) o;
        return Objects.equals(x, rover.x) &&
                Objects.equals(y, rover.y) &&
                Objects.equals(id, rover.id) &&
                orientation == rover.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, id, orientation);
    }
}
