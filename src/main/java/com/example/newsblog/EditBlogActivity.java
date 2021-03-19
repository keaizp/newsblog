package com.example.newsblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsblog.dao.NewsDao;
import com.hdl.mricheditor.bean.CamaraRequestCode;
import com.hdl.mricheditor.view.MRichEditor;

public class EditBlogActivity extends AppCompatActivity implements View.OnClickListener{
    private MRichEditor editor;
    private ImageView back;
    private ImageView send;
    private EditText  titleTxt;
    private MRichEditor contentTxt;
    private String author;
    private NewsDao newsDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_blog);
        editor = findViewById(R.id.mre_editor);
        back = findViewById(R.id.back);
        send = findViewById(R.id.send);
        titleTxt = findViewById(R.id.et_main_title);
        contentTxt = findViewById(R.id.mre_editor);
        back.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.send:
                send();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消操作", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == CamaraRequestCode.CAMARA_GET_IMG) {
            editor.insertImg(data.getData());
        } else if (requestCode == CamaraRequestCode.CAMARA_TAKE_PHOTO) {
            editor.insertImg(data);
        }
    }

    public void send(){
        Intent intent = getIntent();
        author = intent.getStringExtra("username");

        newsDao = new NewsDao(this,"News.db",null,1);
        newsDao.getDataBase(newsDao);

        String title = titleTxt.getText().toString();
        contentTxt.createHtmlStr();
        String content = contentTxt.getHtmlStr();

        newsDao.saveNews(title,content,author);

        Toast.makeText(this,"文章发送成功",Toast.LENGTH_SHORT).show();
    }
}
