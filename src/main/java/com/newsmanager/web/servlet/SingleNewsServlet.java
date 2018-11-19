package com.newsmanager.web.servlet;

import com.newsmanager.web.dao.LabelDao;
import com.newsmanager.web.dao.NewsDao;
import com.newsmanager.web.dao.database.DatabaseLabelDao;
import com.newsmanager.web.dao.database.DatabaseNewsDao;
import com.newsmanager.web.model.Label;
import com.newsmanager.web.model.News;
import com.newsmanager.web.service.LabelService;
import com.newsmanager.web.service.NewsService;
import com.newsmanager.web.service.exception.NewsIdOutOfRangeException;
import com.newsmanager.web.service.simple.SimpleLabelService;
import com.newsmanager.web.service.simple.SimpleNewsService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/single-news")
public final class SingleNewsServlet extends AbstractServlet {

    private final static Logger logger = Logger.getLogger(SingleNewsServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            NewsDao newsDao = new DatabaseNewsDao(connection);
            NewsService newsService = new SimpleNewsService(newsDao);

            LabelDao labelDao = new DatabaseLabelDao(connection);
            LabelService labelService = new SimpleLabelService(labelDao);

            int id = Integer.parseInt(req.getParameter("id"));
            News singleNews = newsService.getById(id);

            List<Label> labels = labelService.getAllByNewsId(id);
            singleNews.setLabels(labels);

            sendMessage(resp, HttpServletResponse.SC_OK, singleNews);
        } catch (NewsIdOutOfRangeException o) {
            logger.info(("(" + HttpServletResponse.SC_NOT_FOUND + ") - Attempt to reach a non-existent content"));
            sendMessage(resp, HttpServletResponse.SC_NOT_FOUND, o.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
