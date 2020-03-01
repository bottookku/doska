package com.example.botto.myapplication.service;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import com.example.botto.myapplication.tools.AbstractHandlerBuildingRequest;
import com.example.botto.myapplication.tools.Error;
import com.example.botto.myapplication.tools.InternetTXRX;
import com.example.botto.myapplication.tools.Sql;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static com.example.botto.myapplication.tools.Sql.TABLE_POST;
import static com.example.botto.myapplication.tools.Sql.col_post_id;
import static com.example.botto.myapplication.tools.Sql.col_time_next_run;

public class Post extends AbstractHandlerBuildingRequest {
    public final String URL_POSTING ="https://api-doska.ykt.ru/v3/status";
    public static final String CONST_ACTIVATE = "activate";
    public static final String CONST_DEACTIVATE = "deactivate";
    String  action;
    String  coockies;
    Integer postId;
    Integer interval;
    Integer random;
    Integer work_start;
    Integer work_end;
    Integer next_run;
    Boolean isEnabled;
    Boolean isPosted = false;
    String  body;
    String findCookieFromPostId(Integer postId){
        List<ContentValues> d = Sql.select(Sql.TABLE_POST,new String[]{Sql.col_user_id}, Sql.col_post_id + " =?",new String[]{postId.toString()});
        if(d.size()==0){
            return "0";
        }
        Integer userId = d.get(0).getAsInteger(Sql.col_user_id);
        if(userId==null){
            return "null";
        }else {
            Log.e("SSS","SSSA"+userId +"HAAAA ");
            List<ContentValues> d2 = Sql.select(Sql.TABLE_USER, new String[]{Sql.col_cookies}, Sql.col_user_id + " =? ", new String[]{userId.toString()});
            if(d2.size()==0){
                Log.e("SUKA","AAA");
                return "null";
            }
            return d2.get(0).getAsString(Sql.col_cookies);
        }
    }//найти cookie по postid
    public Integer calculateNextRunTime (Integer postId){
        List<ContentValues> post = Sql.select(Sql.TABLE_POST,new String[]{Sql.col_time_last_run, Sql.col_time_next_run, Sql.col_INTERVAL,Sql.col_setting_work_end, Sql.col_setting_work_start, Sql.col_setting_random_interval, Sql.col_post_enabled}, Sql.col_post_id+ " =?",new String[]{postId.toString()});
        interval        = post.get(0).getAsInteger(Sql.col_INTERVAL);
        work_end        = post.get(0).getAsInteger(Sql.col_setting_work_end);
        work_start      = post.get(0).getAsInteger(Sql.col_setting_work_start);
        random          = post.get(0).getAsInteger(Sql.col_setting_random_interval);
        isEnabled       = post.get(0).getAsBoolean(Sql.col_post_enabled);
        Integer curr_time_int   = (int) (System.currentTimeMillis()/1000l);
        Integer randomized;
        if(random > 0 ){
            Random sdd22 = new Random();
            randomized = sdd22.nextInt(random * 60);
        }else {
            randomized = 0;
        }
        Log.e(random.toString(),randomized.toString());
        interval =  interval * 60;
        next_run = interval + curr_time_int + randomized;
        Log.e(next_run.toString(),unixTimeToHumanityDate(next_run));
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, work_end);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        Integer timeRunAfterNight = (int)(date.getTimeInMillis()/1000);
        if(next_run  > timeRunAfterNight){
            Calendar date1 = new GregorianCalendar();
            date1.set(Calendar.HOUR_OF_DAY, work_start);
            date1.set(Calendar.MINUTE, 0);
            date1.set(Calendar.SECOND, 0);
            date1.add(Calendar.DAY_OF_YEAR, 1);

            int time_utro = (int)(date1.getTimeInMillis()/1000);
            next_run = interval  + time_utro + randomized;
        }
        return next_run;
    }//Расчитать следующее время запуска
    public Boolean isTimePosting(Integer postId){
        List<ContentValues> post = Sql.select(Sql.TABLE_POST,new String[]{Sql.col_time_next_run, Sql.col_INTERVAL,Sql.col_setting_work_end, Sql.col_setting_work_start}, Sql.col_post_id+ " =?",new String[]{postId.toString()});
        interval        = post.get(0).getAsInteger(Sql.col_INTERVAL);
        work_end        = post.get(0).getAsInteger(Sql.col_setting_work_end);
        work_start      = post.get(0).getAsInteger(Sql.col_setting_work_start);


        int time_next_run;
        if(post.get(0).getAsInteger(col_time_next_run)==null){
            time_next_run = 0;
        }else {
            time_next_run =  post.get(0).getAsInteger(col_time_next_run);
        }
        Log.e(postId.toString(), unixTimeToHumanityDate(time_next_run));
        int curr_time = (int) (System.currentTimeMillis() / 1000);
        if (time_next_run < curr_time || time_next_run == 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
            String strTime = simpleDateFormat.format(new Date());
            int time = Integer.parseInt(strTime);
            if(time < work_end && time> work_start) {
                return true;
            }else {
                return false;
            }
        }else {
            Log.e("time = ","BBBBBBBBBBBBBBB");
            return false;
        }
    }//Пришли ли время для постинга?
    public void firstPostig(Integer postId){
        posting(postId,false);
    }//Стар постинга первый метод Activate
    void posting(Integer PostId, Boolean isDeactivated){
        postId = PostId;
        InternetTXRX ss = new InternetTXRX();
        InternetTXRX.InetRequest inetRequest = ss.new InetRequest();
        inetRequest.object = this;
        inetRequest.url = URL_POSTING;
        inetRequest.method = InternetTXRX.METHOD_GET;
        Map<String,String> mapParams = new ArrayMap<>();
        mapParams.put("postId",PostId.toString());
        if(isDeactivated) {
            action = CONST_ACTIVATE;
        }else {
            action = CONST_DEACTIVATE;
        }
        mapParams.put("action", action);
        inetRequest.RequestParams = mapParams;
        inetRequest.internalTypeOfRequest = InternetTXRX.INTENAL_TYPE_REQUEST_POSTING;
        Map<String,String> headers = new ArrayMap<>();
        coockies = findCookieFromPostId(PostId);
        if(coockies=="null"){
            Error.Error(Error.CONST_ERROR_USERNAME_NOT_HAVE_IN_TABLE,false,true,false,null);
            return;
        }else if (coockies=="0"){
            Error.Error(Error.CONST_ERROR_USERNAME_NOT_HAVE_IN_TABLE,false,true,false,null);
            return;
        }
        headers.put("Cookie","idCookie=" + coockies);
        inetRequest.HeaderParams = headers;
        Log.e("REQ", inetRequest.RequestParams.toString());
        Log.e("REQ", inetRequest.HeaderParams.toString());
        ss.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,inetRequest);

    }//Сам постинг de/activate определяется boolean
    void actionAfterGoodPosting(){
        int nextruntime = calculateNextRunTime(postId);
        Log.e(postId.toString(),"next run time = " + unixTimeToHumanityDate(nextruntime));
        ContentValues cv = new ContentValues();
        ////ВОЗМОЖНО НАХУЙ НАДО???
        //int currtime = (int)System.currentTimeMillis()/1000;
        //cv.put(col_time_last_run,currtime);
        cv.put(col_time_next_run,nextruntime);
        Sql.update(TABLE_POST, col_post_id + " =? ", new String[]{postId.toString()},cv);
    }//Действия если постинг прошел успешно Активируется readPostBOdy
    String unixTimeToHumanityDate(int timeSeconds){
        Long time;
        time = timeSeconds * 1000l;
        Date dd2 = new Date();
        dd2.setTime(time);
        return  dd2.toString();
    }
    @Override
    public void setterFromAsyncTask(String string, boolean isNotInet) {
        if(isNotInet){
            //Presenter.inetIsNot();
        }
        else {
            this.body = string;
        }
    }
    @Override
    public void actionFromAsyncTask() {
        Log.e("body",body);
        JSONObject ss = null;
        try {
            ss = new JSONObject(body);
            if(ss.get("result").equals("success")) {
                if (action.equals(CONST_DEACTIVATE)) {
                    this.posting(postId, true);
                    Log.e("INFO","IS DEACTIVATED");
                }else if(action.equals(CONST_ACTIVATE)){
                    Log.e("INFO","IS POSTED");
                    isPosted = true;
                    actionAfterGoodPosting();
                }
            }else if(ss.get("result").equals("error")){
                isPosted = false;
                if(ss.getInt("code")==1){
                    Error.Error(ss.getInt("code"), false, true, true, "ID " +postId+ " не соответсвует прользователю");
                }else {
                    Error.Error(ss.getInt("code"),false,true,true,ss.getString("msg") );
                }
            }
        } catch (JSONException e) {
            isPosted = false;
            Error.Error(Error.CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER,false,true,false,null);
        }
    }//Запускается из Asyntask проверяет тело отвера JSON запостилось ли объявление. Если деактивация прошла успешно Оправляет posting с активацией.
}

