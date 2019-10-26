package dao;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static java.lang.String.format;

public class QueryExecutor {

    private static Logger log = Logger.getLogger(InitializerDB.class);
    private static String URL;

    static {
        loadDbURL();
    }

    private static void loadDbURL() {
        InputStream inputStream = QueryExecutor.class.getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Problem with loading of db.properties", e);
        }
        URL = format("jdbs:%s:%s", properties.get("db.rdbms"), properties.get("db.path"));
    }

    public static ResultSet executeAndGet(String sqlQuery) {
        log.info(format("Start executing: %s", sqlQuery));
        ResultSet result = null;
        try (Statement stmnt = ConnectionProvider.get(URL).createStatement()) {
            result = stmnt.executeQuery(sqlQuery);
            log.info(format("Executing successfully finished with result object", sqlQuery));
        } catch (SQLException e) {
            log.error(format("Problem with executing `%s`", sqlQuery), e);
        }
        return result;
    }

    public static void execute(String sqlQuery) {
        log.info(format("Start executing: %s", sqlQuery));
        try (PreparedStatement pstmt = ConnectionProvider.get(URL).prepareStatement(sqlQuery)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error(format("Problem with executing `%s`", sqlQuery), e);
        }
    }
}
