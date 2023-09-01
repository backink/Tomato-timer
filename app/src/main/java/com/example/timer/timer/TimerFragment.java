package com.example.timer.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.timer.EventDatabase;
import com.example.timer.MainActivity;
import com.example.timer.R;
import com.example.timer.TimerService;
import com.example.timer.databinding.FragmentTimerBinding;
import com.example.timer.Event;

public class TimerFragment extends Fragment {

    private FragmentTimerBinding binding;
    private buttonStates buttonState = buttonStates.BUTTON_STOPPED;
    private TimeViewModel timeViewModel;
    private enum buttonStates {
        BUTTON_STARTED,
        BUTTON_STOPPED
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTimerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        timeViewModel = new ViewModelProvider(this).get(TimeViewModel.class);

        EventDatabase db = EventDatabase.getDatabase(getContext());
        timeViewModel.setDao(db.eventDao());

        timeViewModel.getProgress().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressCircular.setProgress(integer);
            }
        });
        timeViewModel.getTimeText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.timeText.setText(s);
            }
        });

        //initButton();
        initButtonTest();
        initProgressBar();

        return root;
    }

    private void initButtonTest() {
        binding.startButton.setOnClickListener(view -> {
            Event e = new Event();
            e.category = "abc";
            e.remark = "123";
            timeViewModel.insertEvent(e);

        });
    }

    private void initButton() {
        CountDownTimer button_timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                binding.startButton.setText("Cancel " + "(" + String.valueOf(l / 1000) + ")");
            }
            @Override
            public void onFinish() {
                binding.startButton.setText(R.string.button_cancel);
            }
        };

        binding.startButton.setOnClickListener(view -> {
            if (buttonState == buttonStates.BUTTON_STOPPED) {
                long input = checkInput();
                if (input > 0) {
                    waitForRegret(button_timer);
                    timeViewModel.startAction(input);
                }
            } else {
                stopButtonTimer(button_timer);
                timeViewModel.cancelAction();
            }
        });
    }

    private void waitForRegret(CountDownTimer buttonTimer) {
        buttonState = buttonStates.BUTTON_STARTED;
        buttonTimer.start();
    }

    private void stopButtonTimer(CountDownTimer buttonTimer) {
        buttonState = buttonStates.BUTTON_STOPPED;
        buttonTimer.cancel();
    }

    private long getTimeDuration() {
        long timeValue;
        try {
            timeValue = Integer.parseInt(binding.timeInput.getText().toString());
            if (timeValue > 0)
                return timeValue;
        } catch (NumberFormatException e) {
            // throw new RuntimeException(e);
        }
        Toast.makeText(getContext(), "Please input valid values", Toast.LENGTH_SHORT).show();
        return 0;
    }

    private void initProgressBar() {
        binding.progressCircular.setMax(120);
        binding.progressCircular.setProgress(0);
    }



    private long checkInput() {
        long timeValue = getTimeDuration();
        if (timeValue > 0)
            return timeValue;
        return 0;
    }

}
