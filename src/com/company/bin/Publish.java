package com.company.bin;

import java.util.ArrayList;
import java.util.List;

public class Publish {
    private String publishName;
    private List<Book> bookPublish = new ArrayList<>();

    public Publish(){
        this.publishName = "";
    }
    public Publish(String name){
        this.publishName = name;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

    public void setBookPublish(Book book) {
        this.bookPublish.add(book);
    }

    public List<Book> getBookPublish() {
        return bookPublish;
    }

    @Override
    public String toString() {
        return "\nИздательство " + getPublishName() + getBookPublish();
    }
}
