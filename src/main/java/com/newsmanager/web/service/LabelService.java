package com.newsmanager.web.service;

import com.newsmanager.web.model.Label;

import java.sql.SQLException;
import java.util.List;

public interface LabelService {

    List<Label> getAll() throws SQLException;

    Label getByName(String name) throws SQLException;

    List<Label> getAllByNewsId(int id) throws SQLException;

}
