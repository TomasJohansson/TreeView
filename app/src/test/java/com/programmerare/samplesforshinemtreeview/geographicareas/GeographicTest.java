package com.programmerare.samplesforshinemtreeview.geographicareas;

import static com.programmerare.samplesforshinemtreeview.geographicareas.GeographicAreaRepository.ID_CONTINENT_NORTH_AMERICA;
import static com.programmerare.samplesforshinemtreeview.geographicareas.GeographicAreaRepository.ID_COUNTRY_UNITED_STATES;
import static com.programmerare.samplesforshinemtreeview.geographicareas.GeographicAreaRepository.ID_STATE_OF_US_FLORIDA;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIn.in;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GeographicTest {

    private GeographicAreaRepository geographicAreaRepository;

    private GeographicArea northAmerica, unitedStates, florida;

    @org.junit.Before
    public void setUp() throws Exception {
        geographicAreaRepository = GeographicAreaRepository.getInstance();

        northAmerica = geographicAreaRepository.getGeographicAreaById(ID_CONTINENT_NORTH_AMERICA);
        unitedStates = geographicAreaRepository.getGeographicAreaById(ID_COUNTRY_UNITED_STATES);
        florida = geographicAreaRepository.getGeographicAreaById(ID_STATE_OF_US_FLORIDA);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void getInstance() {
    }

    @org.junit.Test
    public void getGeographicAreaByIdAndGetName() { // "getGeographicAreaById" was used in the setup method but the result is asserted here through the getName method
        assertEquals("North America",   this.northAmerica.getName());
        assertEquals("United States",   this.unitedStates.getName());
        assertEquals("Florida",         this.florida.getName());
    }

    @org.junit.Test
    public void getGeographicLevel() {
        assertEquals(GeographicLevel.CONTINENT,                     northAmerica.getGeographicLevel());
        assertEquals(GeographicLevel.COUNTRY,                       unitedStates.getGeographicLevel());
        assertEquals(GeographicLevel.COUNTRY_DIVISION_FIRST_LEVEL,  florida.getGeographicLevel());
    }

    @org.junit.Test
    public void isCountryLevel() {
        assertFalse(northAmerica.isCountryLevel());
        assertTrue(unitedStates.isCountryLevel());
        assertFalse(florida.isCountryLevel());
    }


    @org.junit.Test
    public void getSubGeographicAreas() {
        final List<GeographicArea> countriesInNorthAmerica = northAmerica.getGeographicSubAreas();
        final List<String> expectedCountryNamesInNorthAmerica = Arrays.asList("United States", "Canada", "Mexico");
        assertNamesOfGeograhicAreas(countriesInNorthAmerica, expectedCountryNamesInNorthAmerica);

        final List<GeographicArea> statesInUnitedStates = unitedStates.getGeographicSubAreas();
        final List<String> expectedStateNames = Arrays.asList("New York State", "California", "Florida");
        assertNamesOfGeograhicAreas(statesInUnitedStates, expectedStateNames);
    }

    @org.junit.Test
    public void getIdParent() {
        assertEquals(northAmerica.getId(), unitedStates.getIdParent());
        assertEquals(unitedStates.getId(), florida.getIdParent());
    }
    @org.junit.Test
    public void getPopulation() {
        assertEquals(21300000, florida.getPopulation());
    }

    @org.junit.Test
    public void isPopulationSpecified() {
        assertTrue(florida.isPopulationSpecified());
        assertFalse(northAmerica.isPopulationSpecified());
    }

    @org.junit.Test
    public void getTopLevelGeographicAreas() {
        // not including Australia and Antarctica in this sample i.e. therefore only five continents
        final List<String> expectedContinentNames = Arrays.asList("North America", "South America", "Europe", "Asia", "Africa");

        final List<GeographicArea> continents = geographicAreaRepository.getTopLevelGeographicAreas();
        assertEquals(expectedContinentNames.size(), continents.size());
        // Java 7 is currently used, but if Java8+ then easier to map objects like this:
        // List<String> names = continents.stream().map(continent -> continent.getName()).collect(Collectors.toList());

        assertNamesOfGeograhicAreas(continents, expectedContinentNames);
    }

    private void assertNamesOfGeograhicAreas(List<GeographicArea> geographicAreas, List<String> expectedNamesOfTheGeographicAreas) {
        assertEquals(expectedNamesOfTheGeographicAreas.size(), geographicAreas.size());
        for(GeographicArea geographicArea : geographicAreas) {
            assertThat(geographicArea.getName(), is(in(expectedNamesOfTheGeographicAreas)));
        }
    }

}