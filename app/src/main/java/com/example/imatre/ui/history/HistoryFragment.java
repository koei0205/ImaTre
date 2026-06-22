package com.example.imatre.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.imatre.R;
import com.example.imatre.data.local.entity.MissionEntity;
import com.example.imatre.data.repository.MissionRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryFragment extends Fragment {

    private MissionRepository missionRepository;
    private TextView historyListText;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        missionRepository = new MissionRepository(requireContext());
        historyListText = view.findViewById(R.id.history_list_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMissionHistory();
    }

    private void loadMissionHistory() {
        if (missionRepository == null || historyListText == null) {
            return;
        }

        missionRepository.getAllMissions(missions -> {
            if (missions == null || missions.isEmpty()) {
                historyListText.setText("まだ履歴はありません");
                return;
            }

            historyListText.setText(buildHistoryText(missions));
        });
    }

    private String buildHistoryText(List<MissionEntity> missions) {
        StringBuilder builder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm", Locale.getDefault());

        for (MissionEntity mission : missions) {
            String status = mission.getStatus();
            if ("CLEARED".equals(status)) {
                status = "クリア";
            } else if ("MISSED".equals(status)) {
                status = "ミス";
            }

            builder.append(dateFormat.format(new Date(mission.getStartTime())))
                    .append("\n")
                    .append(mission.getExerciseName())
                    .append(" ")
                    .append(mission.getTargetCount())
                    .append(mission.getUnit())
                    .append("\n結果：")
                    .append(status)
                    .append("\n消費カロリー：")
                    .append(String.format(Locale.getDefault(), "%.1f", mission.getEstimatedCalories()))
                    .append(" kcal\nきつさ：");

            if (mission.getUserFeeling() >= 1 && mission.getUserFeeling() <= 5) {
                builder.append(mission.getUserFeeling()).append(" / 5");
            } else {
                builder.append("未回答");
            }

            builder.append("\n\n");
        }

        return builder.toString().trim();
    }
}