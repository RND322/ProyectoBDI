//Agregada el 12/8/24
package com.prueba2.prueba2.Classes;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnecction {
    
    @Value("${spring.datasource.url}")
    private String dbUrl;

    public Connection getConnection(String username, String password) throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource.getConnection();
    }
}
