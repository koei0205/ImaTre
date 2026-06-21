package com.example.imatre.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.imatre.data.local.AppDatabase;
import com.example.imatre.data.local.dao.MissionDao;
import com.example.imatre.data.local.entity.MissionEntity;
import com.example.imatre.model.Mission;
import com.example.imatre.util.DateUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MissionRepository {

    private final MissionDao missionDao;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    public MissionRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        missionDao = database.missionDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface MissionListCallback {
        void onResult(List<MissionEntity> missions);
    }

    public void saveMission(MissionEntity mission) {
        executorService.execute(() -> missionDao.insert(mission));
    }

    public MissionEntity toEntity(Mission mission) {
        if (mission == null) {
            return null;
        }

        long clearedTime = 0L;
        if ("CLEARED".equals(mission.getStatus())) {
            DateUtils dateUtils = new DateUtils();
            clearedTime = dateUtils.getCurrentTimeMillis();
        }

        return new MissionEntity(
                mission.getExerciseName(),
                mission.getTargetCount(),
                mission.getUnit(),
                mission.getStatus(),
                mission.getStartTime(),
                mission.getDeadlineTime(),
                clearedTime,
                1,
                mission.getUserFeeling(),
                mission.getEstimatedCalories()
        );
    }

    public void updateMission(MissionEntity mission) {
        executorService.execute(() -> missionDao.update(mission));
    }

    public void getAllMissions(MissionListCallback callback) {
        executorService.execute(() -> {
            List<MissionEntity> missions = missionDao.getAll();
            mainHandler.post(() -> callback.onResult(missions));
        });
    }

    public void getTodayMissions(
            long startOfDay,
            long endOfDay,
            MissionListCallback callback
    ) {
        executorService.execute(() -> {
            List<MissionEntity> missions = missionDao.getTodayMissions(startOfDay, endOfDay);
            mainHandler.post(() -> callback.onResult(missions));
        });
    }
}
