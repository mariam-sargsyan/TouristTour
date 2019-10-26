package dao;

import dao.bean.Country;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

public class InitializerDB {

    private static Logger log = Logger.getLogger(InitializerDB.class);

    public static void createTable(String nameTable, List<String> listColumns) {
        // TODO type of column
        log.info(format("Creating a table '%s'", nameTable));

        String sql = format("CREATE TABLE IF NOT EXISTS %s (%s);", nameTable,
                String.join(", ", listColumns));
        log.info("Creation of the `%s` table has started");
        QueryExecutor.execute(sql);
        log.info("Finished");
    }

    public static void deleteTable(String nameTable) {
        log.info(format("Deleting a table '%s'", nameTable));

        String sql = format("DROP TABLE IF EXISTS %s;", nameTable);
        log.info("Deletion of the table has started");
        QueryExecutor.execute(sql);
        log.info("Finished");
    }

    public static List<Country> select(String nameTable, List<String> columnNames) {
        //String strColumnNames = String.join(",", columnNames);
        // String sql = String.format("SELECT %s FROM %s",strColumnNames,nameTable ) ;
        String sql = format("SELECT * FROM %s", nameTable);
        List<Country> country = new LinkedList<Country>();
        ResultSet rs = QueryExecutor.executeAndGet(sql);
        country = new MappingManager().mappingRows(rs, Country.class);
        return country;
    }

}
