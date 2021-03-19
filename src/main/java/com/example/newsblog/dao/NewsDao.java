package com.example.newsblog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.newsblog.NewsInfo;

import java.util.ArrayList;
import java.util.List;

public class NewsDao extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private List<NewsInfo> list;
    private NewsInfo news;
    private String sql;
    private final String CREATE_TABLE="create table news(id integer primary key autoincrement,title text,content text,author varchar(20))";
    public NewsDao(Context context, String name, SQLiteDatabase.CursorFactory factory,
                   int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("keaizp","升级数据库") ;
    }

    public void getDataBase(NewsDao myDataBase){
        db = myDataBase.getWritableDatabase();
    }

    public List<NewsInfo> queryAllNews(){
        //Date date = new Date();
       // String time = date.toString();
        //System.out.println(time);


        sql = "select * from news";
        Cursor cursor = db.rawQuery(sql,new String[]{});
        list = new ArrayList<>();
        while (cursor.moveToNext()){
            news = new NewsInfo();
            news.setTitle(cursor.getString(1));
            news.setContent(cursor.getString(2));
            news.setAuthor(cursor.getString(3));
            list.add(news);
        }
        return list;
    }

    public NewsInfo queryOneNews( String title){
        //Date date = new Date();
        //String time = date.toString();
        //System.out.println(time);

        sql = "select * from news where title=?";
        Cursor cursor = db.rawQuery(sql,new String[]{title});
        list = new ArrayList<>();
        while (cursor.moveToNext()){
            news = new NewsInfo();
            news.setTitle(cursor.getString(1));
            news.setContent(cursor.getString(2));
        }
        return news;
    }

    public void saveNews(String title,String content,String author){
        sql = "insert into news(title,content,author) values(?,?,?)";
        db.execSQL(sql,new Object[]{title,content,author});
    }
}
