package com.example.botto.myapplication.tools;

import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import com.example.botto.myapplication.Pay.PayModel;
import com.example.botto.myapplication.service.Post;
import com.example.botto.myapplication.PostList.PostListModel;
import com.example.botto.myapplication.UserLogin.LoginUserModel;
import com.example.botto.myapplication.service.PostCountChanger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class InternetTXRX extends AsyncTask<InternetTXRX.InetRequest,Void,InternetTXRX.InetResponse> {
    public static final int INTENAL_TYPE_REQUEST_POSTING = 1;
    public static final int INTENAL_TYPE_REQUEST_LOGIN = 2;
    public static final int INTENAL_TYPE_REQUEST_GET_LIST_POST = 3;
    public static final int INTENAL_TYPE_REQUEST_GET_PRICE = 4;
    public static final int INTENAL_TYPE_REQUEST_CHECK_PROMO = 5;
    public static final int INTENAL_TYPE_REQUEST_GET_VIHOD = 6;
    public static final int INTENAL_TYPE_REQUEST_CAHNGE_COUNT = 7;
    public static final int METHOD_GET = 1;
    public static final int METHOD_POST = 2;
    final int INTERNET_TIME_OUT = 100000;
    @Override
    protected InetResponse doInBackground(InetRequest... params) {
        InetResponse inetResponse = new InetResponse();
        inetResponse.inetRequest = params[0];
        DataOutputStream dos = null;
        URL url;
        String paramsRequest = params[0].createParamsString(params[0].RequestParams);
        try{
            if(params[0].method == 1){
                url = new URL(params[0].url + "?" + paramsRequest);
            }else {
                url = new URL(params[0].url);
            }
            Log.e("url",url.toString());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
            try {
                urlConnection.setConnectTimeout(INTERNET_TIME_OUT);
                urlConnection.setDoInput(true);
                if(params[0].method == 2) {
                    urlConnection.setDoOutput(true);
                    urlConnection.setInstanceFollowRedirects(false);
                    new DataOutputStream(urlConnection.getOutputStream());
                    dos = new DataOutputStream(urlConnection.getOutputStream());
                    Log.e("INFO",paramsRequest);
                    dos.writeBytes(paramsRequest);
                    dos.flush();
                }
                if(params[0].HeaderParams!=null){
                    if(params[0].HeaderParams.size()>0){
                        for(Map.Entry<String,String> ffd : params[0].HeaderParams.entrySet()){
                            urlConnection.setRequestProperty(ffd.getKey(),ffd.getValue());
                        }
                    }
                }
                if(params[0].isNeedHeaders){
                    inetResponse.responseHeader = urlConnection.getHeaderFields();
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String bodyy = "";
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    bodyy += inputLine;
                in.close();
                inetResponse.responseBody = bodyy;
            } finally {
                if (dos != null) {
                    dos.close();
                }

                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {

        } catch (IOException e) {
            Log.e("SS",e.toString());
            inetResponse.responseBody = "";
            inetResponse.responseHeader = new ArrayMap<String,List<String>>();
            List<String> dd = new ArrayList<>();
            inetResponse.responseHeader.put("",dd);
            inetResponse.isInetOff = true;
        }
        return inetResponse;
    }
    @Override
    protected void onPostExecute(InetResponse inetResponse) {
        super.onPostExecute(inetResponse);
        switch (inetResponse.inetRequest.internalTypeOfRequest) {
            case INTENAL_TYPE_REQUEST_POSTING:
                ((Post) inetResponse.inetRequest.object).setterFromAsyncTask(inetResponse.responseBody,inetResponse.isInetOff);
                if(!inetResponse.isInetOff) {
                    ((Post) inetResponse.inetRequest.object).actionFromAsyncTask();
                }
                break;
            case INTENAL_TYPE_REQUEST_LOGIN:
                ((LoginUserModel) inetResponse.inetRequest.object).setterFromAsyncTask(inetResponse.responseHeader.toString(), inetResponse.isInetOff);
                if(!inetResponse.isInetOff) {
                    ((LoginUserModel) inetResponse.inetRequest.object).actionFromAsyncTask();
                }

                break;
            case INTENAL_TYPE_REQUEST_GET_LIST_POST:
                ((PostListModel) inetResponse.inetRequest.object).setterFromAsyncTask(inetResponse.responseBody,inetResponse.isInetOff);
                if(!inetResponse.isInetOff) {
                    ((PostListModel) inetResponse.inetRequest.object).actionFromAsyncTask();
                }
                break;
            case INTENAL_TYPE_REQUEST_GET_PRICE:
                ((PayModel)inetResponse.inetRequest.object).getDataFromAsynstaskPrice(inetResponse.responseBody,inetResponse.isInetOff);
                break;
            case INTENAL_TYPE_REQUEST_CHECK_PROMO:
                ((PayModel)inetResponse.inetRequest.object).getDataFromAsynstaskPromo(inetResponse.responseBody,inetResponse.isInetOff);
                break;
            case INTENAL_TYPE_REQUEST_GET_VIHOD:
                ((PayModel)inetResponse.inetRequest.object).getDataFromAsynctaskVihod(inetResponse.responseBody,inetResponse.isInetOff);
                break;
            case INTENAL_TYPE_REQUEST_CAHNGE_COUNT:
                ((PostCountChanger)inetResponse.inetRequest.object).AsynctaskActivity(inetResponse.responseBody,inetResponse.isInetOff,(PostCountChanger)inetResponse.inetRequest.object);
                break;
        }
    }
    public class InetRequest{
        public int internalTypeOfRequest;
        public String url;
        public Map<String,String> HeaderParams;
        public Map<String,String> RequestParams;
        public int method;
        public boolean isNeedHeaders = false;
        public Object object;

        String createParamsString(Map<String,String> params){
            Log.e("aaa", object.toString());
            String params2 = "";
            int i = 0;
            for (Map.Entry<String, String> ss : params.entrySet()) {
                try {
                    params2 += ss.getKey() + "=" + URLEncoder.encode(ss.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
                if (params.size() != i) {
                    params2 += "&";
                }
            }
            return params2;
        }

    }
    public class InetResponse{

        public InetRequest inetRequest;
        public boolean isInetOff;
        public String responseBody;
        public Map<String,List<String>> responseHeader;
    }
}
