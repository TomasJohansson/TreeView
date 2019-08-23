package com.programmerare.samplesforshinemtreeview.geographicareas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeographicAreaRepository {
    private final static int ID_USED_AS_PARENT_WHEN_TOP_LEVEL = 0;

    public final static int ID_CONTINENT_NORTH_AMERICA = 10;
//    private final static int ID_CONTINENT_SOUTH_AMERICA = 20;
//    private final static int ID_CONTINENT_EUROPE = 30;
//    private final static int ID_CONTINENT_ASIA = 40;
//    private final static int ID_CONTINENT_AFRICA = 50;
//    private final static int ID_CONTINENT_AUSTRALIA = 60;
//    private final static int ID_CONTINENT_ANTARCTICA = 70;

//    private final static int ID_COUNTRY_CANADA = 101;
    public final static int ID_COUNTRY_UNITED_STATES = 102;
//    private final static int ID_COUNTRY_MEXICO = 103;

//    private final static int ID_COUNTRY_BRAZIL = 201;
//    private final static int ID_COUNTRY_ARGENTINA = 202;
//    private final static int ID_COUNTRY_PERU = 203;
//
//    private final static int ID_COUNTRY_UNITED_KINGDOM = 301;
//    private final static int ID_COUNTRY_GERMANY = 302;
//    private final static int ID_COUNTRY_FRANCE = 303;
//    private final static int ID_COUNTRY_SWEDEN = 304;
//
//    private final static int ID_COUNTRY_CHINA = 401;
//    private final static int ID_COUNTRY_INDIA = 402;
//    private final static int ID_COUNTRY_RUSSIA = 403;
//
//    private final static int ID_COUNTRY_NIGERIA = 501;
//    private final static int ID_COUNTRY_ETHIOPIA = 502;
//    private final static int ID_COUNTRY_EGYPT = 503;
//
//    private final static int ID_STATE_OF_US_NEW_YORK = 1021;
//    private final static int ID_STATE_OF_US_CALIFORNIA = 1022;
    public final static int ID_STATE_OF_US_FLORIDA = 1023;
//
//    private final static int ID_COUNTY_OF_US_NEW_YORK_STATE_NEWYORK = 10211;
//    private final static int ID_COUNTY_OF_US_NEW_YORK_STATE_ALBANY  = 10212;
//    private final static int ID_COUNTY_OF_US_NEW_YORK_STATE_NIAGARA = 10213;

    public final static int ID_CITY_OF_SWEDEN_STOCKHOLM = 30411;

    private static GeographicAreaRepository geographicAreaRepository = new GeographicAreaRepository();
    public static GeographicAreaRepository getInstance() {
        return geographicAreaRepository;
    }

    private final List<GeographicArea> allGeographicAreas;
    private final List<GeographicArea> topLevelGeographicAreas;
    private final Map<Integer, GeographicArea> allGeographicAreasById;

    private GeographicAreaRepository() {
        // This data below could be defined in a CSV file but here are instead the rows hardcoded strings
        // The columns are like this (with the last field population being optional)
        // int id, String name, GeographicLevel geographicLevel, int idParent, Integer population
        final List<String> csvRows = Arrays.asList(
            // continents
            "10, North America, 1, 0",
            "20, South America, 1, 0",
            "30, Europe,        1, 0",
            "40, Asia,          1, 0",
            "50, Africa,        1, 0",

            // countries:
            "101, Canada,           2, 10",
            "102, United States,    2, 10",
            "103, Mexico,           2, 10",

            "201, Brazil,           2, 20",
            "202, Argentina,        2, 20",
            "203, Peru,             2, 20",

            "301, United Kingdom,   2, 30,  66040000",
            "302, Germany,          2, 30,  82890000",
            "303, France,           2, 30,  65170000",
            "304, Sweden,           2, 30,  10200000",

            "401, China,    2, 40",
            "402, India,    2, 40",
            "403, Russia,   2, 40",

            "501, Nigeria,  2, 50",
            "502, Ethiopia, 2, 50",
            "503, Egypt,    2, 50",

            // sublevel 1 (some counties in Sweden)
            "3041, Stockholm,        3, 304",
            "3042, Västra Götaland,  3, 304",
            "3043, Skåne,            3, 304",

            // sublevel 2 (some municipalitis in Sweden)
            "30412, Danderyd,       4, 3041",
            "30411, Stockholm City, 4, 3041",
            "30413, Solna,          4, 3041",

            "30421, Gothenburg, 4, 3042",
            "30422, Borås,      4, 3042",
            "30423, Mölndal,    4, 3042",

            "30431, Malmö,          4, 3043",
            "30432, Helsingborg,    4, 3043",
            "30433, Lund,           4, 3043",

            // sublevel 1 (some US states)
            "1021, New York State,  3, 102, 19540000",
            "1022, California,      3, 102, 39560000 ",
            "1023, Florida,         3, 102, 21300000",

            // sublevel 2 (some counties in New York State)
            "10211, New York County,    4, 1021",
            "10212, Niagara County,     4, 1021",
            "10213, Albany County,      4, 1021"
        );

        allGeographicAreas = new ArrayList<GeographicArea>();
        for(String csvRow : csvRows) {
            // int id, String name, GeographicLevel geographicLevel, int idParent, Integer population
            String[] columns = csvRow.split("\\s*,\\s*");
            Integer population = columns.length > 4 ? Integer.parseInt(columns[4].trim()) : null;
            allGeographicAreas.add(
                new GeographicArea(
                    Integer.parseInt(columns[0]), // id
                    columns[1], // name
                    GeographicLevel.getGeographicLevelFromIntegerValue(Integer.parseInt(columns[2])),
                    Integer.parseInt(columns[3]), // idParent
                    population
                )
            );
        }

        allGeographicAreasById = new HashMap<Integer, GeographicArea>();
        topLevelGeographicAreas = new ArrayList<GeographicArea>();
        for (GeographicArea geographicArea : allGeographicAreas) {
            allGeographicAreasById.put(geographicArea.getId(), geographicArea);
            if(geographicArea.getIdParent() == ID_USED_AS_PARENT_WHEN_TOP_LEVEL) {
                topLevelGeographicAreas.add(geographicArea);
            }
            for (GeographicArea geographicAreaPotentialChild : allGeographicAreas) {
                if(geographicAreaPotentialChild.getIdParent() == geographicArea.getId()) {
                    geographicArea.addGeographicSubArea(geographicAreaPotentialChild);
                }
            }
        }
    }

    public GeographicArea getGeographicAreaById(int id) {
        if(allGeographicAreasById.containsKey(id)) {
            return allGeographicAreasById.get(id);
        }
        else return null;
    }

    public List<GeographicArea> getTopLevelGeographicAreas() {
        return topLevelGeographicAreas;
    }
}
