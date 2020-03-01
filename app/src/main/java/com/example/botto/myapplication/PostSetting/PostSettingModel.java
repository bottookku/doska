package com.example.botto.myapplication.PostSetting;

import android.content.ContentValues;
import android.util.Log;

import com.example.botto.myapplication.tools.Sql;

import java.util.List;

/**
 * Created by bottookku on 26.01.2020.
 */

public class PostSettingModel {
    PostSettingPresenter presenter;

    public PostSettingModel(PostSettingPresenter presenter){
        this.presenter = presenter;
    }


    void setSetting (int[] setting){
        ContentValues cv = new ContentValues();
        cv.put(Sql.col_INTERVAL,setting[0]);
        cv.put(Sql.col_setting_work_start,setting[1]);
        cv.put(Sql.col_setting_work_end,setting[2]);
        cv.put(Sql.col_setting_random_interval,setting[3]);
        cv.put(Sql.col_time_next_run,0); //ОБНУЛИТЬ NEXT RUN TIME при изменении настроек...
        Sql.update(Sql.TABLE_POST,Sql.col_post_id + "=?",new String[]{presenter.postId+""},cv);
    }

    void getSetting() {
        Integer[] dd = new Integer[4];
        Log.e(presenter.postId+"", presenter.postId+"" );
        List<ContentValues> cv = Sql.select(Sql.TABLE_POST, new String[]{
            Sql.col_setting_random_interval,
            Sql.col_setting_work_end,
            Sql.col_setting_work_start,
            Sql.col_INTERVAL},
            Sql.col_post_id + " =? ",
            new String[]{presenter.postId + ""});
        dd[0] = cv.get(0).getAsInteger(Sql.col_INTERVAL);
        dd[1] = cv.get(0).getAsInteger(Sql.col_setting_work_start);
        dd[2] = cv.get(0).getAsInteger(Sql.col_setting_work_end);
        dd[3] = cv.get(0).getAsInteger(Sql.col_setting_random_interval);
        presenter.onGettingModelSetting(dd);
    }

}
