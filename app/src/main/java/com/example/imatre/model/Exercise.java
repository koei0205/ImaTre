package com.example.imatre.model;

public class Exercise {

    private final String name;
    private final int baseCount;
    private final double caloriesPerCount;
    private final String unit;

    public Exercise(String name, int baseCount, double caloriesPerCount, String unit) {
        this.name = name;
        this.baseCount = baseCount;
        this.caloriesPerCount = caloriesPerCount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public int getBaseCount() {
        return baseCount;
    }

    public double getCaloriesPerCount() {
        return caloriesPerCount;
    }

    public String getUnit() {
        return unit;
    }
}
