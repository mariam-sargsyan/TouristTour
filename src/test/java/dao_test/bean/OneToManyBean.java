package dao_test.bean;

import dao.annotations_dao.Field;
import dao.annotations_dao.OneToMany;
import dao.annotations_dao.PrimaryKey;
import dao.annotations_dao.Table;

import java.util.Set;

@Table(tableName = "one_to_many_table")
public class OneToManyBean {

    @PrimaryKey
    @Field(columnName = "id_column")
    private Integer idColumn;

    @OneToMany(fieldName = "foreign_key")
    private Set<ManyToOneBean> manyToOneBeans;

    public Integer getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(Integer idColumn) {
        this.idColumn = idColumn;
    }

    public Set<ManyToOneBean> getManyToOneBeans() {
        return manyToOneBeans;
    }

    public void setManyToOneBeans(Set<ManyToOneBean> manyToOneBeans) {
        this.manyToOneBeans = manyToOneBeans;
    }
}
