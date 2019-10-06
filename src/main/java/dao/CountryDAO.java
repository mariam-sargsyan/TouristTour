package dao;

import dao.bean.Country;

import java.util.List;

public class CountryDAO extends AbstractDAO<Country, String> {

    @Override
    public Country getByKey(String key) {
        return getByKey(key, new Country());
    }

    @Override
    public List<Country> getAll() {
        return getAll(Country.class);
    }

    @Override
    public void insert(Country value) {
        insert(value, Country.class);
    }

    @Override
    public void delete(String key) {
        delete(key, Country.class);

    }
}
