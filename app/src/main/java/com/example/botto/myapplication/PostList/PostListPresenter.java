package com.example.botto.myapplication.PostList;



import android.os.Bundle;
import android.util.Log;

import com.example.botto.myapplication.Pay.PayView;
import com.example.botto.myapplication.PostSetting.PostSettingView;
import com.example.botto.myapplication.R;
import com.example.botto.myapplication.UserList.UserListView;
import com.example.botto.myapplication.UserLogin.UserLoginView;
import com.example.botto.myapplication.tools.Error;
import com.example.botto.myapplication.tools.ReplaceFragment;


public class PostListPresenter {
    public static String PrefPostId = "prefSettingPostId";
    public PostDataArray data;
    public static Integer userId;

    PostListView view;
    PostListModel model;
    void onLoadView(Object obj){
        view = (PostListView)obj;
        model = new PostListModel(this);


        if(model.isNoHaveRowsThisUserPostInSql()){
            model.getListAllPostsOfUser();
            view.showProgress(true);
        }else {
            Log.e("DDDDDDDDDDD","AAAAAAAAAAAAAAA");
            model.buildPostDataFromSql();
        }
    }

    void onClickBack(){
        UserListView fragment = new UserListView();
        new ReplaceFragment(view,fragment,null);
    }
    void inetIsNot(){
        view.showProgress(false);
    }

    void onClickRefresh(){
        view.showProgress(true);
        view.resetPosts();
        model.getListAllPostsOfUser();
    }
    void onClickTogglePost(PostData data)
    {
        model.endbledPost(data);
    }

    void onClickSetting(Integer postId){
        PostSettingView fragment = new PostSettingView();
        Bundle bundle = new Bundle();
        bundle.putInt(PrefPostId,postId);
        fragment.setArguments(bundle);
        new ReplaceFragment(view,fragment,bundle);

    }

    void onLoadedFromModel(PostDataArray data){
        view.showProgress(false);


        if(!data.isHaveError) {
            if (data.postDatas != null && data.postDatas.size() > 0) {
                model.applyDataFromJSONToSQL(data);
            }else {
                Error.Error(0,false,true,true,"Нет активный постов.");
            }
        }else {
            Error.Error(data.descrioptionNumber,false,true,data.isErrorFromServer,data.errorMsgFromServer);
        }
    }

    void onApplyAllRowsFromJSONinSQL(PostDataArray dd){
        model.buildDataPostArrayFromSQL(dd);
    }

    void onLoadDataFromSQL(PostDataArray postDataArray){
        view.loadPosts(postDataArray.postDatas);
    }

    void onClickPay(){
        PayView fragment = new PayView();
        new ReplaceFragment(view,fragment,null);


    };
}
