package com.example.botto.myapplication.Pay;

import android.util.Log;


public class PayPresenter {
    PayView view;
    PayModel model;

    public PayPresenter(PayView view){
        this.view = view;
        model = new PayModel(this);
        view.showPb(true);
        model.getPrice();
        view.loadingVihod();
        model.getVihodCoutn();
    }

    public void onGetPrice(String body, boolean isInetOff){
        view.showPb(false);
        if(isInetOff){
            view.showError("Интернета нет");
        }else {
            if(!body.isEmpty()){
                view.loadPrice(Float.parseFloat(body));
            }
            else {
                view.showError("Пустая хрень пришла");
            }
        }
    }

    public void onClickCheckPromo(String code){
        view.showPbPromo(true);
        model.checkPromo(code);
    }

    public void onPromoFromAsynctask(String body, boolean inetIsOff){
        if(body.equals("0")){
            view.showError("Вы это промокод уже использовали");
        }else if(body.equals("-1")){
            view.showError("Это неверный промокод");
        }else if(body.equals("-2")){
            view.showError("Этот промокод уже исчепан");
        }else {
            view.showError("Вам добавлено "+ body +" выходов");
        }
        view.showPbPromo(false);
    }

    public void onVihodLoaded(String body, boolean inetIsOff){

        if(inetIsOff){
            view.loadedVihod("Не загрузилось");
        }else {
            view.loadedVihod(body);
        }
    }

}
