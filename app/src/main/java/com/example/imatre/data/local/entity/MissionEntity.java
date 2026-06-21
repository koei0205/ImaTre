package com.example.imatre.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "missions")
public class MissionEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exerciseName;
    private int targetCount;
    private String unit;
    private String status;
    private long startTime;
    private long deadlineTime;
    private long clearedTime;
    private int difficultyLevel;
    private int userFeeling;
    private double estimatedCalories;

    public MissionEntity(
            String exerciseName,
            int targetCount,
            String unit,
            String status,
            long startTime,
            long deadlineTime,
            long clearedTime,
            int difficultyLevel,
            int userFeeling,
            double estimatedCalories
    ) {
        this.exerciseName = exerciseName;
        this.targetCount = targetCount;
        this.unit = unit;
        this.status = status;
        this.startTime = startTime;
        this.deadlineTime = deadlineTime;
        this.clearedTime = clearedTime;
        this.difficultyLevel = difficultyLevel;
        this.userFeeling = userFeeling;
        this.estimatedCalories = estimatedCalories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public long getClearedTime() {
        return clearedTime;
    }

    public void setClearedTime(long clearedTime) {
        this.clearedTime = clearedTime;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getUserFeeling() {
        return userFeeling;
    }

    public void setUserFeeling(int userFeeling) {
        this.userFeeling = userFeeling;
    }

    public double getEstimatedCalories() {
        return estimatedCalories;
    }

    public void setEstimatedCalories(double estimatedCalories) {
        this.estimatedCalories = estimatedCalories;
    }
}
