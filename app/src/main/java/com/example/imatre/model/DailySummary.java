package com.example.imatre.model;

public class DailySummary {

    private final String date;
    private final int clearCount;
    private final int missCount;
    private final double totalCalories;

    public DailySummary(String date, int clearCount, int missCount, double totalCalories) {
        this.date = date;
        this.clearCount = clearCount;
        this.missCount = missCount;
        this.totalCalories = totalCalories;
    }

    public String getDate() {
        return date;
    }

    public int getClearCount() {
        return clearCount;
    }

    public int getMissCount() {
        return missCount;
    }

    public double getTotalCalories() {
        return totalCalories;
    }
}
