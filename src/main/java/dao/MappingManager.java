package dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MappingManager {

    public <T> T mapResultSetToClass (ResultSet rs, T objToMap) {

        Class<? extends T> clazz = (Class<? extends T>) objToMap.getClass();

        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(dao.annotations_dao.Field.class)){
               String columnName = f.getAnnotation(dao.annotations_dao.Field.class).columnName();
                try {
                    f.setAccessible(true);
                    f.set(objToMap, rs.getObject(columnName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return objToMap;
    }

    public <T> List<T> mappingRows(ResultSet rs, Class<T> clazz) throws SQLException, IllegalAccessException, InstantiationException {
        List<T> result = new LinkedList<>();
        while (rs.next()){
            T object= clazz.newInstance();
            mapResultSetToClass(rs,object);
            result.add(object);

        }
        return result;
    }
}
