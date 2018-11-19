package com.newsmanager.web.dao;

import com.newsmanager.web.dto.AmountOfDataPerDayDto;
import com.newsmanager.web.dto.NewsWithTheMostWordDto;
import com.newsmanager.web.dto.TopTenLabelDto;

import java.sql.SQLException;
import java.util.List;

public interface StatisticsDao {

    List<TopTenLabelDto> findTopTenPopularLabel() throws SQLException;

    NewsWithTheMostWordDto findNewsWithTheMostWord() throws SQLException;

    List<AmountOfDataPerDayDto> findCharactersAveragePerDay() throws SQLException;

    List<AmountOfDataPerDayDto> findNumberOfNewsPerDay() throws SQLException;

}
