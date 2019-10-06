package dao.bean;

import dao.annotations_dao.Field;
import dao.annotations_dao.ManyToOne;
import dao.annotations_dao.PrimaryKey;
import dao.annotations_dao.Table;

@Table(tableName = "cities")
public class Cities {

    @Field(columnName = "name")
    private String name;

    @ManyToOne(tableName = "countries",fieldName = "abbreviation")
    @Field(columnName = "country_abbreviation")
    private String countryAbbreviation;

    @PrimaryKey
    @Field(columnName = "abbreviation")
    private String abbreviation;

    public Cities setName(String name) {
        this.name = name;
        return this;
    }

    public Cities setCountryAbbreviation(String countryAbbreviation) {
        this.countryAbbreviation = countryAbbreviation;
        return this;
    }

    public Cities setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }
}
