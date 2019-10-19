package dao_test.dao;

import dao.AbstractDAO;
import dao_test.bean.OneToManyBean;

public class OneToManyDao extends AbstractDAO<OneToManyBean, String> {
    @Override
    public OneToManyBean getByKey(String key) {
        return getByKey(key, new OneToManyBean());
    }
}
