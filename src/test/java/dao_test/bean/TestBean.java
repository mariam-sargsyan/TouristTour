package dao_test.bean;

import dao.annotations_dao.Field;
import dao.annotations_dao.PrimaryKey;
import dao.annotations_dao.Table;

@Table(tableName = "test_table")
public class TestBean  {

    @PrimaryKey
    @Field(columnName = "test_column")
    private String testColumnName;

    public String getTestColumnName() {
        return testColumnName;
    }

    public TestBean setTestColumnName(String testColumnName) {
        this.testColumnName = testColumnName;
        return this;
    }
}
