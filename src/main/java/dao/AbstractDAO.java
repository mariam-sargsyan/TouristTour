package dao;

import dao.annotations_dao.Field;
import dao.annotations_dao.OneToMany;
import dao.annotations_dao.PrimaryKey;
import org.apache.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static dao.ReflectionDAOUtils.*;

public abstract class AbstractDAO<T, K> {

    private static Logger log = Logger.getLogger(CountryDAO.class);

    public abstract T getByKey(K key);

    protected T getByKey(K key, T foundObject) {
        Class<T> foundObjectClass = (Class<T>) foundObject.getClass();
        String nameOfTable = getTableName(foundObjectClass);
        String primaryKey = Stream.of(foundObjectClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(PrimaryKey.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Primary Key is not found in %s class", foundObjectClass.getName())))
                .getAnnotation(Field.class).columnName();
        String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", nameOfTable, primaryKey, key);
        ResultSet rs = QueryExecutor.executeAndGet(sql);
        foundObject = new MappingManager().mappingRows(rs, (Class<T>) foundObject.getClass())
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        return foundObject;
    }

    public List<T> getAll() {
        Class<T> beanClass = getBeanClass();
        String nameOfTable = getTableName(beanClass);
        String sql = String.format("SELECT * FROM %s", nameOfTable);
        List<T> beanObjectsList = new LinkedList<T>();

        ResultSet rs = QueryExecutor.executeAndGet(sql);
        beanObjectsList = new MappingManager().mappingRows(rs, beanClass);
        return beanObjectsList;
    }

    public void insert(T value) {
        Class<T> beanClass = getBeanClass();
        String nameOfTable = getTableName(beanClass);
        List<String> columnsNames = new LinkedList<>();
        List<String> values = new LinkedList<>();
        for (java.lang.reflect.Field f : beanClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(Field.class)) {
                columnsNames.add(f.getAnnotation(Field.class).columnName());
                values.add(String.format("'%s'", getFieldValueFromObj(f, value).toString()));
            }
        }

        log.info("Insertion started");
        String sql = String.format("INSERT INTO %s (%s) VALUES(%s)", nameOfTable,
                String.join(",", columnsNames),
                String.join(",", values));
        QueryExecutor.execute(sql);
    }

    private <S, R> void deleteWithCascade(Class<S> beanClass, R key, S objToBeDeleted) {
        String nameOfTable = getTableName(beanClass);
        List<String> primaryKeysCondition = new LinkedList<>();
        for (java.lang.reflect.Field f : beanClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(OneToMany.class)) {
                Class clazzForeign = (Class)f.getType().getGenericSuperclass();
                Class cascadeKeyClass = findFieldByAnnotation(clazzForeign, PrimaryKey.class).getType();
                Set set = (Set)getFieldValueFromObj(f, objToBeDeleted);
                set.forEach(field -> deleteWithCascade(clazzForeign, getFieldValueFromObj(findFieldByAnnotation(clazzForeign, PrimaryKey.class), field),field));
            }
            if (f.isAnnotationPresent(PrimaryKey.class)) {
                primaryKeysCondition.add(String.format("%s = '%s'",
                        f.getAnnotation(Field.class).columnName(),
                        key)
                );
            }
        }
        String condition = String.join(" OR ", primaryKeysCondition);
        String sql = String.format("DELETE FROM %s WHERE %s", nameOfTable, condition);
        QueryExecutor.execute(sql);
    }

    public void delete(K key) {
        Class<T> beanClass = getBeanClass();
        T objToBeDeleted = getByKey(key);
        deleteWithCascade(beanClass, key, objToBeDeleted);
    }

    public Class<T> getBeanClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<K> getKeyClass() {
        return (Class<K>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }
}


