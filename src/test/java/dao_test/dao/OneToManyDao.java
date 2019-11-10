package dao_test.dao;

import dao.AbstractDAO;
import dao_test.bean.OneToManyBean;

public class OneToManyDao extends AbstractDAO<OneToManyBean, Integer> {
    @Override
    public OneToManyBean getByKey(Integer key) {
        return getByKey(key, new OneToManyBean());
    }
}
