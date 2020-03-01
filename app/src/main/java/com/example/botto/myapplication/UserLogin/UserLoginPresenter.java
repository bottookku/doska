package com.example.botto.myapplication.UserLogin;


import com.example.botto.myapplication.Interfaces.MainInterface;
import com.example.botto.myapplication.UserList.UserListView;
import com.example.botto.myapplication.tools.Error;
import com.example.botto.myapplication.tools.ReplaceFragment;


public class UserLoginPresenter implements MainInterface.interfacerPresenter{
    UserLoginData data;
    UserLoginView userLoginView;
    LoginUserModel model;
    public void onClickButton(){
        data = userLoginView.getData();
        userLoginView.showProgress(true);
        model.loginCreateRequest();
    }

    @Override
    public void onLoadView(Object dd) {
        userLoginView = (UserLoginView)dd;
        model = new LoginUserModel(this);
    }

    //model
    public void loginLoaded(UserLoginData data){
        if(data.isHaveError){
            userLoginView.showProgress(false);
            Error.Error(data.descriptionError,false,true,false,null);
        }else {
            model.loginPassed();
        }
    }

    public void onLoginPassed(){
        if(userLoginView!=null) {
            userLoginView.showProgress(false);
            UserListView fragment = new UserListView();
            new ReplaceFragment(userLoginView,fragment,null);


        }
    }
    void inetIsNot(){
        userLoginView.showProgress(false);
    }
    void onLcickBack(){
        userLoginView.showProgress(false);
        UserListView fragment = new UserListView();
        new ReplaceFragment(userLoginView,fragment,null);
    }


}
