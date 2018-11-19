package com.newsmanager.web.dao.database;

import com.newsmanager.web.dao.NewsDao;
import com.newsmanager.web.model.News;
import com.newsmanager.web.service.exception.NewsIdOutOfRangeException;
import com.newsmanager.web.service.exception.TitleAlreadyExistsException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseNewsDao extends AbstractDao implements NewsDao {

    private final static Logger logger = Logger.getLogger(DatabaseNewsDao.class);

    public DatabaseNewsDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<News> findAll() throws SQLException {
        List<News> news = new ArrayList<>();
        String sql = "SELECT id, title, content, post_date FROM news";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                news.add(fetchNews(resultSet));
            }
            return news;
        }
    }

    @Override
    public List<News> findAllByOrderByDate(String order) throws SQLException {
        // Asc or Desc ordering
        List<News> news = new ArrayList<>();
        String sql = "SELECT id, title, content, post_date FROM news\n" +
                "ORDER BY post_date " + order + ";";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                news.add(fetchNews(resultSet));
            }
            return news;
        }
    }

    @Override
    public News findById(int id) throws SQLException, NewsIdOutOfRangeException {
        String sql = "SELECT id, title, content, post_date FROM news\n" +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchNews(resultSet);
            }
        }
        throw new NewsIdOutOfRangeException("You've reached the end");
    }

    public void save(String title, String content, String[] labels) throws SQLException, TitleAlreadyExistsException {
        try {
            connection.setAutoCommit(false);
            saveNews(title, content);
            saveLabels(labels);
            saveNewsLabels(title, labels);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveNews(String title, String content) throws SQLException, TitleAlreadyExistsException {
        String sql = "INSERT INTO news (title, content, post_date) VALUES\n" +
                "(?, ?, NOW());";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setString(1, title);
            statement.setString(2, content);
            executeInsert(statement);
        }
    }

    private void saveLabels(String[] labels) throws SQLException {
        String sql = "INSERT INTO labels (name)\n" +
                "VALUES (?)\n" +
                "ON CONFLICT (name)\n" +
                "DO UPDATE\n" +
                "SET name = EXCLUDED.name;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            for (String label : labels) {
                statement.setString(1, label);
                statement.executeUpdate();
            }
        }
    }

    private void saveNewsLabels(String title, String[] labels) throws SQLException {
        String sql = "INSERT INTO news_labels (news_id, label_id) VALUES\n" +
                "((SELECT id FROM news WHERE title = ?), (SELECT id FROM labels WHERE name = ?))\n" +
                "\tON CONFLICT\n" +
                "\tDO NOTHING;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            for (String label : labels) {
                statement.setString(1, title);
                statement.setString(2, label);
                statement.executeUpdate();
            }
        }
    }

    private News fetchNews(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");
        Date postDate = resultSet.getDate("post_date");
        return new News(id, title, content, postDate);
    }
}
