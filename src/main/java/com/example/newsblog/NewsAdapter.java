package com.example.newsblog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    List<NewsInfo> newsList;
    Context context;

    public NewsAdapter(Context context,List<NewsInfo> newsList){
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if(view==null){
            view=  LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        }
        ImageView imageView=(ImageView)view.findViewById(R.id.image);
        TextView title=(TextView)view.findViewById(R.id.title);
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        title.setText(newsList.get(i).getTitle());
        return view;
    }
}
