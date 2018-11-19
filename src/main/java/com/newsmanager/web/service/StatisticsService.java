package com.newsmanager.web.service;

import com.newsmanager.web.dto.TopTenLabelDto;

import java.sql.SQLException;
import java.util.List;

public interface StatisticsService {

    List<TopTenLabelDto> getTopTenPopularLabel() throws SQLException;

    Object statisticsSelector(String option) throws SQLException;

}
