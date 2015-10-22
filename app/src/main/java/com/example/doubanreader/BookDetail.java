package com.example.doubanreader;

import java.io.Serializable;

/**
 * Created by zhaojian26 on 15-10-11.
 */
public class BookDetail implements Serializable {
    private String imageViewUrl;
    private String author;
    private String number;
    private String authorInfo;
    private String catalog;

    public BookDetail(String imageViewUrl, String author, String number, String authorInfo, String catalog){
        this.imageViewUrl = imageViewUrl;
        this.author = author;
        this.number = number;
        this.authorInfo = authorInfo;
        this.catalog = catalog;
    }

    public String getImageViewUrl(){return imageViewUrl;}
    public String getAuthor(){return  author;}
    public String getNumber(){return number;}
    public String getAuthorInfo(){return authorInfo;}
    public String getCatalog(){return catalog;}
}
