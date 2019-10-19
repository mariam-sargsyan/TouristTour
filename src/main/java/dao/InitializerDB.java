package dao;

import dao.annotations_dao.Field;
import dao.annotations_dao.Table;
import dao.bean.Country;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class InitializerDB {

    private static final String URL = "jdbc:sqlite:C://Users/mariam.sargsyan/IdeaProjects/Databases/src/main/resources/tourist_tour_info.db";
    private static Logger log = Logger.getLogger(InitializerDB.class);

    public static void createTable(String nameTable, List<String> listColumns) {
        // TODO type of column
        log.info(String.format("Creating a table '%s'", nameTable));

        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s);", nameTable,
                String.join(", ", listColumns));


        try (Statement stmt = ConnectionProvider.get(URL).createStatement()) {
            log.info("Creation of the table has started");
            stmt.executeUpdate(sql);
            log.info("Finished");
        } catch (SQLException e) {
            log.error("The problem ocurred on the stage of table creation", e);
        }


    }

    public static void deleteTable(String nameTable) {

        log.info(String.format("Deleting a table '%s'", nameTable));

        String sql = String.format("DROP TABLE IF EXISTS %s;", nameTable);


        try (Statement stmt = ConnectionProvider.get(URL).createStatement()) {
            log.info("Deletion of the table has started");
            stmt.executeUpdate(sql);
            log.info("Finished");
        } catch (SQLException e) {
            log.error("The problem ocurred on the stage of table deletion", e);
        }
    }



      public static List<Country> select(String nameTable, List<String> columnNames) {
        //String strColumnNames = String.join(",", columnNames);
        // String sql = String.format("SELECT %s FROM %s",strColumnNames,nameTable ) ;
        String sql = String.format("SELECT * FROM %s", nameTable);
        List<Country> country = new LinkedList<Country>();

        try (Statement stmnt = ConnectionProvider.get(URL).createStatement()) {
            ResultSet rs = stmnt.executeQuery(sql);

            try {
                country = new MappingManager().mappingRows(rs, Country.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return country;
    }


}
