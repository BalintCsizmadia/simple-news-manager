package com.newsmanager.web.service;

import com.newsmanager.web.model.News;
import com.newsmanager.web.service.exception.NewsIdOutOfRangeException;
import com.newsmanager.web.service.exception.TitleAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;

public interface NewsService {

    List<News> getAll() throws SQLException;

    List<News> getAll(String order) throws SQLException;

    News getById(int id) throws SQLException, NewsIdOutOfRangeException;

    void add(String title, String content, String[] labels) throws SQLException, TitleAlreadyExistsException;

}
