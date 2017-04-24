/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author cmkm
 * 
 * https://maxkatz.org/2009/08/17/learning-jsf2-managed-beans/
 */
@Named(value = "login")
@ManagedBean(name = "login", eager=true)
@SessionScoped
public class Login implements Serializable {
    private String username;
    private String password;
    private String firstName;
    private String middleInitial;
    private String lastName;

    /**
     * Creates a new instance of Login
     */
    public Login() {
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleInitial() {
        return this.middleInitial;
    }
    
    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    // called from login form at Login.xhtml
    public String submit() {
        Connection connection = DatabaseController.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement("select * from User where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                System.out.println("goal!");
                return "/Quiz.xhtml";
            } else {
                System.out.println("Bad credentials!");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bad username or password", null));
            }
            
        } catch (SQLException ex) {
            System.out.println("Login DB error: " + ex.getMessage());
        } finally {
            DatabaseController.close(connection);
        }
        
        return "/Login.xhtml";

    }
    
    // called from registration form at Register.xhtml
    public String register() {
        Connection connection = DatabaseController.getConnection();
        
        try {
            PreparedStatement checkps = connection.prepareStatement(
                    "select * from User where username = ?");
            PreparedStatement newps = connection.prepareStatement(
                    "insert into User (username, firstname, mi, lastname, " + 
                            "password, whencreated) values (?, ?, ?, ?, ?, ?)");
            checkps.setString(1, username);
            System.out.println(checkps);
            ResultSet checkrs = checkps.executeQuery();
            Date date = new Date();
            Timestamp time = new Timestamp(date.getTime());
            
            if (checkrs.next()) {
                // username already exists in system
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "User " + username + "already exists; " + 
                            "please use a different username", null));

                return "/Register.xhtml";
            } else {
                // ok to create new user
                newps.setString(1, username);
                newps.setString(2, firstName);
                newps.setString(3, middleInitial);
                newps.setString(4, lastName);
                newps.setString(5, password);
                newps.setTimestamp(6, time);
                
                int newrs = newps.executeUpdate();
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "OK! Please log in with your new username and password", null));
                return "/Login.xhtml";
            }
            
        } catch (SQLException ex) {
            System.out.println("Login DB error: " + ex.getMessage());
        } finally {
            DatabaseController.close(connection);
        }
        
        return "/Register.xhtml";
    }
    
    public String logout() {
        this.username = null;
        this.password = null;
        return "/Login.xhtml";
    }
    
}
