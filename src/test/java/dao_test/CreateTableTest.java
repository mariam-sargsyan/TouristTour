package dao_test;

import dao.AbstractDAO;
import dao.InitializerDB;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

public class CreateTableTest {
    private static final String nameTestTable = "test_table";
    private static final List<String> listOfColumns = Collections.singletonList("test_column");


    @Test
    public void createTableTest(){
        InitializerDB.createTable(nameTestTable,listOfColumns);
        List<Object> set = AbstractDAO.execute("SELECT name FROM sqlite_master WHERE type='table' AND name='{test_table}'");
        int a = 8;
    }

}
