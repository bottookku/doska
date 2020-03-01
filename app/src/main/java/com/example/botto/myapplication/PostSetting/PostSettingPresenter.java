package com.example.botto.myapplication.PostSetting;


import android.os.Bundle;
import android.widget.EditText;

import com.example.botto.myapplication.PostList.PostListView;
import com.example.botto.myapplication.R;
import com.example.botto.myapplication.UserList.UserListPresenter;
import com.example.botto.myapplication.tools.Error;
import com.example.botto.myapplication.tools.ReplaceFragment;

import static com.example.botto.myapplication.PostList.PostListPresenter.userId;

/**
 * Created by bottookku on 26.01.2020.
 */

public class PostSettingPresenter {
    int postId;
    PostSettingView view;
    PostSettingModel postSettingModel;
    void onLoadView(Object view){
        this.view = (PostSettingView) view;
        postSettingModel = new PostSettingModel(this);
        postSettingModel.getSetting();
    }



    void onClickButton(){
        int[] dd = view.getValues();
        if(dd[0]>30&&dd[0]<1000){
            if (dd[1]>0&&dd[1]<24){
                if (dd[2]>0&&dd[2]<24) {
                    if(dd[2]<=dd[1]){
                        Error.Error(1, false, true, true, "Время конца рабочего дня не должно быть меньше или равно времен начала рабочего дня");
                    }else {
                        if(dd[3]>14 && dd[3]< 1000){
                            postSettingModel.setSetting(dd);
                            view.info("Применено");

                        }else {
                            Error.Error(1, false, true, true, "Время рандомного интервала должно быть в пределах 15- 1000");
                        }
                    }
                }else {
                    Error.Error(1, false, true, true, "Время конца рабочего дня должно быть в пределах 1 - 24");
                }

            }else {
                if(dd[1]>=dd[2]){
                    Error.Error(1, false, true, true, "Время начала рабочего дня не должно быть больше или равно времени конца рабочего дня");
                }else {
                    Error.Error(1, false, true, true, "Время начала рабочего дня должно быть в пределах 1 - 24");
                }
            }
        }else {
            Error.Error(1,false,true,true,"Интервал выхода долен быть больше чем 30 минут но не более 1000");
        }

    }


    void onGettingModelSetting(Integer[] setting){
        view.setValues(setting);
    }

    void onClickBack(){
        PostListView fragment = new PostListView();
        Bundle bundle = new Bundle();
        bundle.putInt(PostListView.preferenceBundleNameUserId,userId);
        new ReplaceFragment(view,fragment,bundle);

    }

}
