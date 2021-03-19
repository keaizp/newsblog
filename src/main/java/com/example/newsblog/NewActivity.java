package com.example.newsblog;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity {
    private TextView titleTxt;
    private TextView authorTxt;
    private ImageView Img;
    private TextView contentTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        init();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String content = intent.getStringExtra("content");

        titleTxt.setText(title);
        authorTxt.setText(author);
        contentTxt.setText(Html.fromHtml(content));
    }
    public void init(){
        titleTxt = findViewById(R.id.News_title);
        authorTxt = findViewById(R.id.News_author);
        contentTxt = findViewById(R.id.News_content);
    }
}
