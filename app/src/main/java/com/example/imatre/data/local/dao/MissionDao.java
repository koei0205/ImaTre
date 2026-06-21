package com.example.imatre.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.imatre.data.local.entity.MissionEntity;

import java.util.List;

@Dao
public interface MissionDao {

    @Insert
    long insert(MissionEntity mission);

    @Update
    void update(MissionEntity mission);

    @Query("SELECT * FROM missions ORDER BY startTime DESC")
    List<MissionEntity> getAll();

    @Query("SELECT * FROM missions WHERE startTime BETWEEN :startOfDay AND :endOfDay ORDER BY startTime DESC")
    List<MissionEntity> getTodayMissions(long startOfDay, long endOfDay);
}
