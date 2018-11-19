package com.newsmanager.web.dao.database;

import com.newsmanager.web.dao.StatisticsDao;
import com.newsmanager.web.dto.AmountOfDataPerDayDto;
import com.newsmanager.web.dto.NewsWithTheMostWordDto;
import com.newsmanager.web.dto.TopTenLabelDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseStatisticsDao extends AbstractDao implements StatisticsDao {

    public DatabaseStatisticsDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<TopTenLabelDto> findTopTenPopularLabel() throws SQLException {
        List<TopTenLabelDto> news = new ArrayList<>();
        String sql = "SELECT labels.name, COUNT(labels.id) AS presence_in_news FROM news\n" +
                "JOIN news_labels AS nt ON news.id = nt.news_id\n" +
                "JOIN labels ON nt.label_id = labels.id\n" +
                "GROUP BY labels.id\n" +
                "ORDER BY presence_in_news DESC\n" +
                "LIMIT 10;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                news.add(fetchTopTenLabelDto(resultSet));
            }
            return news;
        }
    }

    @Override
    public NewsWithTheMostWordDto findNewsWithTheMostWord() throws SQLException {
        String sql = "SELECT id, title, SUM(array_length(regexp_split_to_array(content, '\\s'),1)) AS len FROM news\n" +
                "GROUP BY id\n" +
                "ORDER BY len DESC\n" +
                "LIMIT 1";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return fetchNewsWithTheLongestWordDto(resultSet);
            }
        }
        return null;
    }

    @Override
    public List<AmountOfDataPerDayDto> findCharactersAveragePerDay() throws SQLException {
        List<AmountOfDataPerDayDto> characterAvgPerDayDtos = new ArrayList<>();
        String sql = "SELECT post_date, ROUND(AVG(LENGTH(content)),2) AS amount FROM news AS ou\n" +
                "WHERE (SELECT COUNT(post_date) FROM news AS inr\n" +
                "WHERE inr.post_date = ou.post_date) >= 1\n" +
                "GROUP BY post_date";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                characterAvgPerDayDtos.add(fetchCharacterAvgPerDayDto(resultSet));
            }
            return characterAvgPerDayDtos;
        }
    }

    @Override
    public List<AmountOfDataPerDayDto> findNumberOfNewsPerDay() throws SQLException {
        List<AmountOfDataPerDayDto> numberOfNewsPerDayDtos = new ArrayList<>();
        String sql = "SELECT post_date, COUNT(title) AS amount FROM news AS ou\n" +
                "WHERE (SELECT COUNT(post_date) FROM news AS inr\n" +
                "WHERE inr.post_date = ou.post_date) >= 1\n" +
                "GROUP BY post_date";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                numberOfNewsPerDayDtos.add(fetchCharacterAvgPerDayDto(resultSet));
            }
        }
        return numberOfNewsPerDayDtos;
    }

    private TopTenLabelDto fetchTopTenLabelDto(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int occurrences = resultSet.getInt("presence_in_news");
        return new TopTenLabelDto(name, occurrences);
    }

    private NewsWithTheMostWordDto fetchNewsWithTheLongestWordDto(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int numberOfWords = resultSet.getInt("len");
        return new NewsWithTheMostWordDto(id, title, numberOfWords);
    }

    private AmountOfDataPerDayDto fetchCharacterAvgPerDayDto(ResultSet resultSet) throws SQLException {
        Date date = resultSet.getDate("post_date");
        int amount = resultSet.getInt("amount");
        return new AmountOfDataPerDayDto(date, amount);
    }
}
