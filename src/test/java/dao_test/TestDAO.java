package dao_test;

import dao.AbstractDAO;

public class TestDAO extends AbstractDAO<TestBean, String> {

    @Override
    public TestBean getByKey(String key) {
        return getByKey(key, new TestBean());
    }
}
