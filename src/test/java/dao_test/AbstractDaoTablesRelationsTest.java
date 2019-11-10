package dao_test;

import dao.InitializerDB;
import dao_test.bean.ManyToOneBean;
import dao_test.bean.OneToManyBean;
import dao_test.bean.TestBean;
import dao_test.dao.ManyToOneDao;
import dao_test.dao.OneToManyDao;
import dao_test.dao.TestDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractDaoTablesRelationsTest {

    private static final List<String> oneToManyListOfColumns = Collections.singletonList("id_column INTEGER PRIMARY KEY");
    private static final List<String> manyToOneListOfColumns = Arrays.asList(
            "id_column INTEGER PRIMARY KEY",
            "foreign_key INTEGER",
            "FOREIGN KEY (foreign_key) REFERENCES one_to_many_table (id_column)");
    private static final String oneToManyTable = "one_to_many_table";
    private static final String manyToOneTable = "many_to_one_table";

    @Before
    public void preparation() {
        InitializerDB.createTable(oneToManyTable, oneToManyListOfColumns);
        InitializerDB.createTable(manyToOneTable, oneToManyListOfColumns);

    }

    @Test
    public void testForCascadeDelete() {
        OneToManyBean oneToManyBean = new OneToManyBean();
        oneToManyBean.setIdColumn(25);
        oneToManyBean.setManyToOneBeans(new HashSet<>());

        ManyToOneBean manyToOneBean1 = new ManyToOneBean();
        manyToOneBean1.setIdColumn(1);
        manyToOneBean1.setOneToManyBean(oneToManyBean);

        ManyToOneBean manyToOneBean2 = new ManyToOneBean();
        manyToOneBean1.setIdColumn(2);
        manyToOneBean1.setOneToManyBean(oneToManyBean);

        ManyToOneBean manyToOneBean3 = new ManyToOneBean();
        manyToOneBean1.setIdColumn(3);
        manyToOneBean1.setOneToManyBean(oneToManyBean);


        oneToManyBean.getManyToOneBeans().add(manyToOneBean1);
        oneToManyBean.getManyToOneBeans().add(manyToOneBean2);
        oneToManyBean.getManyToOneBeans().add(manyToOneBean3);

        OneToManyDao oneToManyDao = new OneToManyDao();
        oneToManyDao.insert(oneToManyBean);

        ManyToOneDao manyToOneDao = new ManyToOneDao();
        manyToOneDao.insert(manyToOneBean1);
        manyToOneDao.insert(manyToOneBean2);
        manyToOneDao.insert(manyToOneBean3);

        oneToManyDao.delete(25);

        assertThat(manyToOneDao.getAll()).isEmpty();

    }

    @After
    public void tearDown() {
        InitializerDB.deleteTable(manyToOneTable);
        InitializerDB.deleteTable(oneToManyTable);
    }

}
