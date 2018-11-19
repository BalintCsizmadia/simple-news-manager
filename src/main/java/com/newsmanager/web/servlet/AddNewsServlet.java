package com.newsmanager.web.servlet;

import com.newsmanager.web.dao.NewsDao;
import com.newsmanager.web.dao.database.DatabaseNewsDao;
import com.newsmanager.web.service.NewsService;
import com.newsmanager.web.service.exception.TitleAlreadyExistsException;
import com.newsmanager.web.service.simple.SimpleNewsService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/add-news")
public final class AddNewsServlet extends AbstractServlet {

    private final static Logger logger = Logger.getLogger(AddNewsServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            NewsDao newsDao = new DatabaseNewsDao(connection);
            NewsService newsService = new SimpleNewsService(newsDao);
            String title = req.getParameter("title");
            String[] labels = req.getParameter("labels").split(",");
            String content = req.getParameter("content");

            newsService.add(title, content, labels);
            logger.info("News added");

        } catch (TitleAlreadyExistsException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            logger.warn("Attempt to insert an already exist title");
        } catch (SQLException e) {
            handleSqlError(resp, e);
        }
    }
}