package com.example.imatre.model;

public class Mission {

    private String exerciseName;
    private int targetCount;
    private String unit;
    private long startTime;
    private long deadlineTime;
    private String status;
    private double estimatedCalories;
    private int userFeeling;

    public Mission(
            String exerciseName,
            int targetCount,
            String unit,
            long startTime,
            long deadlineTime,
            String status,
            double estimatedCalories,
            int userFeeling
    ) {
        this.exerciseName = exerciseName;
        this.targetCount = targetCount;
        this.unit = unit;
        this.startTime = startTime;
        this.deadlineTime = deadlineTime;
        this.status = status;
        this.estimatedCalories = estimatedCalories;
        this.userFeeling = userFeeling;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(long deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getEstimatedCalories() {
        return estimatedCalories;
    }

    public void setEstimatedCalories(double estimatedCalories) {
        this.estimatedCalories = estimatedCalories;
    }

    public int getUserFeeling() {
        return userFeeling;
    }

    public void setUserFeeling(int userFeeling) {
        this.userFeeling = userFeeling;
    }
}
