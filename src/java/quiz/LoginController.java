/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author cmkm
 */
@Named(value = "loginController")
@RequestScoped
public class LoginController {

    
    private String username, password;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    static boolean validate(String username, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        
        try {
            connection = DatabaseController.getConnection();
            ps = connection.prepareStatement("select username, password from User where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error: " + ex.getMessage());
            return false;
        } finally {
            DatabaseController.close(connection);
        }
        
        return false;
    }
    
}
