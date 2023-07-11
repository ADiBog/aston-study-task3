package com.example.firstwebapp.config;

import com.example.firstwebapp.business.JdbcConnProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.SQLException;

@WebListener
public class StartupListener implements ServletContextListener {

    private static final String INIT_SCRIPT = "create.sql";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            initDb();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        ServletContextListener.super.contextInitialized(sce);
    }

    private String getFileContent() throws IOException {
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(StartupListener.INIT_SCRIPT)) {
            if (inputStream == null) {
                return "";
            }
            return new String(inputStream.readAllBytes());
        }
    }

    private void initDb() throws SQLException, IOException {
        /*var jdbcRep = new JDBCRep();*/
        var connection = JdbcConnProvider.getJdbcConnectionProvider().getNewConnection();
        if (connection != null) {
            var statement = connection.createStatement();
            var initSql = getFileContent();
            if (initSql.isEmpty()) {
                return;
            }
            statement.execute(initSql);
        }
    }
}
