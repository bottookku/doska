package com.example.botto.myapplication.UserLogin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.botto.myapplication.R;

/**
 * Created by bottookku on 26.01.2020.
 */

public class UserLoginView extends Fragment{
    Button back;
    EditText login;
    EditText password;
    Button button;
    UserLoginPresenter presenter;
    ProgressBar progressBar;
    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new UserLoginPresenter();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.new_login_write_page, container, false);
        login = (EditText) view.findViewById(R.id.editText7);
        password = (EditText) view.findViewById(R.id.editText8);
        button = (Button) view.findViewById(R.id.button15);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar5);
        back = (Button) view.findViewById(R.id.button20);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickButton();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLcickBack();
            }
        });
        presenter.onLoadView(this);
        return view;
    }


    public UserLoginData getData(){
        UserLoginData data = new UserLoginData();
        data.userName = login.getText().toString();
        data.password = password.getText().toString();
        return data;
    }



    public void showProgress(Boolean isShow){
        if(isShow) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            password.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
        }else {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            login.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }
}
