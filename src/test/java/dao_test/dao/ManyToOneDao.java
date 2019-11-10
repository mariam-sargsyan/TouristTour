package dao_test.dao;

import dao.AbstractDAO;
import dao_test.bean.ManyToOneBean;
import dao_test.bean.TestBean;

public class ManyToOneDao extends AbstractDAO<ManyToOneBean, Integer> {
    @Override
    public ManyToOneBean getByKey(Integer key) {
        return getByKey(key, new ManyToOneBean());
    }
}
