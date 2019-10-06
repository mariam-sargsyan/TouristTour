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

    public static <T> void insert(T objToInsert) {
        Class<? extends T> clazz = (Class<? extends T>) objToInsert.getClass();

        String nameOfTable = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            nameOfTable = clazz.getAnnotation(Table.class).tableName();
        }

        List<String> columnsNames = new LinkedList<>();
        List<String> values = new LinkedList<>();
        for (java.lang.reflect.Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Field.class)) {
                columnsNames.add(f.getAnnotation(Field.class).columnName());
                try {
                    f.setAccessible(true);
                    Object objValue = f.get(objToInsert);
                    values.add(String.format("'%s'", objValue.toString()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("Insertion started");
        String sql = String.format("INSERT INTO %s (%s) VALUES(%s)", nameOfTable,
                String.join(",", columnsNames),
                String.join(",", values));
        try (PreparedStatement pstmt = ConnectionProvider.get(URL).prepareStatement(sql)){
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("error took place while executing the command", e);

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
