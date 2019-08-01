package com.revature.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ConnectionUtil
 */
public class ConnectionUtil {
    private Connection connection;
    private String url, user, password;

    public ConnectionUtil() throws IOException {
        try {
        	Class.forName("org.postgresql.Driver");
            Properties properties = new Properties();
            properties.load(new FileReader(
                    new File(
                        getClass().getClassLoader().getResource("application.properties").getFile()
                    )
                )
            );
            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            //this.connection = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:5432/projectone", "projectone", "password");
            //this.connection = DriverManager.getConnection(this.url,this.user,this.password);
           
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {

        }
    }
}