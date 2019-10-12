package dao;

import dao.annotations_dao.Field;
import dao.annotations_dao.OneToMany;
import dao.annotations_dao.PrimaryKey;
import dao.annotations_dao.Table;
import org.apache.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractDAO<T, K> {
    private static final String URL = "jdbc:sqlite:C://Users/mariam.sargsyan/IdeaProjects/Databases/src/main/resources/tourist_tour_info.db";
    private static Logger log = Logger.getLogger(CountryDAO.class);


    protected String getTableName(Class<T> clazz) {
        String nameOfTable = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            nameOfTable = clazz.getAnnotation(Table.class).tableName();
        }
        return nameOfTable;
    }

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

        try (Statement stmnt = ConnectionProvider.get(URL).createStatement()) {
            ResultSet rs = stmnt.executeQuery(sql);

            try {
                foundObject = new MappingManager().mappingRows(rs, (Class<T>) foundObject.getClass())
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return foundObject;
    }

    public List<T> getAll() {
        Class<T> beanClass = getBeanClass();
        String nameOfTable = getTableName(beanClass);
        String sql = String.format("SELECT * FROM %s", nameOfTable);
        List<T> beanObjectsList = new LinkedList<T>();

        try (Statement stmnt = ConnectionProvider.get(URL).createStatement()) {
            ResultSet rs = stmnt.executeQuery(sql);

            try {
                beanObjectsList = new MappingManager().mappingRows(rs, beanClass);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return beanObjectsList;
    }

    ;

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
        try (PreparedStatement pstmt = ConnectionProvider.get(URL).prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("error took place while executing the command", e);

        }
    }

    public void delete(K key) {
        Class<T> beanClass = getBeanClass();
        T objToBeDeleted = getByKey(key);
        String nameOfTable = getTableName(beanClass);
        List<String> primaryKeysCondition = new LinkedList<>();
        for (java.lang.reflect.Field f : beanClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(OneToMany.class)) {
                Class linkedClassName = f.getAnnotation(OneToMany.class).linkedClassName();
                Object valueForeignKey = getFieldValueFromObj(f, objToBeDeleted);
                String nameFieldForeignKey = f.getAnnotation(OneToMany.class).fieldName();
                java.lang.reflect.Field fieldForeignKey = Stream.of(linkedClassName.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(Field.class) && field.getAnnotation(Field.class).columnName().equals(nameFieldForeignKey))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Bad foreign key"));
                java.lang.reflect.Field primaryKeyFieldLinkedClass = Stream.of(linkedClassName.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException(""));
               /* getAll(linkedClassName).stream()
                        .filter(linkedClassObject -> getFieldValueFromObj(fieldForeignKey, linkedClassObject).equals(valueForeignKey))
                        .forEach(linkedClassObject -> delete(getFieldValueFromObj(primaryKeyFieldLinkedClass, linkedClassObject), linkedClassName));
                 Есть аннотация OneToMany, и в ней нельзя параметризовать поле LinkedClassName
                 Это ведет к проблемам в вызове delete выше */
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
        try (PreparedStatement pstmt = ConnectionProvider.get(URL).prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("error took place while executing the command", e);

        }

    }

    private Object getFieldValueFromObj(java.lang.reflect.Field objField, Object obj) {
        Object res = null;
        try {
            objField.setAccessible(true);
            res = objField.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return res;
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


