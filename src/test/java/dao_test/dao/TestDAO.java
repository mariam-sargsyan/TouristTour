package dao_test.dao;

import dao.AbstractDAO;
import dao_test.bean.TestBean;

public class TestDAO extends AbstractDAO<TestBean, String> {

    @Override
    public TestBean getByKey(String key) {
        return getByKey(key, new TestBean());
    }
}
