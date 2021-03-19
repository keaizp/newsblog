package com.example.newsblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsblog.dao.UserDao;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userTxt;
    private EditText pwdTxt;
    private EditText confirm_PwdTxt;
    private RadioButton radioButton;
    private Button regiBtn;
    private Button backBtn;
    private UserDao userDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();

        regiBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }
    public void initView(){
        userTxt = findViewById(R.id.re_username);
        pwdTxt = findViewById(R.id.re_pwd);
        confirm_PwdTxt = findViewById(R.id.re_confirm_pwd);
        radioButton = findViewById(R.id.radioBtn);
        regiBtn = findViewById(R.id.re_registerBtn);
        backBtn = findViewById(R.id.re_backBtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_registerBtn:
                register();
                break;
            case R.id.re_backBtn:
                finish();
                break;
        }
    }

    public void register(){
        String username = userTxt.getText().toString();
        String pwd = pwdTxt.getText().toString();
        String confirm_pwd = confirm_PwdTxt.getText().toString();
        userDao = new UserDao(this,"blog.db",null,1);
        userDao.getDataBase(userDao);
        if(radioButton.isChecked()&&pwd.equals(confirm_pwd)){
            userDao.save(username,pwd);
            Toast.makeText(RegisterActivity.this,"注册成功!"+username+"  "+pwd,Toast.LENGTH_SHORT).show();
        } else if(!pwd.equals(confirm_pwd)){
            Toast.makeText(RegisterActivity.this,"密码输入错误！",Toast.LENGTH_SHORT).show();
            userTxt.setText("");
            pwdTxt.setText("");
            confirm_PwdTxt.setText("");
        }else{
            Toast.makeText(RegisterActivity.this,"请先确认条款！",Toast.LENGTH_SHORT).show();
            userTxt.setText("");
            pwdTxt.setText("");
            confirm_PwdTxt.setText("");
        }
    }
}
