package com.example.newsblog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsblog.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserDao extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private List<UserInfo> list;
    private  UserInfo user;
    private String sql;

    public UserDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),password VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void getDataBase(UserDao myDataBase){
        db = myDataBase.getWritableDatabase();
    }


    public String getPassword(String name){
        String password;
        sql = "select password from user where name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{name});
        cursor.moveToNext();
        password = cursor.getString(0);
        cursor.close();
        return password;
    }


    public boolean queryUser(String name){
        sql = "select * from user where name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{name});
        if(cursor.getCount()==0){
            return false;
        }
        cursor.close();
        return true;

    }

    public void delete(String name,String password){
        sql = "delete from user where name=? and password=?";
        db.execSQL(sql,new String[]{name,password});
    }

    public void save(String name,String password){
        sql = "insert into user(name,password) values(?,?)";
        db.execSQL(sql,new String[]{name,password});
    }

    public void update(String name1,String name){
        sql = "update user1 set name=?,password=? where name=?";
        db.execSQL(sql,new String[]{name1,name});
    }

    public List<UserInfo> queryOne(String name){
        user = new UserInfo();
/*        sql = "insert into user(name,score) values(?,?)";
        db.execSQL(sql,new Object[]{"wcc","20"});*/

/*        sql = "drop table user";
        db.execSQL(sql);*/

        list = new ArrayList<>();
        sql = "select * from user where name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{name});
        while (cursor.moveToNext()){
            user = new UserInfo();
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            list.add(user);
        }
        cursor.close();
        return list;
    }
}

