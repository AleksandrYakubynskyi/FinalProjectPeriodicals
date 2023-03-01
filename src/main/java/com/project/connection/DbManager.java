package com.project.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DbManager {
    private static HikariDataSource dataSource;

    private static final String URL = "jdbc:mysql://localhost:3306/Aleksandr";
    private static final String USER = "root";
    private static final String PASSWORD = "rootroot";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = getHikariConfig();
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    private static HikariConfig getHikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setDriverClassName(DB_DRIVER);
        return config;
    }

    private DbManager() {
    }
}