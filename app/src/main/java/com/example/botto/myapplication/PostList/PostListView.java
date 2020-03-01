package com.example.botto.myapplication.PostList;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.botto.myapplication.R;

import java.util.List;

/**
 * Created by bottookku on 26.01.2020.
 */

public class PostListView extends Fragment{
    public static String preferenceBundleNameUserId;
    LinearLayout linearLayoutAdd;
    LinearLayout linearLayoutItem;
    PostListPresenter postListPresenter;
    ProgressBar progressBar;
    Button buttonRefresh;
    Button back;
    Button buttonPay;
    TextView textCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        postListPresenter = new PostListPresenter();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.new_post_list, container, false);
        buttonPay = (Button)view.findViewById(R.id.buttonPay);
        textCount = (TextView)view.findViewById(R.id.TextViewpayCount);
        linearLayoutAdd = (LinearLayout) view.findViewById(R.id.postAdd);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar4);
        buttonRefresh =(Button)view.findViewById(R.id.button17);
        back = (Button)view.findViewById(R.id.back);
        PostListPresenter.userId = getArguments().getInt(preferenceBundleNameUserId);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postListPresenter.onClickRefresh();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postListPresenter.onClickBack();
            }
        });
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postListPresenter.onClickPay();
            }
        });
        postListPresenter.onLoadView(this);
        return view;
    }

    void loadPosts(List<PostData> posts){
        for(final PostData data : posts){
            if(getActivity()==null) {
                return;
            }
            linearLayoutItem = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.new_post_list_item, null);
            linearLayoutItem.setTag(data.postId);
            final TextView text = (TextView) linearLayoutItem.findViewById(R.id.textView28);
            Button buttonSetting = (Button) linearLayoutItem.findViewById(R.id.button16);
            ToggleButton toggleButton = (ToggleButton) linearLayoutItem.findViewById(R.id.toggleButton);
            Log.e("AAAAAAAAAA",data.enabled+"");
            toggleButton.setChecked(data.enabled);
            toggleButton.setTag(data.postId);
            text.setText(data.title);
            buttonSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postListPresenter.onClickSetting(data.postId);
                }
            });
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ToggleButton)v).setChecked(((ToggleButton)v).isChecked());
                    data.enabled = ((ToggleButton)v).isChecked();
                    postListPresenter.onClickTogglePost(data);
                }
            });

            linearLayoutAdd.addView(linearLayoutItem
            );
        }
    }

    void resetPosts(){
        linearLayoutAdd.removeAllViews();
    }


    void showProgress(boolean show){
        if(show) {
            progressBar.setVisibility(View.VISIBLE);
            linearLayoutAdd.setVisibility(View.INVISIBLE);
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            linearLayoutAdd.setVisibility(View.VISIBLE);
        }
    }
}
