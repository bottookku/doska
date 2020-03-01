package com.example.botto.myapplication.PostSetting;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.botto.myapplication.PostList.PostListPresenter;
import com.example.botto.myapplication.R;


public class PostSettingView extends Fragment {
    int postId;
    EditText interval;
    EditText startWork;
    EditText endWork;
    EditText random;
    Button back;
    PostSettingPresenter presenter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new PostSettingPresenter();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.new_posty_setting, container, false);
        interval = (EditText) view.findViewById(R.id.editText20);
        startWork = (EditText) view.findViewById(R.id.editText17);
        endWork = (EditText) view.findViewById(R.id.editText18);
        random = (EditText) view.findViewById(R.id.editText19);
        back = (Button) view.findViewById(R.id.button19);
        Button button = (Button) view.findViewById(R.id.button18);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickButton();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickBack();
            }
        });
        postId = getArguments().getInt(PostListPresenter.PrefPostId);
        presenter.postId = postId;
        presenter.onLoadView(this);
        return view;
    }

    int[] getValues(){
        int[] dd = new int[4];

        dd[0] = interval.getText().toString().isEmpty() ? 0 : Integer.parseInt(interval.getText().toString());
        dd[1] = startWork.getText().toString().isEmpty() ? 0 : Integer.parseInt(startWork.getText().toString());
        dd[2] = endWork.getText().toString().isEmpty() ? 0 : Integer.parseInt(endWork.getText().toString());
        dd[3] = random.getText().toString().isEmpty() ? 0 : Integer.parseInt(random.getText().toString());
        return dd;
    }

    void setValues(Integer[] values){
        interval.setText(values[0].toString());
        startWork.setText(values[1].toString());
        endWork.setText(values[2].toString());
        random.setText(values[3].toString());
    }
    void info(String info){
        Toast.makeText(getActivity(),info,Toast.LENGTH_LONG).show();
    }



}
