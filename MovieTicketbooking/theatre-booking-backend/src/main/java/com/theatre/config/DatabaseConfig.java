package com.theatre.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    
    @Value("${db.url:jdbc:mysql://localhost:3306/theatre_booking}")
    private String url;
    
    @Value("${db.username:root}")
    private String username;
    
    @Value("${db.password:Naveen@2006}")
    private String password;
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}