package com.example.doubanreader;

/**
 * Created by zhaojian26 on 15-10-5.
 */
public class BookData {
    private String id;
    private String url;
    private String title;
    private String image;
    private String subTitle;
    private String itemAuthor;
    private String itemNumber;
    private String itemPeople;
    private String summary;

    public BookData(String id, String url, String title, String image, String subTitle, String itemAuthor, String itemNumber, String itemPeople, String summary){
        this.id = id;
        this.title = title;
        this.url = url;
        this.image = image;
        this.subTitle = subTitle;
        this.itemAuthor = itemAuthor;
        this.itemNumber = itemNumber;
        this.itemPeople = itemPeople;
        this.summary = summary;
    }

    public String getId(){return id;}
    public String getUrl(){return url;}
    public String getTitle(){return title;}
    public String getImage(){return image;}
    public String getSubTitle(){return subTitle;}
    public String getItemAuthor(){return itemAuthor;}
    public String getItemNumber(){return itemNumber;}
    public String getItemPeople(){return itemPeople;}
    public String getSummary(){return summary;}
    }

