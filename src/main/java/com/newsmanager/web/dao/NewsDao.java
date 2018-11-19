package com.newsmanager.web.dao;

import com.newsmanager.web.model.News;
import com.newsmanager.web.service.exception.NewsIdOutOfRangeException;
import com.newsmanager.web.service.exception.TitleAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;

public interface NewsDao {

    List<News> findAll() throws SQLException;

    List<News> findAllByOrderByDate(String order) throws SQLException;

    News findById(int id) throws SQLException, NewsIdOutOfRangeException;

    void save(String title, String content, String[] labels) throws SQLException, TitleAlreadyExistsException;

}
