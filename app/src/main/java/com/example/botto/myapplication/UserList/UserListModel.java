package com.example.botto.myapplication.UserList;

import android.content.ContentValues;
import android.util.Log;

import com.example.botto.myapplication.tools.Sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bottookku on 26.01.2020.
 */



public class UserListModel {
    UserListPresenter presenter;
    public UserListModel(UserListPresenter presenter){
        this.presenter = presenter;
    }

    public void  getUsersList(){
        List<ContentValues>  contentValues = Sql.select(Sql.TABLE_USER,new String[]{Sql.col_user_name,Sql.col_user_id},null,null);
        List<UserListData> usersLists = new ArrayList<>();
        for(ContentValues dd : contentValues){
            UserListData userList = new UserListData();
            userList.users = dd.getAsString(Sql.col_user_name);
            userList.users_id = dd.getAsInteger(Sql.col_user_id);
            usersLists.add(userList);
        }
        presenter.onLoadUser(usersLists);
    }

    public void deleteUserAndPosts(Integer userId){
        Log.e("-->",userId.toString());
        Log.e("---->",Sql.delete(Sql.TABLE_POST,Sql.col_user_id + " =?", new String[] {userId.toString()})+"");
        int i = Sql.delete(Sql.TABLE_USER,Sql.col_user_id + " =?", new String[] {userId.toString()});
        presenter.onDeltedUser(i);
    }
}
