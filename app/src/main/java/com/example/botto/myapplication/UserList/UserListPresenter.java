package com.example.botto.myapplication.UserList;

import android.os.Bundle;
import android.util.Log;

import com.example.botto.myapplication.Interfaces.MainInterface;
import com.example.botto.myapplication.PostList.PostListView;
import com.example.botto.myapplication.UserLogin.UserLoginView;
import com.example.botto.myapplication.tools.ReplaceFragment;

import java.util.List;



public class UserListPresenter implements MainInterface.interfacerPresenter{

    UserListView view;
    UserListModel modelUserPage;

    List<UserListData> users;

    public UserListPresenter(){
        modelUserPage = new UserListModel(this);
    }

    ///VIEW
    public void onClickAddUser(){
        UserLoginView fragment = new UserLoginView();
        new ReplaceFragment(view,fragment,null);

    }

    public void onClickPay(){

    }

    public void onClickDelteUser(int userid){
        view.showProgress(true);
        modelUserPage.deleteUserAndPosts(userid);
        view.deleteItem(userid);
    }






    public void onLoadView(Object view){
        this.view = (UserListView)view;
        this.view.showProgress(true);
        modelUserPage.getUsersList();
    }




    public void onClickUser(int userId){

        PostListView fragment = new PostListView();
        Bundle bundle = new Bundle();
        bundle.putInt(PostListView.preferenceBundleNameUserId,userId);
        fragment.setArguments(bundle);

        new ReplaceFragment(view,fragment,bundle);

    }


    public void onLongClickUserNameListener(int userid){
        view.showDialog(userid);
    }

    //MODELS
    public void onLoadUser(List<UserListData> users){

        if(users.size()==0||users == null){
            UserLoginView fragment = new UserLoginView();
            new ReplaceFragment(view,fragment,null);
        }else {
            PostListView fragment = new PostListView();
            Bundle bundle = new Bundle();
            bundle.putInt(PostListView.preferenceBundleNameUserId,users.get(0).users_id);
            fragment.setArguments(bundle);

            new ReplaceFragment(view,fragment,bundle);
        }
        //УДАЛИТЬ ВСЕ С ВЕРХУ и убрать комментарии // чтобы была многопользвовательская.
        //this.users = users;
        //view.showProgress(false);
        //view.loadUsers(users);
    }

    public void onDeltedUser(int i){
        view.showProgress(false);
        if(i!=1){
            Log.e("ERROR","Error");
        }else {
            Log.e("ERROR","OK");
        }
    }


}
