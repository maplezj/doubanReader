package com.example.doubanreader;

/**
 * Created by zhaojian26 on 15/10/31.
 */
public class BookReviewData {
    private String reviewStar;
    private String reviewAuthor;
    private String reviewTime;
    private String reviewSupport;
    private String reviewComment;
    private String reviewContent;

    public BookReviewData(String reviewStar, String reviewAuthor, String reviewTime, String reviewSupport, String reviewComment,String reviewContent){
        this.reviewStar = reviewStar;
        this.reviewAuthor = reviewAuthor;
        this.reviewTime = reviewTime;
        this.reviewSupport = reviewSupport;
        this.reviewComment = reviewComment;
        this.reviewContent = reviewContent;
    }

    public String getReviewStar(){return reviewStar;}
    public String getReviewAuthor(){return reviewAuthor;}
    public String getReviewTime(){return reviewTime;}
    public String getReviewSupport(){return reviewSupport;}
    public String getReviewComment(){return reviewComment;}
    public String getReviewContent(){return  reviewContent;}
}
