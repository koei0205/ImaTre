package com.example.imatre.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.imatre.R;
import com.example.imatre.data.local.entity.MissionEntity;
import com.example.imatre.data.repository.MissionRepository;
import com.example.imatre.util.DateUtils;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private MissionRepository missionRepository;
    private TextView missionStatusText;
    private TextView calorieStatusText;
    private ProgressBar missionProgress;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        missionRepository = new MissionRepository(requireContext());
        missionStatusText = view.findViewById(R.id.home_mission_status);
        calorieStatusText = view.findViewById(R.id.home_calorie_status);
        missionProgress = view.findViewById(R.id.home_mission_progress);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTodaySummary();
    }

    private void loadTodaySummary() {
        if (missionRepository == null
                || missionStatusText == null
                || calorieStatusText == null
                || missionProgress == null) {
            return;
        }

        long startOfDay = DateUtils.getStartOfTodayMillis();
        long endOfDay = DateUtils.getEndOfTodayMillis();

        missionRepository.getTodayMissions(startOfDay, endOfDay, missions -> {
            int clearCount = 0;
            double totalCalories = 0.0;

            if (missions != null) {
                for (MissionEntity mission : missions) {
                    if ("CLEARED".equals(mission.getStatus())) {
                        clearCount++;
                        totalCalories += mission.getEstimatedCalories();
                    }
                }
            }

            missionStatusText.setText(clearCount + " / 3");
            missionProgress.setProgress(Math.min(clearCount, 3));
            calorieStatusText.setText(
                    String.format(Locale.getDefault(), "%.1f", totalCalories)
                            + " kcal"
            );
        });
    }
}
