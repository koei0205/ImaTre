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
import com.example.imatre.model.Mission;
import com.example.imatre.service.MissionGenerator;
import com.example.imatre.util.DateUtils;

public class MissionFragment extends Fragment {

    private Mission currentMission;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_mission, container, false);

        MissionGenerator missionGenerator = new MissionGenerator();
        currentMission = missionGenerator.generateMission();

        TextView missionDescription = view.findViewById(R.id.mission_description);
        missionDescription.setText(
                currentMission.getExerciseName()
                        + "を"
                        + currentMission.getTargetCount()
                        + currentMission.getUnit()
                        + "しよう！"
        );

        DateUtils dateUtils = new DateUtils();
        long remainingMillis = dateUtils.getRemainingMillis(currentMission.getDeadlineTime());
        long remainingMinutes = (remainingMillis + 60L * 1000L - 1L) / (60L * 1000L);

        TextView missionRemainingTime = view.findViewById(R.id.mission_remaining_time);
        if (remainingMinutes > 0L) {
            missionRemainingTime.setText("残り時間：約" + remainingMinutes + "分");
        } else {
            missionRemainingTime.setText("残り時間：時間切れ");
        }

        TextView missionCalories = view.findViewById(R.id.mission_calories);
        missionCalories.setText("推定消費カロリー：" + currentMission.getEstimatedCalories() + " kcal");

        Button missionClearButton = view.findViewById(R.id.mission_clear_button);
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
                Toast.makeText(requireContext(), "時間切れです", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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
                    Toast.makeText(requireContext(), "回答を保存しました", Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}
