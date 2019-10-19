package dao_test.dao;

import dao.AbstractDAO;
import dao_test.bean.ManyToOneBean;
import dao_test.bean.TestBean;

public class ManyToOneDao extends AbstractDAO<ManyToOneBean, String> {
    @Override
    public ManyToOneBean getByKey(String key) {
        return getByKey(key, new ManyToOneBean());
    }
}
