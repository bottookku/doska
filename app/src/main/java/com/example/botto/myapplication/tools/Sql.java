package com.example.botto.myapplication.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_NULL;
import static android.database.Cursor.FIELD_TYPE_STRING;
import static com.example.botto.myapplication.service.AlarmReceiver.context;

public class Sql extends SQLiteOpenHelper {

    public Sql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    static public String TABLE_POST = "db_posting";
    static public String TABLE_USER = "db_user";
    static public String TABLE_SAVE = "db_save";



    static public String col_save_id = "save_id";
    static public String col_save_post_count = "save_count";




    static public String col_post_title = "post_title";
    static public String col_setting_random_interval = "random_interval";
    static public String col_setting_work_start = "work_start";
    static public String col_setting_work_end = "work_end";
    static public String col_post_id = "post_id";
    static public String col_post_enabled = "enabled";
    static public String col_INTERVAL = "INTERVAL";
    static public String col_time_last_run = "time_last_run";
    static public String col_time_next_run = "time_next_run";
    static public String col_user_id = "user_id";
    static public String col_user_name = "user_name";
    static public String col_user_pass = "user_pass";
    static public String col_cookies = "cookies";
    static public String col_item_number = "numb"; //МЕСТО В КОТОРОМ ОН НАХОДИТСЯ нужен чтобы не путать при нажатии ок попытку добавления новой записи с таким же айди или попытку обновления записи которая есть






    public Sql(Context context)  {
        super(context, "SUKA", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_POST + "(" +
                col_post_id +" INTEGER PRIMARY KEY," +
                col_INTERVAL +" INTEGER DEFAULT 180," +
                col_post_title + " TEXT,"+
                col_time_last_run + " INTEGER," +
                col_time_next_run + " INTEGER," +
                col_user_id + " INTEGER,"+
                col_item_number + " INTEGER," +
                col_post_enabled + " BOOLEAN DEFAULT TRUE," +
                col_setting_random_interval + " INTEGER DEFAULT 60," +
                col_setting_work_start + " INTEGER DEFAULT 8," +
                col_setting_work_end + " INTEGER DEFAULT 22)";

        db.execSQL(script);
        String script2 = "CREATE TABLE "+ TABLE_USER + "(" +
                col_user_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                col_user_name + " TEXT UNIQUE,"+
                col_user_pass +" TEXT,"+
                col_cookies + " TEXT);";
        db.execSQL(script2);


        String script3 = "CREATE TABLE "+ TABLE_SAVE + " (" +
                col_save_id + " INTEGER PRIMARY KEY,"+
                col_save_post_count + " INTEGER)";

        db.execSQL(script3);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);

        onCreate(db);
    }



    public static void createTables(){

        //final Sql sdf = new Sql(context);
        //SQLiteDatabase db = sdf.getReadableDatabase();


        //db.execSQL(script3);
        ///db.close();
        //sdf.close();

    }

    public static List<ContentValues> select(String nameOfTable, String[] cols, String selection ,String[] selectionArgs){

        ///selection "name=? AND email=?"
        //ARGS new String[]{"vasya","asd@mail.ru"}
        List<ContentValues> d = new ArrayList<>();
        final Sql sdf1 = new Sql(context);
        sdf1.getReadableDatabase();
        SQLiteDatabase sql = sdf1.getReadableDatabase();
        //Sql.execSQL("REPLACE INTO " + TABLE_SETTING + " VALUES (1,15,8,22)");
        Cursor sddd = sql.query(nameOfTable,cols, selection ,selectionArgs,null,null,null);
        Log.e("INFO","STROK = "+sddd.getCount());
        while(sddd.moveToNext()){
            ContentValues dd = new ContentValues();
            for(int i = 0 ; i< sddd.getColumnCount() ; i++) {
                switch (sddd.getType(i)) {
                    case FIELD_TYPE_NULL:
                        dd.putNull(sddd.getColumnName(i));
                        break;
                    case FIELD_TYPE_FLOAT:
                        dd.put(sddd.getColumnName(i), sddd.getFloat(i));
                        break;
                    case FIELD_TYPE_INTEGER:
                        dd.put(sddd.getColumnName(i), sddd.getInt(i));
                        break;
                    case FIELD_TYPE_STRING:
                        dd.put(sddd.getColumnName(i), sddd.getString(i));
                        break;
                }
            }
            d.add(dd);
        }
        sddd.close();
        sql.close();
        sdf1.close();
        return d;
    }
    public static int update(String tableName, String selection, String[] selecyionArgs, ContentValues sd){
        final Sql sdf = new Sql(context);
        int id;
        SQLiteDatabase db = sdf.getReadableDatabase();
        id = db.update(tableName,sd, selection,selecyionArgs);
        db.close();
        sdf.close();
        return id;
    }

    public static void sqlQuerry(String string){
        final Sql sdf = new Sql(context);
        SQLiteDatabase db = sdf.getReadableDatabase();
        db.execSQL(string);
        db.close();
        sdf.close();
    }

    public static Long insert(String tableName, ContentValues sd){
        Long id ;
        final Sql sdf = new Sql(context);
        SQLiteDatabase db = sdf.getReadableDatabase();
        id = db.insert(tableName,null,sd);
        db.close();
        sdf.close();
        return id;
    }
    public static Long replace(String tableName, ContentValues sd){
        Long id ;
        final Sql sdf = new Sql(context);
        SQLiteDatabase db = sdf.getReadableDatabase();
        id = db.replace(tableName,null,sd);
        db.close();
        sdf.close();
        return id;
    }
    public static int delete(String tableName, String whereClause, String[] whereArgs){
        int id;
        final Sql sdf = new Sql(context);
        SQLiteDatabase db = sdf.getReadableDatabase();
        id = db.delete(tableName,whereClause,whereArgs);
        db.close();
        sdf.close();
        return id;
    }



}