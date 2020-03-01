package com.example.botto.myapplication.UserLogin;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import com.example.botto.myapplication.tools.AbstractHandlerBuildingRequest;
import com.example.botto.myapplication.tools.Error;
import com.example.botto.myapplication.tools.InternetTXRX;
import com.example.botto.myapplication.tools.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.botto.myapplication.tools.Error.CONST_ERROR_LOGIN_NOT_PASS;
import static com.example.botto.myapplication.tools.Error.CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER;

import static com.example.botto.myapplication.tools.Sql.col_cookies;
import static com.example.botto.myapplication.tools.Sql.col_user_name;
import static com.example.botto.myapplication.tools.Sql.col_user_pass;

public class LoginUserModel {
    final String CONST_LOGIN_CHECK_URL = "https://id.ykt.ru/page/login";
    final String CONST_SEACRH_COOKIE_REGEX = "idCookie=([^\"].+?);Path.*";

    String login;
    String password;
    String cookiesFiled;
    UserLoginPresenter userLoginPresenter;

    public LoginUserModel(UserLoginPresenter userLoginPresenter){
        this.userLoginPresenter = userLoginPresenter;
    }

    void loginCreateRequest(){
        this.login = userLoginPresenter.data.userName;
        this.password = userLoginPresenter.data.password;
        Map<String, String> paramsReq = new HashMap<>();
        paramsReq.put("nick",login);
        paramsReq.put("password",password);
        InternetTXRX inetReq = new InternetTXRX();
        InternetTXRX.InetRequest inetRequest = inetReq.new InetRequest();
        inetRequest.internalTypeOfRequest = InternetTXRX.INTENAL_TYPE_REQUEST_LOGIN;
        inetRequest.RequestParams = paramsReq;
        inetRequest.object = this;
        inetRequest.url = CONST_LOGIN_CHECK_URL;
        inetRequest.method = InternetTXRX.METHOD_POST;
        inetRequest.isNeedHeaders = true;
        inetReq.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,inetRequest);
    }
    public void loginPassed(){
        Log.e("LOGIN","PASSED");
        ContentValues cv = new ContentValues();
        cv.put(col_user_name,login);
        cv.put(col_user_pass,password);
        cv.put(col_cookies,userLoginPresenter.data.cookie);
        Sql.replace(Sql.TABLE_USER,cv);
        userLoginPresenter.onLoginPassed();

    }
    public void actionFromAsyncTask(){
        if(cookiesFiled==null||cookiesFiled=="") {
            userLoginPresenter.data.isHaveError = true;
            userLoginPresenter.data.descriptionError = CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER;
            userLoginPresenter.loginLoaded(userLoginPresenter.data);
        }else{
            Pattern p = Pattern.compile(CONST_SEACRH_COOKIE_REGEX);
            Matcher m = p.matcher(cookiesFiled);
            if (m.find()) {
                userLoginPresenter.data.isHaveError = false;
                userLoginPresenter.data.cookie = m.group(1);
                userLoginPresenter.loginLoaded(userLoginPresenter.data);
            } else {
                userLoginPresenter.data.isHaveError = true;
                userLoginPresenter.data.descriptionError = CONST_ERROR_LOGIN_NOT_PASS;
                userLoginPresenter.loginLoaded(userLoginPresenter.data);
            }
        }
    }
    public void setterFromAsyncTask(String string, boolean isNotInet){
        if(isNotInet){
            Log.e("AA","aaa");
            userLoginPresenter.inetIsNot();}
        else {
            cookiesFiled = string;
        }

    }

}
