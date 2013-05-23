package de.hsanhalt.inf.studiappkoethen.util.xml.buildings;

public class CollegeBuilding extends Building
{
    /**
     * Die Nummer des Gebauedes, in dem sich dieses Gebaeude bzw diese Abteilung befindet.
     */
    private final Integer numberOfBuilding;
    /**
     * Die Nummer des Fachbereiches um den es sich bei diesem Gebaeude handelt.
     */
    private final Integer numberOfFaculty;

    public CollegeBuilding(String name, byte id, BuildingCategory buildingCategory, String street, String houseNumber, String postalCode, String city, String phoneNumber, Integer latitude, Integer longitude, String description, Integer numberOfBuilding, Integer numberOfFaculty, String url, String[] images)
    {
        super(name, id, buildingCategory, street, houseNumber, postalCode, city, phoneNumber, latitude, longitude, description, url, images);

        this.numberOfBuilding = numberOfBuilding;
        this.numberOfFaculty = numberOfFaculty;
    }

    /**
     * Gibt die Nummer des Gebaeudes zurueck.
     */
    public Integer getNumberOfBuilding()
    {
        return this.numberOfBuilding;
    }

    /**
     * Gibt die Fachbereichsnummer des Gebaedes zurueck.
     */
    public Integer getNumberOfFaculty()
    {
        return this.numberOfFaculty;
    }
}
