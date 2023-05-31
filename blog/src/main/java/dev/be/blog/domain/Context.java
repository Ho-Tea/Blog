package dev.be.blog.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Context {

    private String title;
    private String text;
    private String updatedDate;
    private final String createdDate;

    private Context(String title, String text){
        this.title = title;
        this.text = text;
        this.createdDate = calculateTime();
    }

    private String calculateTime(){
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        // Timestamp to String
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentTimestamp);
    }

    public static Context write(String title, String text){
        return new Context(title, text);
    }

    public void editTitle(String title){
        this.title = title;
        this.updatedDate = calculateTime();
    }
    public void editText(String text){
        this.text = text;
        this.updatedDate = calculateTime();
    }
    public void edit(String title, String text){
        this.title = title;
        this.text = text;
        this.updatedDate = calculateTime();
    }
    public void print(){
        System.out.println("======================");
        System.out.println("Title : " + title);
        System.out.println("Text : " + text);
        System.out.println("Created : " + createdDate);
        System.out.println("Updated : " + updatedDate);
        System.out.println("======================");
    }
}

