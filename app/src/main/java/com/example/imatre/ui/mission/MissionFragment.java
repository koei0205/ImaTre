package com.example.imatre.ui.mission;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.imatre.R;
import com.example.imatre.data.local.entity.MissionEntity;
import com.example.imatre.data.repository.MissionRepository;
import com.example.imatre.model.Mission;
import com.example.imatre.service.MissionGenerator;
import com.example.imatre.util.DateUtils;

public class MissionFragment extends Fragment {

    private static final int DAILY_MISSION_LIMIT = 3;

    private Mission currentMission;
    private MissionRepository missionRepository;
    private MissionGenerator missionGenerator;
    private DateUtils dateUtils;
    private TextView missionDescription;
    private TextView missionRemainingTime;
    private TextView missionCalories;
    private TextView todayMissionCountText;
    private TextView remainingMissionCountText;
    private TextView missionStateText;
    private Button missionClearButton;
    private Button startNewMissionButton;
    private boolean isMissionSaved;
    private boolean isTodayMissionCountLoaded;
    private int todayMissionCount;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_mission, container, false);

        missionRepository = new MissionRepository(requireContext());
        missionGenerator = new MissionGenerator();
        dateUtils = new DateUtils();
        missionDescription = view.findViewById(R.id.mission_description);
        missionRemainingTime = view.findViewById(R.id.mission_remaining_time);
        missionCalories = view.findViewById(R.id.mission_calories);
        todayMissionCountText = view.findViewById(R.id.today_mission_count);
        remainingMissionCountText = view.findViewById(R.id.remaining_mission_count);
        missionStateText = view.findViewById(R.id.mission_state_text);
        missionClearButton = view.findViewById(R.id.mission_clear_button);
        startNewMissionButton = view.findViewById(R.id.start_new_mission_button);
        missionClearButton.setEnabled(false);
        startNewMissionButton.setEnabled(false);

        missionClearButton.setOnClickListener(v -> {
            if (currentMission == null) {
                Toast.makeText(requireContext(), "ミッションがありません", Toast.LENGTH_SHORT).show();
                return;
            }

            long currentTime = dateUtils.getCurrentTimeMillis();
            if (currentTime <= currentMission.getDeadlineTime()) {
                currentMission.setStatus("CLEARED");
                Toast.makeText(requireContext(), "クリアしました！", Toast.LENGTH_SHORT).show();
                showFeelingSurveyDialog();
            } else {
                currentMission.setStatus("MISSED");
                missionRemainingTime.setText("残り時間：時間切れ");
                if (!isMissionSaved) {
                    MissionEntity missionEntity = missionRepository.toEntity(currentMission);
                    if (missionEntity != null) {
                        missionRepository.saveMission(missionEntity);
                        isMissionSaved = true;
                        missionStateText.setText("時間切れです。履歴に保存しました");
                        missionClearButton.setEnabled(false);
                        loadTodayMissionCount(false);
                    }
                }
                Toast.makeText(requireContext(), "時間切れです。履歴に保存しました", Toast.LENGTH_SHORT).show();
            }
        });

        startNewMissionButton.setOnClickListener(v -> startNewMission());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTodayMissionCount(true);
    }

    private void startNewMission() {
        if (!isTodayMissionCountLoaded || todayMissionCount >= DAILY_MISSION_LIMIT) {
            updateTodayMissionDisplay();
            return;
        }

        currentMission = missionGenerator.generateMission();
        isMissionSaved = false;
        missionStateText.setText("現在のミッションに挑戦中です");
        missionClearButton.setEnabled(true);

        missionDescription.setText(
                currentMission.getExerciseName()
                        + "を"
                        + currentMission.getTargetCount()
                        + currentMission.getUnit()
                        + "しよう！"
        );

        long remainingMillis = dateUtils.getRemainingMillis(currentMission.getDeadlineTime());
        long remainingMinutes = (remainingMillis + 60L * 1000L - 1L) / (60L * 1000L);
        if (remainingMinutes > 0L) {
            missionRemainingTime.setText("残り時間：約" + remainingMinutes + "分");
        } else {
            missionRemainingTime.setText("残り時間：時間切れ");
        }

        missionCalories.setText(
                "推定消費カロリー：" + currentMission.getEstimatedCalories() + " kcal"
        );
    }

    private void loadTodayMissionCount(boolean startMissionIfNeeded) {
        long startOfDay = DateUtils.getStartOfTodayMillis();
        long endOfDay = DateUtils.getEndOfTodayMillis();

        missionRepository.getTodayMissions(startOfDay, endOfDay, missions -> {
            todayMissionCount = missions == null ? 0 : missions.size();
            isTodayMissionCountLoaded = true;
            updateTodayMissionDisplay();

            if (startMissionIfNeeded
                    && currentMission == null
                    && todayMissionCount < DAILY_MISSION_LIMIT) {
                startNewMission();
            }
        });
    }

    private void updateTodayMissionDisplay() {
        int displayedCount = Math.min(todayMissionCount, DAILY_MISSION_LIMIT);
        todayMissionCountText.setText(
                "今日のミッション回数：" + displayedCount + " / " + DAILY_MISSION_LIMIT
        );

        if (todayMissionCount >= DAILY_MISSION_LIMIT) {
            remainingMissionCountText.setText("今日はすべてのミッションが完了しました");
            missionStateText.setText("今日はすべてのミッションが完了しました");
            missionClearButton.setEnabled(false);
            startNewMissionButton.setEnabled(false);
        } else {
            int remainingCount = DAILY_MISSION_LIMIT - todayMissionCount;
            remainingMissionCountText.setText("あと" + remainingCount + "回できます");
            startNewMissionButton.setEnabled(isTodayMissionCountLoaded);
        }
    }

    private void showFeelingSurveyDialog() {
        String[] choices = {
                "1：楽だった",
                "2：少し楽",
                "3：ちょうどよい",
                "4：きつい",
                "5：かなりきつい"
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("今回の運動はどれくらいきつかったですか？")
                .setItems(choices, (dialog, which) -> {
                    if (currentMission == null) {
                        Toast.makeText(requireContext(), "ミッションがありません", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentMission.setUserFeeling(which + 1);
                    if (!isMissionSaved) {
                        MissionEntity missionEntity = missionRepository.toEntity(currentMission);
                        if (missionEntity != null) {
                            missionRepository.saveMission(missionEntity);
                            isMissionSaved = true;
                            missionStateText.setText("クリア済みです。新しいミッションを開始できます");
                            missionClearButton.setEnabled(false);
                            loadTodayMissionCount(false);
                        }
                    }

                    Toast.makeText(requireContext(), "回答と履歴を保存しました", Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}
