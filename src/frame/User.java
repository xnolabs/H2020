/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.io.File;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author W1710551 - Christopher Rees
 */
public class User {
    String fName, lName, username, email, profileImg, userType, sessionID;
    int userID;
    boolean userActive;
    Timestamp lastLogin, currentLogin;
    
    public User() {
        userID = 0;
        sessionID = "";
        userActive = false;
        userType = "";
        fName = "";
        lName = "";
        email = "";
        username = "";
        profileImg = "";
        lastLogin = null;
    }
  
    public User(int userId, String firstName, String lastName, String uName, String emailID, String userType) {
        String setSessionID = setSessionID();
        this.sessionID = setSessionID;
        this.userID = userId;
        this.userType = userType;
        this.fName = firstName;
        this.lName = lastName;
        this.email = emailID;
        this.username = uName;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(Timestamp ts) {
        this.lastLogin = ts;
    }

    public Timestamp setLoginDateTime() {
        Timestamp loginDateTime = new Timestamp(System.currentTimeMillis());
        this.lastLogin = loginDateTime;
        return loginDateTime;
    }
    
    
    public boolean isUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSessionID() {
        return sessionID;
    }

    final String setSessionID() {
        String uniqueID = UUID.randomUUID().toString();
        this.sessionID = uniqueID;
        return uniqueID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
    
    public static int newUser(String fName, String lName, String email, String pass, String uName, Timestamp lastLogin, String userType) {
        PreparedStatement st;
        ResultSet rs;
        int userID = 0;
        
        String regQuery = "INSERT INTO `users`(`first_name`, `last_name`, `email`, `password`, `username`, `last_login`, `user_type`) VALUES (?, ?, ?, ?, ?, ?, ?)";
                
        try {
            st = DBConnection.getConnection().prepareStatement(regQuery, new String[]{"userID"});
            st.setString(1, fName);
            st.setString(2, lName);
            st.setString(3, email);
            st.setString(4, pass);
            st.setString(5, uName);
            st.setObject(6, lastLogin);
            st.setString(7, userType);

            if (st.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Your account has been created");
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    userID = rs.getInt(1);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Oops! An error has occurred. Check your information");
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userID;
    }
    
    // Custom Method for Logging User Login Activity
    public org.apache.log4j.Logger loginActivity() {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(User.class);
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        if(this.isUserActive() == true) {
            logger.debug(
                    "Username: " + this.getUsername() + 
                    " - User ID: " + this.getUserID() + 
                    " - Session ID: " + this.getSessionID() + 
                    " - Action: Log-In Activity" +
                    " - Outcome: Successful");
        }
        else {
            logger.debug(this.getUsername() + 
                    " - User ID: " + this.getUserID() + 
                    " - Session ID: " + this.getSessionID() + 
                    " - Action: Log-In Activity" +
                    " - Outcome: Failed");  
        }
        return logger;
    }
    
    // Custom Method for Logging User Register Activity
    public org.apache.log4j.Logger registerActivity() {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(User.class);
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        if(this.isUserActive() == true) {
            logger.debug(
                    "Username: " + this.getUsername() + 
                    " - User ID: " + this.getUserID() + 
                    " - Session ID: " + this.getSessionID() + 
                    " - Action: Register Activity" +
                    " - Outcome: Registration Successful");
        }
        else {
            logger.debug(this.getUsername() + 
                    " - User ID: " + this.getUserID() + 
                    " - Session ID: " + this.getSessionID() + 
                    " - Action: Register Activity" +
                    " - Outcome: Registration Failed");  
        }
        return logger;
    }
    
    // Custom Method to Log Upload Activity to User Log File
    public org.apache.log4j.Logger uploadProject() {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(User.class);
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        if (this.isUserActive() == true) {
            logger.debug(
                "Username: " + this.getUsername() + 
                " - has successfully uploaded a new Project Data File");
        }
        return logger;
    }
    
    // Custom Method to Log Upload Activity to User Log File
    public org.apache.log4j.Logger uploadPart() {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(User.class);
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        if (this.isUserActive() == true) {
            logger.debug(
                    "Username: " + this.getUsername() + 
                    " - has successfully uploaded a new Participant Data File");
        }
        return logger;
    }
    
    // Custom Method for Logging User Logout Activity
    public org.apache.log4j.Logger logoutActivity() {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(User.class);
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        if(this.isUserActive() == false) {
            logger.debug(
                    "Username: " + this.getUsername() + 
                    " - User ID: " + this.getUserID() + 
                    " - Session ID: " + this.getSessionID() + 
                    " - Action: Log-Out Activity" +
                    " - Outcome: User logged out");
        }
        return logger;
    }
    
}
