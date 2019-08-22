package com.programmerare.samplesforshinemtreeview.geographicareas;
// Tomas

import java.util.ArrayList;
import java.util.List;

/**
 * One instance of this class represents a continent or a country or some subdivision within a country
 * (for example a state or municipality) depending on the value of 'geographicLevel'
 */
public final class GeographicArea {

    private final int id; // "primary key"
    private final String name;
    private final GeographicLevel geographicLevel;
    private final int idParent;
    private final Integer population;

    public GeographicArea(
        final int id,
        final String name,
        final GeographicLevel geographicLevel,
        final int idParent,
        final Integer population
    ) {
        this.id = id;
        this.name = name;
        this.geographicLevel = geographicLevel;
        this.idParent = idParent;
        this.population = population;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public GeographicLevel getGeographicLevel() {
        return geographicLevel;
    }

    public int getIdParent() {
        return idParent;
    }

    public int getPopulation() { return population; }


    private final List<GeographicArea> subAreas = new ArrayList<GeographicArea>();
    public void addGeographicSubArea(GeographicArea subArea) {
        subAreas.add(subArea);
    }
    public List<GeographicArea> getGeographicSubAreas() {
        return subAreas;
    }

    @Override
    public String toString() {
        return name;
    }

    // convenience methods
    public boolean isCountryLevel() {
        return this.getGeographicLevel() == GeographicLevel.COUNTRY;
    }
    public boolean isPopulationSpecified() {
        return this.population != null;
    }
}