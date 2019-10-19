package dao;

import dao.bean.City;

public class CitiesDAO extends AbstractDAO <City, String>{

       @Override
    public City getByKey(String key) {
        return getByKey(key, new City());
    }

}
