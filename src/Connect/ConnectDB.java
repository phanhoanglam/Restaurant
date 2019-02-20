/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class ConnectDB {

    public static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String DB_URL = "jdbc:sqlserver://localhost:1433;";
    public static final String DATABASENAME = "databaseName=RestaurantDB;";
    public static final String USER = "user=sa;";
    public static final String PASS = "password=123";

    public static Connection connectSQLServer() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Loi" + e.getMessage());
            return null;
        }
    }
}
