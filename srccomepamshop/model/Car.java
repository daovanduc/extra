package com.epam.dao.task1.entity;

import java.util.Objects;

public class Car extends Vehicle {

    private int wheel;
    private int door;
    private int seat;


    public Car(String name, int year, int brand, double speed) {
        super(name, year, brand, speed);
    }

    public int getWheel() {
        return wheel;
    }

    public void setWheel(int wheel) {
        this.wheel = wheel;
    }

    public int getDoor() {
        return door;
    }

    public void setDoor(int door) {
        this.door = door;
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
        Car car = (Car) o;
        return wheel == car.wheel &&
                door == car.door &&
                seat == car.seat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wheel, door, seat);
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheel=" + wheel +
                ", door=" + door +
                ", seat=" + seat +
                '}';
    }
}
