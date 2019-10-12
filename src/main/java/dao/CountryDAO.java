package dao;

import dao.bean.Country;

import java.util.List;

public class CountryDAO extends AbstractDAO<Country, String> {

    @Override
    public Country getByKey(String key) {
        return getByKey(key, new Country());
    }

}
