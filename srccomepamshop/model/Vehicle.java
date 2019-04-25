package com.epam.dao.task1.entity;

import java.util.Objects;

public abstract class Vehicle {

    private String name;
    private int year;
    private int brand;
    private double speed;


    public Vehicle(String name, int year, int brand, double speed) {
        super();
        this.name = name;
        this.year = year;
        this.brand = brand;
        this.speed = speed;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return year == vehicle.year &&
                brand == vehicle.brand &&
                Double.compare(vehicle.speed, speed) == 0 &&
                Objects.equals(name, vehicle.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, brand, speed);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", brand=" + brand +
                ", speed=" + speed +
                '}';
    }
}
