package com.fire.fire.postandcommenttutorial.models;

import java.io.Serializable;

/**
 * Model for post data to be parsed by JSON and sent to a database in Google Firebase
 */

public class Post implements Serializable {
    private User user;
    private String postText;
    private String postImageUrl;
    private String postId;
    private String bookCourse;
    private String bookTitle;
    private String bookAuthor;
    private String bookISBN;
    private String bookPrice;
    private Boolean bookPaperback;
    private long numLikes;
    private long numComments;
    private long timeCreated;

    public Post() {
    }

    public Post(User user, String postText, String postImageUrl, String postId, String bookCourse, String bookTitle,
                String bookAuthor, String bookISBN, String bookPrice, Boolean bookPaperback, long numLikes, long numComments, long timeCreated) {

        this.user = user;
        this.postText = postText;
        this.postImageUrl = postImageUrl;
        this.postId = postId;
        this.bookCourse = bookCourse;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookISBN = bookISBN;
        this.bookPrice = bookPrice;
        this.bookPaperback = bookPaperback;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.timeCreated = timeCreated;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getBookCourse() { return bookCourse; }

    public void setBookCourse(String bookCourse) {this.bookCourse = bookCourse;}

    public String getBookTitle() { return bookTitle; }

    public void setBookTitle(String bookTitle) {this.bookTitle = bookTitle;}

    public String getBookAuthor() { return bookAuthor; }

    public void setBookAuthor(String bookAuthor) { this.bookAuthor=bookAuthor;}

    public String getBookISBN() {return bookISBN;}

    public void setBookISBN(String bookISBN) {this.bookISBN = bookISBN;}

    public String getBookPrice() {return bookPrice;}

    public void setBookPrice(String bookPrice) {this.bookPrice = bookPrice;}

    public Boolean getBookPaperback() {return bookPaperback;}

    public void setBookPaperback(Boolean bookPaperback) {this.bookPaperback = bookPaperback;}

    public long getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(long numLikes) {
        this.numLikes = numLikes;
    }

    public long getNumComments() {
        return numComments;
    }

    public void setNumComments(long numComments) {
        this.numComments = numComments;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }
}
