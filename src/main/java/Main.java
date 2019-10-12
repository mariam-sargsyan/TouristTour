import dao.*;
import dao.bean.Cities;
import dao.bean.Country;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    private static final String URL = "jdbc:sqlite:C://Users/mariam.sargsyan/IdeaProjects/Databases/src/main/resources/tourist_tour_info.db";

    public static void main(String[] args) {

        List<String> columns = Arrays.asList("name TEXT",
                "abbreviation TEXT PRIMARY KEY",
                "country_abbreviation TEXT",
                "FOREIGN KEY (country_abbreviation) \n" +
                        "      REFERENCES countries (abbreviation) \n" +
                        "         ON DELETE CASCADE \n" +
                        "         ON UPDATE NO ACTION");
        //InitializerDB.createTable("cities", columns);

        CountryDAO cd = new CountryDAO();
        CitiesDAO citiesDao = new CitiesDAO();
        //List<Country> countries = cd.getAll();

        cd.getKeyClass();

        //Country c = new Country().setName("Sweden").setAbbreviation("SU").setCurrency("Krone").setLanguage("Swedish").setShengen(1);
       // cd.delete("SU");

        int a  = 3;
    }





}