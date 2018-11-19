package com.newsmanager.web.service.simple;

import com.newsmanager.web.dao.LabelDao;
import com.newsmanager.web.model.Label;
import com.newsmanager.web.service.LabelService;

import java.sql.SQLException;
import java.util.List;

public class SimpleLabelService implements LabelService {

    private final LabelDao labelDao;

    public SimpleLabelService(LabelDao labelDao) {
        this.labelDao = labelDao;
    }

    public List<Label> getAll() throws SQLException {
        return labelDao.findAll();
    }

    @Override
    public Label getByName(String name) throws SQLException {
        return labelDao.findByName(name);
    }

    @Override
    public List<Label> getAllByNewsId(int id) throws SQLException {
        return labelDao.findAllByNewsId(id);
    }
}
