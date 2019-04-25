package com.epam.dao.task1.entity;

import java.util.Objects;

public class Bicycle extends Vehicle {

    private int wheel;
    private int seat;


    public Bicycle(String name, int year, int brand, double speed) {
        super(name, year, brand, speed);
    }

    public int getWheel() {
        return wheel;
    }

    public void setWheel(int wheel) {
        this.wheel = wheel;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bicycle bicycle = (Bicycle) o;
        return wheel == bicycle.wheel &&
                seat == bicycle.seat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wheel, seat);
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "wheel=" + wheel +
                ", seat=" + seat +
                '}';
    }
}
