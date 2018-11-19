package com.newsmanager.web.dao.database;

import com.newsmanager.web.dao.LabelDao;
import com.newsmanager.web.model.Label;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseLabelDao extends AbstractDao implements LabelDao {

    public DatabaseLabelDao(Connection connection) {
        super(connection);
    }

    public List<Label> findAll() throws SQLException {
        String sql = "SELECT id, name FROM labels";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Label> labels = new ArrayList<>();
            while (resultSet.next()) {
                labels.add(fetchLabel(resultSet));
            }
            return labels;
        }
    }

    @Override
    public Label findByName(String name) throws SQLException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        String sql = "SELECT id, name FROM labels WHERE name = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchLabel(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Label> findAllByNewsId(int id) throws SQLException {
        List<Label> labels = new ArrayList<>();
        String sql = "SELECT id, name FROM labels\n" +
                "JOIN news_labels ON labels.id = news_labels.label_id\n" +
                "WHERE news_labels.news_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    labels.add(fetchLabel(resultSet));
                }
            }
        }
        return labels;
    }

    private Label fetchLabel(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Label(id, name);
    }
}
