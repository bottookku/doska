package com.example.botto.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    static public Context context;
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context_, Intent intent) {
        context = context_;
        serviceWhile.serviceWhile2();
    }
}
