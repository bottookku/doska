package com.example.botto.myapplication.service;

import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import com.example.botto.myapplication.tools.Error;
import com.example.botto.myapplication.tools.InternetTXRX;

import java.util.Map;

import static com.example.botto.myapplication.Pay.PayModel.URL_WEB_SERVER;
import static com.example.botto.myapplication.tools.Sql.createTables;



public class PostCountChanger {

    public Post post;
    public Integer postId;
    public Integer count;

    public void changePostCount(String username ){

        createTables();

        /*
        List<ContentValues> cd = select(Sql.TABLE_SAVE,new String[]{Sql.col_save_post_count},null,null);
        if(cd != null && cd.size()!=0){
            count = cd.get(0).getAsInteger(Sql.col_save_post_count);
            if(count==0){
                count = 1;
            }
        }else {
            ContentValues sd = new ContentValues();
            sd.put(Sql.col_save_id,1);
            sd.put(Sql.col_save_post_count,0);
            insert(Sql.TABLE_SAVE,sd);
            count = 1;
        }
        */
        ////////////
        count = 1;
        //////////////////
        InternetTXRX inetReq = new InternetTXRX();
        InternetTXRX.InetRequest inetRequest = inetReq.new InetRequest();
        inetRequest .internalTypeOfRequest = InternetTXRX.INTENAL_TYPE_REQUEST_CAHNGE_COUNT;
        Map<String,String> params = new ArrayMap<>();
        params.put("changepostcount","-"+count.toString());
        params.put("username",username);
        Log.e("11111", params.toString());
        inetRequest.RequestParams = params;
        inetRequest.object = this;
        inetRequest.url = URL_WEB_SERVER;
        inetRequest.method = InternetTXRX.METHOD_GET;
        inetReq.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, inetRequest);
    }



    public void AsynctaskActivity(String body, Boolean isInetOff, PostCountChanger pcch){
        Log.e("lll=",body);
        boolean neDozvonilsya = true;
        if(!isInetOff){
            if(body.equals("1")){
                neDozvonilsya = false;
                pcch.post.firstPostig(pcch.postId);
            }else if(body.equals("0")) {
                neDozvonilsya = false;

                Error.Error(1,false,true,true,"БЛЯДЬ выводы закончились нахуй!"+pcch.postId);

                //////////////////////////////////////////////////
                ////////НОтификация закончился вывод!!!///////////
                ////////НОтификация закончился вывод!!!///////////
                //////////////////////////////////////////////////
            }
        }
        /*
        if(neDozvonilsya){
            String d = "UPDATE " + Sql.TABLE_SAVE +" SET "+ Sql.col_save_post_count + " = " + Sql.col_save_post_count + " + 1 where "+ Sql.col_save_id + " = 1";
            Log.e("neDozvonilsya" , d);
            Sql.sqlQuerry(d);
        }else {
            Log.e("DOZVON" , "!!");
            ContentValues cv = new ContentValues();
            cv.put(Sql.col_save_post_count,0);
            Sql.update(Sql.TABLE_SAVE,null,null,cv);
        }
        */
    }
}
