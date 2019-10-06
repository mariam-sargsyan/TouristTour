package dao;

import dao.bean.Cities;

import java.util.List;

public class CitiesDAO extends AbstractDAO <Cities, String>{

    @Override
    public Cities getByKey(String key) {
        return getByKey(key, new Cities());
    }

    @Override
    public List<Cities> getAll() {
        return getAll(Cities.class);
    }

    @Override
    public void insert(Cities value) {
        insert(value,Cities.class);

    }

    @Override
    public void delete(String key) {
        delete(key,Cities.class);

    }
}
