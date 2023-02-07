package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class utilizes the {@link org.postgresql.Driver} and
 *         {@link DriverManager} of the JDBC to allow our backend to 
 *         interact with our postgre database.
 *         </p>
 */
public class ConnectionUtil {
    private static Connection connection;

    // Private constructor so no instances of this class can be created
    private ConnectionUtil() {
        connection = null;
    }

    /**
     * <p>
     * This method creates a connection on
     * <code>url</code>,<code>user</code>,<code>password</code> which are 
     * being stored as enviornment variable in our system.
     * </p>
     * 
     * @return a connection to the URL
     */
    public static Connection getConnection() {
        // Test to see if a connection has already been established
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Initialize the variables from our enviornment
        String url = System.getenv("url"),
                user = System.getenv("user"),
                password = System.getenv("password");

        // Create the connection
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
