package com.newsmanager.web.servlet;

import com.newsmanager.web.dao.NewsDao;
import com.newsmanager.web.dao.database.DatabaseNewsDao;
import com.newsmanager.web.model.News;
import com.newsmanager.web.service.NewsService;
import com.newsmanager.web.service.simple.SimpleNewsService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


@WebServlet("/all-news")
public final class AllNewsServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            NewsDao newsDao = new DatabaseNewsDao(connection);
            NewsService newsService = new SimpleNewsService(newsDao);

            String order = req.getParameter("order");
            List<News> news = newsService.getAll(order);
            // Send back an ordered list to the frontend
            sendMessage(resp, HttpServletResponse.SC_OK, news);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            NewsDao newsDao = new DatabaseNewsDao(connection);
            NewsService newsService = new SimpleNewsService(newsDao);

            List<News> news = newsService.getAll();
            // After reverse the user sees the latest elements first from the list
            Collections.reverse(news);

            sendMessage(resp, HttpServletResponse.SC_OK, news);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
