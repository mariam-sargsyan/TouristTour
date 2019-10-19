package dao;

import dao.annotations_dao.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class ReflectionDAOUtils {

    public static <T> String getTableName(Class<T> clazz) {
        String nameOfTable = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            nameOfTable = clazz.getAnnotation(Table.class).tableName();
        }
        return nameOfTable;
    }

    public static Object getFieldValueFromObj(Field objField, Object obj) {
        Object res = null;
        try {
            objField.setAccessible(true);
            res = objField.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Field findFieldByAnnotation(Class clazz, Class<? extends Annotation> annotationClass) {
        return Stream.of(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotationClass))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Expected `%s` annotation in `%s` class", annotationClass.getName(), clazz.getName())
                ));
    }
}
