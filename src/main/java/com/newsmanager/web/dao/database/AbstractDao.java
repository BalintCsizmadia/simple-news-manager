package com.newsmanager.web.dao.database;

import com.newsmanager.web.service.exception.TitleAlreadyExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class AbstractDao {

    final Connection connection;

    AbstractDao(Connection connection) {
        this.connection = connection;
    }

    void executeInsert(PreparedStatement statement) throws SQLException, TitleAlreadyExistsException {
        int insertCount;
        try {
            insertCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new TitleAlreadyExistsException("This title already exists");
        }
        if (insertCount != 1) {
            connection.rollback();
            throw new SQLException("Expected 1 row to be inserted");
        }
    }
}
