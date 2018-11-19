package com.newsmanager.web.dto;

public final class NewsWithTheMostWordDto {

    private final int id;
    private final String title;
    private final int numberOfWords;

    public NewsWithTheMostWordDto(int id, String title, int length) {
        this.id = id;
        this.title = title;
        this.numberOfWords = length;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }
}
