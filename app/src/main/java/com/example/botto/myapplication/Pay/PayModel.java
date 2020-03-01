package com.example.botto.myapplication.Pay;

import android.os.AsyncTask;
import android.util.ArrayMap;

import com.example.botto.myapplication.MainActivity;
import com.example.botto.myapplication.tools.InternetTXRX;

import java.util.Map;

import static com.example.botto.myapplication.service.serviceWhile.userName;

/**
 * Created by bottookku on 05.02.2020.
 */

public class PayModel {
    PayPresenter presenter;

    final static public String URL_WEB_SERVER = "https://quintillionth-thoug.000webhostapp.com/doska.php";
    public PayModel(PayPresenter presenter){
        this.presenter = presenter;
    }

    void getPrice(){
        InternetTXRX inetReq = new InternetTXRX();
        InternetTXRX.InetRequest inetRequest = inetReq.new InetRequest();
        inetRequest .internalTypeOfRequest = InternetTXRX.INTENAL_TYPE_REQUEST_GET_PRICE;
        Map<String,String> params = new ArrayMap<>();
        params.put("getpricedoska","1");
        inetRequest.RequestParams = params;
        inetRequest.object = this;
        inetRequest.url = URL_WEB_SERVER;
        inetRequest.method = InternetTXRX.METHOD_GET;
        inetReq.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, inetRequest);
    }

    void checkPromo(String promo){
        InternetTXRX inetReq = new InternetTXRX();
        InternetTXRX.InetRequest inetRequest = inetReq.new InetRequest();
        inetRequest .internalTypeOfRequest = InternetTXRX.INTENAL_TYPE_REQUEST_CHECK_PROMO;
        Map<String,String> params = new ArrayMap<>();
        params.put("username", userName );
        params.put("code", promo);
        inetRequest.RequestParams = params;
        inetRequest.object = this;
        inetRequest.url = URL_WEB_SERVER;
        inetRequest.method = InternetTXRX.METHOD_GET;
        inetReq.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, inetRequest);
    }

    public void getVihodCoutn(){
        InternetTXRX inetReq = new InternetTXRX();
        InternetTXRX.InetRequest inetRequest = inetReq.new InetRequest();
        inetRequest .internalTypeOfRequest = InternetTXRX.INTENAL_TYPE_REQUEST_GET_VIHOD;
        Map<String,String> params = new ArrayMap<>();
        params.put("posts","1");
        params.put("username",userName);
        inetRequest.RequestParams = params;
        inetRequest.object = this;
        inetRequest.url = URL_WEB_SERVER;
        inetRequest.method = InternetTXRX.METHOD_GET;
        inetReq.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, inetRequest);
    }

    public void getDataFromAsynstaskPrice(String body, boolean inetIsOff){
        presenter.onGetPrice(body,inetIsOff);
    }

    public void getDataFromAsynstaskPromo(String body, boolean inetIsOff){
        presenter.onPromoFromAsynctask(body,inetIsOff);
    }
    public void getDataFromAsynctaskVihod(String body, boolean inetIsOff){
        presenter.onVihodLoaded(body,inetIsOff);
    }
}