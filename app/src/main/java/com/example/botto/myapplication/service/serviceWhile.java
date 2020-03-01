package com.example.botto.myapplication.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.example.botto.myapplication.tools.Sql;

import java.util.List;

import static com.example.botto.myapplication.service.AlarmReceiver.context;


public class serviceWhile {
    public static String userName;
    protected static void serviceWhile2() {

        getUserName();
        alarm();


        List<ContentValues> list = Sql.select(Sql.TABLE_POST,new String[]{Sql.col_post_enabled,Sql.col_post_id,Sql.col_user_id},null,null);
        Post post = new Post();
        for(ContentValues cv: list){
            if(cv.getAsBoolean(Sql.col_post_enabled)){
                int postid = cv.getAsInteger(Sql.col_post_id);
                if(post.isTimePosting(postid)){
                    PostCountChanger d = new PostCountChanger();
                    d.post = post;
                    d.postId = postid;
                    d.changePostCount(userName);
                }
            }
        }
    }

    public static void getUserName(){
        if(userName==null) {
            List<ContentValues> contentValues = Sql.select(Sql.TABLE_USER, new String[]{Sql.col_user_name, Sql.col_user_id}, null, null);
            if (contentValues == null || contentValues.size() == 0) {

            }else {
                userName = contentValues.get(0).getAsString(Sql.col_user_name);
            }
        }
    }



    public static boolean alarmAlredyRunned;
    public static void alarm() {

        AlarmManager mgr;
        if (!alarmAlredyRunned) {
            mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, AlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pi);
            } else {
                mgr.setRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime(), 60000, pi);
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent i=new Intent(context, AlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
                mgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000 , pi);
            }
        }
        alarmAlredyRunned = true;
    }
}
