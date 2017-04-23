/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author cmkm
 */
class DatabaseController {
    
    static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javaquizweb", "root", "catfood"
            );
            return connection;
        } catch (Exception ex) {
            System.out.println("DB connection error: " + ex.getMessage());
            return null;
        }
    }
    
    static void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println("DB error: " + ex.getMessage());
        }
    }
    
}
