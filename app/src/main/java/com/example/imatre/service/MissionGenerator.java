package com.example.imatre.service;

import com.example.imatre.model.Exercise;
import com.example.imatre.model.Mission;
import com.example.imatre.util.DateUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MissionGenerator {

    private final Random random = new Random();
    private final CalorieCalculator calorieCalculator = new CalorieCalculator();
    private final DateUtils dateUtils = new DateUtils();
    private final List<Exercise> exercises = Arrays.asList(
            new Exercise("スクワット", 20, 0.0, "回"),
            new Exercise("腕立て", 10, 0.0, "回"),
            new Exercise("腹筋", 15, 0.0, "回"),
            new Exercise("プランク", 30, 0.0, "秒")
    );

    public Mission generateMission() {
        Exercise exercise = exercises.get(random.nextInt(exercises.size()));
        double estimatedCalories = calorieCalculator.calculateCalories(
                exercise.getName(),
                exercise.getBaseCount()
        );
        long startTime = dateUtils.getCurrentTimeMillis();
        long deadlineTime = dateUtils.getDeadlineTimeMillis(startTime);

        return new Mission(
                exercise.getName(),
                exercise.getBaseCount(),
                exercise.getUnit(),
                startTime,
                deadlineTime,
                "ACTIVE",
                estimatedCalories,
                0
        );
    }
}
