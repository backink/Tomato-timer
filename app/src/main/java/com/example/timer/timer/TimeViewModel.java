package com.example.timer.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.timer.EventDao;
import com.example.timer.EventDatabase;
import com.example.timer.R;
import com.example.timer.TimerService;
import com.example.timer.Event;

public class TimeViewModel extends ViewModel {
    private MutableLiveData<Integer> progress;
    private MutableLiveData<String> timeText;
    private EventDao eventDao;

    public void setDao(EventDao dao) {
        eventDao = dao;
    }

    public void insertEvent(Event event) {
        Thread thread = new Thread(() -> eventDao.insert(event));
        thread.start();
        // Does the thread need to be joined?
    }

    public MutableLiveData<Integer> getProgress() {
        if (progress == null) {
            progress = new MutableLiveData<Integer>();
        }
        return progress;
    }

    public MutableLiveData<String> getTimeText() {
        if (timeText == null) {
            timeText = new MutableLiveData<String>();
        }
        return timeText;
    }

    public void startAction(long input) {
//        serviceIntent = new Intent(this, TimerService.class);
//        serviceIntent.putExtra("timeValue", (int) input);
//        //addEvent(input, binding.categoryInput.getText().toString(), binding.remarkInput.getText().toString());
//        startService(serviceIntent);
        CountDownTimer timer = new CountDownTimer(input * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress.setValue((int) millisUntilFinished / 1000);
                timeText.setValue(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                progress.setValue(0);
                timeText.setValue(String.valueOf(R.string.stopped));
            }
        };
        progress.setValue((int) input);
        timer.start();
        Log.i("ViewModel", "Timer start");
    }

    public void cancelAction() {
        //stopService(serviceIntent);

    }


}
