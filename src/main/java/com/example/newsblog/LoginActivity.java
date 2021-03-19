package com.example.newsblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsblog.dao.UserDao;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userTxt;
    private EditText pwdTxt;
    private Button loginBtn;
    private Button registerBtn;
    private String username;
    private String pwd;
    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();

    }

    public void init(){
        userTxt = findViewById(R.id.username);
        pwdTxt = findViewById(R.id.pwd);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                login();
                break;
            case R.id.registerBtn:
                register();
                break;
        }
    }

    public void login(){
        userDao = new UserDao(this,"blog.db",null,1);
        userDao.getDataBase(userDao);
        username = userTxt.getText().toString();
        pwd = pwdTxt.getText().toString();
        if(!userDao.queryUser(username)){
            Toast.makeText(this,"用户名不存在！",Toast.LENGTH_SHORT).show();
            userTxt.setText("");
            pwdTxt.setText("");
        }else if(userDao.getPassword(username).equals(pwd)){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("pwd",pwd);
            startActivityForResult(intent,Constants.LOGIN_REQUEST);
        }else {
            Toast.makeText(this,"密码错误！",Toast.LENGTH_SHORT).show();
            userTxt.setText("");
            pwdTxt.setText("");
        }
    }

    public void register(){
        startActivityForResult(new Intent(LoginActivity.this,RegisterActivity.class),Constants.REGISTER_REQUEST);
    }
}
