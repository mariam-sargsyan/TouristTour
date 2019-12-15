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

    private static final List<String> listOfColumns = Collections.singletonList("test_column text PRIMARY KEY");
    private static final String nameTestTable = "test_table";
    private static final TestDAO testDAO = new TestDAO();
    private static final TestBean testBean = new TestBean();

    @Before
    public void preparation() {
        InitializerDB.createTable(nameTestTable, listOfColumns);
    }

    @Test
    public void testForInsert() {
        testBean.setTestColumnName("test");
        testDAO.insert(testBean);
        List<TestBean> tableContent = testDAO.getAll();

        assertThat(tableContent).hasSize(1);
        assertThat(tableContent.get(0).getTestColumnName()).isEqualTo(testBean.getTestColumnName());
    }

    @Test
    public void testForDelete(){
        TestBean testBean2 = new TestBean();
        testBean.setTestColumnName("test_1");
        testBean2.setTestColumnName("test_2");
        testDAO.insert(testBean);
        testDAO.insert(testBean2);
        testDAO.delete("test_1");

        List<TestBean> tableContent = testDAO.getAll();
        assertThat(tableContent).hasSize(1);
        assertThat(tableContent.get(0).getTestColumnName()).isEqualTo(testBean2.getTestColumnName());
    }

    @Test
    public void testMultipleValuesReturn(){
        TestBean testBean2 = new TestBean();
        TestBean testBean3 = new TestBean();
        testBean.setTestColumnName("test_1");
        testBean2.setTestColumnName("test_2");
        testBean3.setTestColumnName("test_3");
        testDAO.insert(testBean);
        testDAO.insert(testBean2);
        testDAO.insert(testBean3);
        List<TestBean> tableContent = testDAO.getAll();

        assertThat(tableContent).hasSize(3);

    }

    @After
    public void tearDown() {
        InitializerDB.deleteTable(nameTestTable);
    }

}
