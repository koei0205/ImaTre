package com.example.imatre.service;

public class CalorieCalculator {

    public double calculateCalories(String exerciseName, int count) {
        switch (exerciseName) {
            case "スクワット":
                return count * 0.3;
            case "腕立て":
                return count * 0.4;
            case "腹筋":
                return count * 0.25;
            case "プランク":
                return count * (2.0 / 30.0);
            default:
                return 0.0;
        }
    }
}
