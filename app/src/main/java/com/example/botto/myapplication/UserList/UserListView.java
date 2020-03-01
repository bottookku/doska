package com.example.botto.myapplication.UserList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.botto.myapplication.R;

import java.util.List;


public class UserListView extends Fragment {

    LinearLayout linearUsersSpace;
    Button buttonAddUser;
    Button buttonPay;
    TextView textCountPost;
    UserListPresenter presenter;
    ProgressBar progressBar;
    /*
    void UserPageFrag(UserPagePresenter presenter){
        this.presenter = presenter;
    }
*/
    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.new_user, container, false);
        presenter = new UserListPresenter();
        buttonAddUser = (Button) view.findViewById(R.id.button14);
        buttonPay = (Button) view.findViewById(R.id.button10);
        textCountPost = (TextView)view.findViewById(R.id.textView12);
        linearUsersSpace = (LinearLayout)view.findViewById(R.id.ddi);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar2);
        buttonAddUser.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                presenter.onClickAddUser();
            }
        });
        buttonPay.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                presenter.onClickPay();
            }
        });
        //VIEW загрусился блядь пора запускать презентер нахуй
        presenter.onLoadView(this);
        return view;
    }


    public void loadUsers(final List<UserListData> users)
    {
        for(final UserListData usersList : users){
            LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(getActivity()).inflate(R.layout.new_user_item, null);
            LinearLayout linear = (LinearLayout) linearLayout.findViewById(R.id.aa);
            TextView textView = (TextView)linearLayout.findViewById(R.id.name_user_new);
            Button buttonPereiti = (Button) linearLayout.findViewById(R.id.button21);
            Button buttonDelete = (Button) linearLayout.findViewById(R.id.button22);

            textView.setText(usersList.users);

            linear.setTag(usersList.users_id);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onLongClickUserNameListener(usersList.users_id);
                }
            });

            buttonPereiti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onClickUser(usersList.users_id);
                }
            });
            linearUsersSpace.addView(linear);
        }
    }


    public void LoadCountPost(int countPay){
        textCountPost.setText(countPay);
    }


    public void showProgress(Boolean isShow)
    {
        if(isShow) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }else {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }


    public void showDialog(final Integer userId)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("Удалить?")
                .setNegativeButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onClickDelteUser(userId);
                    }
                })
                .setPositiveButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public void deleteItem(int id){
        for(int i = 0; i< linearUsersSpace.getChildCount(); i++){
            View view = linearUsersSpace.getChildAt(i);
            if((int)view.getTag() == id) {
                linearUsersSpace.removeView(view);
            }
        }
    }
}

