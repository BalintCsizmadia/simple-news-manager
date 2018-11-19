package com.newsmanager.web.service.simple;

import com.newsmanager.web.dao.StatisticsDao;
import com.newsmanager.web.dto.AmountOfDataPerDayDto;
import com.newsmanager.web.dto.NewsWithTheMostWordDto;
import com.newsmanager.web.dto.TopTenLabelDto;
import com.newsmanager.web.service.StatisticsService;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class SimpleStatisticsService implements StatisticsService {

    private final static Logger logger = Logger.getLogger(SimpleStatisticsService.class);

    private final StatisticsDao statisticsDao;

    public SimpleStatisticsService(StatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    @Override
    public List<TopTenLabelDto> getTopTenPopularLabel() throws SQLException {
        return statisticsDao.findTopTenPopularLabel();
    }

    private NewsWithTheMostWordDto getNewsWithTheMostWord() throws SQLException {
        return statisticsDao.findNewsWithTheMostWord();
    }

    private List<AmountOfDataPerDayDto> getCharactersAveragePerDay() throws SQLException {
        return statisticsDao.findCharactersAveragePerDay();
    }

    private List<AmountOfDataPerDayDto> getNumberOfNewsPerDay() throws SQLException {
        return statisticsDao.findNumberOfNewsPerDay();
    }

    @Override
    public Object statisticsSelector(String option) throws SQLException {
        Object statistics = null;
        switch (option) {
            case "top":
                statistics = getTopTenPopularLabel();
                break;
            case "most":
                statistics = getNewsWithTheMostWord();
                break;
            case "avg":
                statistics = getCharactersAveragePerDay();
                break;
            case "day":
                statistics = getNumberOfNewsPerDay();
                break;
        }
        if (statistics == null) {
            logger.warn("Wrong or missing argument");
            throw new IllegalArgumentException("Wrong or missing argument");
        } else {
            return statistics;
        }
    }
}
