package frame;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author W1710551 - Christopher Rees
 */
public class DBConnection { 
    
    
    private static String username = "root";
    private static String password = "";
    private static String servername = "localhost";
    private static String db = "h2020";
    private static int port = 3306;
    

    public static Connection getConnection() {
       
        Connection conn = null;
        
        MysqlDataSource datasource = new MysqlDataSource();
        datasource.setServerName(servername);
        datasource.setUser(username);
        datasource.setPassword(password);
        datasource.setDatabaseName(db);
        datasource.setPortNumber(port);
        
        try {
            conn = datasource.getConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger("Get Connection: " + DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }
    
}
