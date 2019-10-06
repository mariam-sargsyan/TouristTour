package dao.bean;

import dao.annotations_dao.Field;
import dao.annotations_dao.OneToMany;
import dao.annotations_dao.PrimaryKey;
import dao.annotations_dao.Table;

@Table(tableName = "countries")
public class Country {

    @Field(columnName = "name")
    private String name;

    @Field(columnName = "language")
    private String language;

    @Field(columnName = "currency")
    private String currency;

    @Field(columnName = "shengen")
    private Integer shengen;

    @OneToMany(linkedClassName = Cities.class, fieldName = "country_abbreviation" )
    @PrimaryKey
    @Field( columnName = "abbreviation")
    private String abbreviation;

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public Country setLanguage(String language) {
        this.language = language;
        return this;
    }

    public Country setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Country setShengen(Integer shengen) {
        this.shengen = shengen;
        return this;
    }

    public Country setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }
}
