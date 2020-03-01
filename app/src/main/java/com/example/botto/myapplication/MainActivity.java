package com.example.botto.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.example.botto.myapplication.UserList.UserListView;
import com.example.botto.myapplication.service.AlarmReceiver;
import com.example.botto.myapplication.service.Post;
import com.example.botto.myapplication.service.PostCountChanger;
import com.example.botto.myapplication.tools.Sql;

import java.util.List;

import static com.example.botto.myapplication.service.AlarmReceiver.context;
import static com.example.botto.myapplication.service.serviceWhile.alarm;
import static com.example.botto.myapplication.service.serviceWhile.getUserName;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(context == null){
            context = getApplicationContext();
        }
        getUserName();
        setContentView(R.layout.fragmentroot);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserListView fragment = new UserListView();
        fragmentTransaction.add(R.id.frag_root, fragment);
        fragmentTransaction.commit();
        alarm();

        Post dd = new Post();
        List<ContentValues> cv = Sql.select(Sql.TABLE_POST,new String[]{Sql.col_post_id},null,null);
        for (ContentValues d :cv) {
            dd.calculateNextRunTime(d.getAsInteger(Sql.col_post_id));
        }

    }



}


