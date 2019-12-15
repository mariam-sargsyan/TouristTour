package dao;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    private static Logger log = Logger.getLogger(ConnectionProvider.class);
    private static InputStream inputStream = QueryExecutor.class.getResourceAsStream("/db.properties");
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Problem with loading of db.properties", e);
        }
    }


    public static Connection get (String url){
        log.info("Start creation of connection");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, properties.getProperty("db.user_name"), properties.getProperty("db.user_password"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;

    }
}
