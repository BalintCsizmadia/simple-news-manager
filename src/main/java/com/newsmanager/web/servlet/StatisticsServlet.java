package com.newsmanager.web.servlet;

import com.newsmanager.web.dao.StatisticsDao;
import com.newsmanager.web.dao.database.DatabaseStatisticsDao;
import com.newsmanager.web.dto.TopTenLabelDto;
import com.newsmanager.web.service.StatisticsService;
import com.newsmanager.web.service.simple.SimpleStatisticsService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/statistics")
public class StatisticsServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            StatisticsDao statisticsDao = new DatabaseStatisticsDao(connection);
            StatisticsService statisticsService = new SimpleStatisticsService(statisticsDao);
            // Valid options: 'top'; 'most'; 'avg'; 'day';
            String option = req.getParameter("option");

            sendMessage(resp, HttpServletResponse.SC_OK, statisticsService.statisticsSelector(option));
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            StatisticsDao statisticsDao = new DatabaseStatisticsDao(connection);
            StatisticsService statisticsService = new SimpleStatisticsService(statisticsDao);
            // The default query
            List<TopTenLabelDto> labelDtoList = statisticsService.getTopTenPopularLabel();

            sendMessage(resp, HttpServletResponse.SC_OK, labelDtoList);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
