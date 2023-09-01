package com.example.timer;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

public class TimerService extends Service {
    private Looper serviceLooper;
    private CountDownTimer timer;

    private Event event;
    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        int timeValue = intent.getIntExtra("timeValue", 0);

        timer = initTimer((long) timeValue * 1000);
        timer.start();
        return START_STICKY;
    }
    public IBinder onBind(Intent i) {
        return null;
    }

    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    private CountDownTimer initTimer(long timeValue) {
        return new CountDownTimer(timeValue, 1000) {
            @Override
            public void onTick(long l) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.ACTION_VIEW");
                intent.putExtra("timeValue", l);
                sendBroadcast(intent);
                Log.i("Timer", "Intent sent");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.ACTION_VIEW");
                intent.putExtra("timeValue", 0L);
                sendBroadcast(intent);

                intent.setAction("timer_finished");
                sendBroadcast(intent);
                Log.d("Timer", "Timer finished");
            }
        };
    }


}
