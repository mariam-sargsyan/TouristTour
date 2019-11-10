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

public class AbstractDaoTest {

    private static final List<String> listOfColumns = Collections.singletonList("test_column");
    private static final String nameTestTable = "test_table";



    @Before
    public void preparation() {
        InitializerDB.createTable(nameTestTable, listOfColumns);
    }

    @Test
    public void testForInsert() {
        TestDAO testDAO = new TestDAO();
        TestBean testBean = new TestBean();
        testBean.setTestColumnName("test");
        testDAO.insert(testBean);
        List<TestBean> tableContent = testDAO.getAll();

        assertThat(tableContent).hasSize(1);
        assertThat(tableContent.get(0).getTestColumnName()).isEqualTo(testBean.getTestColumnName());
    }

    @After
    public void tearDown() {
        InitializerDB.deleteTable(nameTestTable);
    }

}
