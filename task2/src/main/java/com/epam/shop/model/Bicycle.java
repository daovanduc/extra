package com.epam.shop.model;

public class Bicycle extends Vehicle {

    private int wheel;
    private int seat;

    public Bicycle(String name, int year, String brand, double speed) {
        super(name, year, brand, speed);
    }
}
