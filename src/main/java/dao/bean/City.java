package dao.bean;

import dao.annotations_dao.Field;
import dao.annotations_dao.ManyToOne;
import dao.annotations_dao.PrimaryKey;
import dao.annotations_dao.Table;

@Table(tableName = "cities")
public class City {

    @Field(columnName = "name")
    private String name;

    @ManyToOne(fieldName = "country_abbreviation")
    private Country country;

    @PrimaryKey
    @Field(columnName = "abbreviation")
    private String abbreviation;

    public City setName(String name) {
        this.name = name;
        return this;
    }

    public City setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
