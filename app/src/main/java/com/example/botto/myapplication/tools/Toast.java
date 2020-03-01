package com.example.botto.myapplication.tools;

import static com.example.botto.myapplication.service.AlarmReceiver.context;

public class Toast {
    static void Toast(String text, boolean isShort){
        int duration;
        if(isShort){
            duration = android.widget.Toast.LENGTH_SHORT;
        }else {
            duration = android.widget.Toast.LENGTH_LONG;
        }
        android.widget.Toast.makeText(context,text,duration).show();
    }
}
