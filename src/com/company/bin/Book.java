package com.company.bin;

import java.util.List;

public class Book{
    private String bookName;
    private String author;
    private double rating;
    private String langCode;
    private int pages;
    private String yearPublish;

    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLangCode() {
        return langCode;
    }
    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getYearPublish() {
        String year[] = yearPublish.split("/");
        int yearInt[] = new int[year.length];
        for (int i = 0; i < year.length; i++){
            yearInt[i] = Integer.parseInt(year[i]);
        }
        return yearInt[2];
    }
    public void setYearPublish(String yearPublish) {
        this.yearPublish = yearPublish;
    }

    @Override
    public String toString() {
        return "\nКнига:\n" +
                "- Название: '" + getBookName() + '\'' + "\n" +
                "- Автор: '" + getAuthor() + '\'' + "\n" +
                "- Рейтинг: " + getRating() + "\n" +
                "- Код языка: '" + getLangCode() + '\'' + "\n" +
                "- Страницы: " + getPages() + "\n" +
                "- Год публикации: " + getYearPublish();
    }

}
