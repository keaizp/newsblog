package com.example.newsblog;

public class NewsInfo {
    private int id;
    private int icon;
    private String title;
    private String content;
    private String author;

    public NewsInfo(){

    }

    public NewsInfo(int id,int icon,String title,String content,String author){
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public int getId(){
        return id;
    }

    public int getIcon(){
        return icon;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){return content;}

    public String getAuthor(){return  author;}

    public void setAuthor(String author){this.author = author;}

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content){ this.content = content;}
}
