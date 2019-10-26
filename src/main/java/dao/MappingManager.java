package dao;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

public class MappingManager {

    private static Logger log = Logger.getLogger(MappingManager.class);

    private  <T> T mapResultSetToClass (ResultSet rs, T objToMap) {

        Class<? extends T> clazz = (Class<? extends T>) objToMap.getClass();

        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(dao.annotations_dao.Field.class)){
               String columnName = f.getAnnotation(dao.annotations_dao.Field.class).columnName();
                try {
                    f.setAccessible(true);
                    f.set(objToMap, rs.getObject(columnName));
                } catch (IllegalAccessException e) {
                    log.error(format("Problem with `%s` field of `%s` class", f.getName(), clazz.getName()), e);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return objToMap;
    }

    public <T> List<T> mappingRows(ResultSet rs, Class<T> clazz) {
        List<T> result = new LinkedList<>();
        try {
            while (rs.next()) {
                T object = clazz.getDeclaredConstructor().newInstance();
                mapResultSetToClass(rs, object);
                result.add(object);
            }
        } catch (ReflectiveOperationException e) {
            log.error(format("Instance of `%s` class was not created", clazz.getName()), e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
