package com.example.botto.myapplication.Pay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.botto.myapplication.R;
import com.example.botto.myapplication.tools.Error;


public class PayView extends Fragment {
    PayPresenter presenter;
    Button buttonPromo;
    Button buttonPay;
    TextView textViewPrice;
    EditText pay;
    EditText promo;
    TextView textVihod;
    ProgressBar pb;
    ProgressBar pbPromo;
    ProgressBar pbVihod;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.pay, container, false);
        buttonPay = (Button) view.findViewById(R.id.buttonPAYCOunt);
        buttonPromo = (Button) view.findViewById(R.id.button3Promo);
        pay = (EditText)view.findViewById(R.id.editTextPayCount);
        promo = (EditText)view.findViewById(R.id.editTextPromo);
        textViewPrice = (TextView)view.findViewById(R.id.textViewPrice);
        pb = (ProgressBar)view.findViewById(R.id.pbPrice);
        pbPromo = (ProgressBar)view.findViewById(R.id.progressBar3);
        //pbPromo.setVisibility(View.INVISIBLE);
        pbVihod = (ProgressBar)view.findViewById(R.id.pbVihod);
        textVihod = (TextView)view.findViewById(R.id.textVihod);

        presenter = new PayPresenter(this);


        buttonPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickCheckPromo(promo.getText().toString());
            }
        });
        return view;
    }

    int getPay(){
        return Integer.parseInt(pay.getText().toString());
    }

    int getPromo() {
        return Integer.parseInt(promo.getText().toString());
    }

    public void loadPrice(Float price){
        textViewPrice.setText(price.toString());
    }

    public void showPb(boolean show){
        if(show){

            pb.setVisibility(View.VISIBLE);
        }else {
            pb.setVisibility(View.INVISIBLE);
        }
    }


    public void showPbPromo(boolean show){
        if(show){
            pbPromo.setVisibility(View.VISIBLE);
            buttonPromo.setEnabled(false);
            promo.setEnabled(false);
        }else {
            pbPromo.setVisibility(View.INVISIBLE);
            buttonPromo.setEnabled(true);
            promo.setEnabled(true);
        }
    }

    public  void loadingVihod(){
        pbVihod.setVisibility(View.VISIBLE);
        textVihod.setText("");
    };

    public  void loadedVihod(String text){
        pbVihod.setVisibility(View.INVISIBLE);
        textVihod.setText(text);
    };

    public void showError(String description){
        Error.Error(0,false,true,true,description);
    }
}
