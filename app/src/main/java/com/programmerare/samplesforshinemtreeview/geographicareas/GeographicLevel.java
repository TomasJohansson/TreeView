package com.programmerare.samplesforshinemtreeview.geographicareas;
// Tomas

public enum GeographicLevel {
    CONTINENT(1),
    COUNTRY(2),
    // Countries are divided in different ways, FOR EXAMPLE the first level can be states  https://en.wikipedia.org/wiki/List_of_administrative_divisions_by_country
    COUNTRY_DIVISION_FIRST_LEVEL(3), // e.g. State
    COUNTRY_DIVISION_SECOND_LEVEL(4), // e.g. County
    COUNTRY_DIVISION_THIRD_LEVEL(5); // e.g. City or Municipality

    private final int levelValue;
    GeographicLevel(int levelValue) {
        this.levelValue = levelValue;
    }

    public static GeographicLevel getGeographicLevelFromIntegerValue(int levelValue) {
        // of course this can be optimized with a lookup table (hash table) but performance does not matter for this small sample app
        GeographicLevel[] values = GeographicLevel.values();
        for(GeographicLevel geographicLevel : values) {
            if(geographicLevel.levelValue == levelValue) {
                return geographicLevel;
            }
        }
        return null; // or throw exception ...
    }
}