package com.newsmanager.web.dao;

import com.newsmanager.web.model.Label;

import java.sql.SQLException;
import java.util.List;

public interface LabelDao {

    List<Label> findAll() throws SQLException;

    Label findByName(String name) throws SQLException;

    List<Label> findAllByNewsId(int id) throws SQLException;

}
