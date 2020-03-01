package com.example.botto.myapplication.PostList;


import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import com.example.botto.myapplication.tools.AbstractHandlerBuildingRequest;
import com.example.botto.myapplication.tools.Error;
import com.example.botto.myapplication.tools.InternetTXRX;
import com.example.botto.myapplication.tools.Sql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.botto.myapplication.tools.Error.CONST_ERROR_THIS_USER_NOT_HAVE_A_COOKIES;
import static com.example.botto.myapplication.tools.Error.CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER;

public class PostListModel extends AbstractHandlerBuildingRequest {

    PostListPresenter presenter;
    PostListModel(PostListPresenter presenter){
        this.presenter = presenter;
    }
    final String CONST_LOGIN_CHECK_URL = "https://api-doska.ykt.ru/v3/profile/posts/active";
    String body;
    String coockies;
    Boolean getListAllPostsOfUser() { //ЕСЛИ пользователя нет возвращает false
        Map<String, String> pk = new ArrayMap<>();
        pk.put("limit", "1000");
        pk.put("offset", "0");
        InternetTXRX inetReq = new InternetTXRX();
        InternetTXRX.InetRequest inetRequest = inetReq.new InetRequest();
        inetRequest.internalTypeOfRequest = InternetTXRX.INTENAL_TYPE_REQUEST_GET_LIST_POST;
        inetRequest.RequestParams = pk;
        inetRequest.object = this;
        inetRequest.url = CONST_LOGIN_CHECK_URL;
        inetRequest.method = InternetTXRX.METHOD_GET;
        Map<String, String> headers = new ArrayMap<>();
        coockies = findCookieFromUserName();
        if(coockies==null){
            /////////////////////////////////////////
            //Error.Error(CONST_ERROR_THIS_USER_NOT_HAVE_A_COOKIES,false,true,false,null);
            /////////////////////////////////////////
            //ЭТО ВНУТРЕННЯ ОШИБКА COOKIE NOT SAVE///
            /////////////////////////////////////////
            return false;
        }else {
            headers.put("Cookie", "idCookie=" + coockies);
            inetRequest.HeaderParams = headers;
            inetReq.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, inetRequest);
            return true;
        }
    }
    class PostsLists {
        public List<String> title;
        public List<Integer> postId;
    }
    String findCookieFromUserName() {

        List<ContentValues> dd = Sql.select(Sql.TABLE_USER, new String[]{Sql.col_cookies}, Sql.col_user_id + " =?", new String[]{presenter.userId.toString()});
        if(dd.size()==0){
            return null;
        }else {
            String ddd = dd.get(0).getAsString(Sql.col_cookies);
            return ddd;
        }
    }
    //Вводим данные из JSON в SQL если их нет
    void applyDataFromJSONToSQL(PostDataArray dd){

        List<Integer> postIdds = new ArrayList<>();
        List<Boolean> enableds = new ArrayList<>();
        List<ContentValues> dds = Sql.select(Sql.TABLE_POST,new String []{Sql.col_post_id,Sql.col_post_enabled},Sql.col_user_id + " =?",new String[]{presenter.userId.toString()});
        dd.userId = presenter.userId;
        for(ContentValues cv2 : dds){
            postIdds.add(cv2.getAsInteger(Sql.col_post_id));
            enableds.add(cv2.getAsBoolean(Sql.col_post_enabled));
        }

        for( PostData dd2 : dd.postDatas){
            Integer postId = dd2.postId;
            Boolean title = dd2.enabled;
            ContentValues sd = new ContentValues();
            if(!postIdds.contains(postId)){
                sd.put(Sql.col_post_enabled,title);
                sd.put(Sql.col_post_id,postId);
                sd.put(Sql.col_user_id,presenter.userId);
                sd.put(Sql.col_post_title,dd2.title);
                Sql.insert(Sql.TABLE_POST, sd);
            }
        }
        presenter.onApplyAllRowsFromJSONinSQL(dd);
    }

    public void setterFromAsyncTask(String string,boolean isNoInet) {
        if(isNoInet){
            presenter.inetIsNot();}
        else {
            this.body = string;
        }
    }
    @Override
    public void actionFromAsyncTask() {
        JSONObject ss = null;
        presenter.data = new PostDataArray();

        try {
            ss = new JSONObject(body);
            if (ss.get("result").equals("success")) {

                Log.e("11","222");

                JSONArray jsonArray = ss.getJSONArray("data");
                if(jsonArray.length()>0){
                    Log.e("11","4444");
                    presenter.data.postDatas = new ArrayList<>();
                    for(int i =0; i< jsonArray.length();i++){
                        Log.e("11","222");
                        PostData data = new PostData();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        data.postId = jsonObject.getInt("id");
                        data.title = jsonObject.getString("title");
                        data.enabled = true;
                        ///////////////////////////////////////
                        /////////ПО УМОЛЧАНИЮ ВКЛЮЧЕННЫ ВСЕ ОБЪЯВЛЕНИЯ ПОСЛЕ ЗАГРУЗКИ
                        ////////////////////////////////////////
                        presenter.data.postDatas.add(data);
                        Log.e("11","222" + jsonObject.getInt("id"));

                    }
                }else {
                    //ПУСТОЙ МАССИВ ВСЕ ВРЕНО НО МАССИВ ПУСТОЙ,
                }
            }else if(ss.get("result").equals("error")){
                presenter.data.descrioptionNumber =ss.getInt("code");
                presenter.data.isErrorFromServer = true;
                presenter.data.isHaveError = true;
                presenter.data.errorMsgFromServer =ss.getString("msg");
            }
        } catch (JSONException e) {
            presenter.data.descrioptionNumber = CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER;
            presenter.data.isHaveError = true;
        }

        presenter.onLoadedFromModel(presenter.data);
    }
    public void endbledPost(PostData data){
        ContentValues cv = new ContentValues();
        cv.put(Sql.col_post_enabled,  data.enabled);
        Sql.update(Sql.TABLE_POST,Sql.col_post_id + " =?", new String[]{data.postId.toString()},cv);


        List<ContentValues> dd = Sql.select(Sql.TABLE_POST,new String []{Sql.col_post_id,Sql.col_post_enabled},Sql.col_user_id + " =?",new String[]{presenter.userId.toString()});
        for(ContentValues cv2 : dd){
            Log.e("AA", cv2.toString());

        }

    }
    //вводим ДАННЫЕ ИЗ sql d PostDataArray
    public void buildDataPostArrayFromSQL(PostDataArray postDataArray){
        List<ContentValues> dd = Sql.select(Sql.TABLE_POST,new String []{Sql.col_post_id,Sql.col_post_enabled},Sql.col_user_id + " =?",new String[]{postDataArray.userId.toString()});
        for(ContentValues cv2 : dd){

            Integer aa = cv2.getAsInteger(Sql.col_post_id);
            for (PostData pd :postDataArray.postDatas) {
                Integer ff = pd.postId;
                if (aa.equals(ff)) {
                    pd.enabled = cv2.getAsBoolean(Sql.col_post_enabled);
                }
            }

        }
        presenter.onLoadDataFromSQL(postDataArray);
    }
    public boolean isNoHaveRowsThisUserPostInSql(){
        return Sql.select(Sql.TABLE_POST,new String []{Sql.col_post_id},Sql.col_user_id + " =?",new String[]{presenter.userId.toString()}).isEmpty();
    }


    public void buildPostDataFromSql(){
        PostDataArray postDataArray = new PostDataArray();
        postDataArray.postDatas = new ArrayList<>();
        List<ContentValues> dd = Sql.select(Sql.TABLE_POST,new String []{Sql.col_post_id,Sql.col_post_enabled,Sql.col_post_title},Sql.col_user_id + " =?",new String[]{presenter.userId.toString()});
        for(ContentValues cv2 : dd){


            PostData postData = new PostData();
            postData.title = cv2.getAsString(Sql.col_post_title);
            postData.postId =cv2.getAsInteger(Sql.col_post_id);
            postData.enabled = cv2.getAsBoolean(Sql.col_post_enabled);
            Log.e(postData.enabled+"", "asaaaaaaaaaaaaaaaaaaaaaa");

            postDataArray.postDatas.add(postData);

        }
        presenter.onLoadDataFromSQL(postDataArray);
    }

}
