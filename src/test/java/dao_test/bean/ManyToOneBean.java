package dao_test.bean;

import dao.annotations_dao.Field;
import dao.annotations_dao.ManyToOne;
import dao.annotations_dao.PrimaryKey;
import dao.annotations_dao.Table;

@Table(tableName = "many_to_one_table")
public class ManyToOneBean {

    @PrimaryKey
    @Field(columnName = "id_column")
    private Integer idColumn;

    @ManyToOne(fieldName = "foreign_key")
    private OneToManyBean oneToManyBean;

    public Integer getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(Integer idColumn) {
        this.idColumn = idColumn;
    }

    public OneToManyBean getOneToManyBean() {
        return oneToManyBean;
    }

    public void setOneToManyBean(OneToManyBean oneToManyBean) {
        this.oneToManyBean = oneToManyBean;
    }
}
