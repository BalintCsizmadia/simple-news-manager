package com.newsmanager.web.listener;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public final class WebappContextListener implements ServletContextListener {

    private final static Logger logger = Logger.getLogger(WebappContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        registerCharacterEncodingFilter(sce);
        DataSource dataSource = putDataSourceToServletContext(sce);
        runDatabaseInitScript(dataSource, "/init.sql");
        logger.info("Database initialized");
    }

    private void registerCharacterEncodingFilter(ServletContextEvent sce) {
        sce.getServletContext().addFilter(
                "SetCharacterEncodingFilter",
                "org.apache.catalina.filters.SetCharacterEncodingFilter");
    }

    private DataSource putDataSourceToServletContext(ServletContextEvent sce) {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envCtx.lookup("jdbc/newsmanager");
            ServletContext servletCtx = sce.getServletContext();
            servletCtx.setAttribute("dataSource", dataSource);
            return dataSource;
        } catch (NamingException ex) {
            logger.error(ex.getMessage());
            throw new IllegalStateException(ex);
        }
    }

    private void runDatabaseInitScript(DataSource dataSource, String resource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource(resource));
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Database connection ended");
    }
}
