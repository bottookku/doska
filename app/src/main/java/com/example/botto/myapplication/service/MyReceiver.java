package com.example.botto.myapplication.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.botto.myapplication.Interfaces.MainInterface;
import com.example.botto.myapplication.MainActivity;
import com.example.botto.myapplication.service.AlarmReceiver;

import static com.example.botto.myapplication.service.serviceWhile.alarm;

public class MyReceiver extends WakefulBroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(AlarmReceiver.context==null) {
            AlarmReceiver.context = context;
        }
        alarm();
    }
}
