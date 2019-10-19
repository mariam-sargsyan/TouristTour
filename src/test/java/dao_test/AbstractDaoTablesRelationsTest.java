package dao_test;

import dao.InitializerDB;
import dao_test.bean.TestBean;
import dao_test.dao.TestDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractDaoTablesRelationsTest {

    private static final List<String> oneToManyListOfColumns = Collections.singletonList("id_column");
    private static final List<String> manyToOneListOfColumns = Collections.singletonList("id_column, foreign_key");
    private static final String oneToManyTable = "one_to_many_table";
    private static final String manyToOneTable = "many_to_one_table";

    @Before
    public void preparation() {
    }

    @Test
    public void testForCascadeDelete() {

    }

    @After
    public void tearDown() {
    }

}
