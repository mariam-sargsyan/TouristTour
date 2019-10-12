package dao;

import dao.bean.Cities;

import java.util.List;

public class CitiesDAO extends AbstractDAO <Cities, String>{

       @Override
    public Cities getByKey(String key) {
        return getByKey(key, new Cities());
    }

}
