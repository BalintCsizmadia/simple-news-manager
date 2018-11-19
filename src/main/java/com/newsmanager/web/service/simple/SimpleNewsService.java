package com.newsmanager.web.service.simple;

import com.newsmanager.web.dao.NewsDao;
import com.newsmanager.web.model.News;
import com.newsmanager.web.service.NewsService;
import com.newsmanager.web.service.exception.NewsIdOutOfRangeException;
import com.newsmanager.web.service.exception.TitleAlreadyExistsException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class SimpleNewsService implements NewsService {

    private final static Logger logger = Logger.getLogger(SimpleNewsService.class);

    private final NewsDao newsDao;

    public SimpleNewsService(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public List<News> getAll() throws SQLException {
        return newsDao.findAll();
    }

    @Override
    public List<News> getAll(String order) throws SQLException {
        if (order != null) {
            if (order.equals("ASC") || order.equals("DESC")) {
                return getAllByOrderByDate(order);
            }
        }
        return getAll();
    }

    @Override
    public News getById(int id) throws SQLException, NewsIdOutOfRangeException {
        return newsDao.findById(id);
    }

    @Override
    public void add(String title, String content, String[] labels) throws SQLException, TitleAlreadyExistsException {
        if (title == null || title.equals("") || content == null || content.equals("") || labels.length < 1) {
            logger.warn("Wrong or missing argument");
            throw new IllegalArgumentException("Wrong or missing argument");
        }
        newsDao.save(title, content, labels);
    }

    private List<News> getAllByOrderByDate(String order) throws SQLException {
        return newsDao.findAllByOrderByDate(order);
    }

}
