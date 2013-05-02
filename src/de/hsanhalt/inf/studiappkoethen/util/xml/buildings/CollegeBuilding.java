package de.hsanhalt.inf.studiappkoethen.util.xml.buildings;

import java.net.URL;

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

	public CollegeBuilding(String name, BuildingCategory buildingCategory, String street, String houseNumber, String postalCode, String city, String phoneNumber, Integer latitude, Integer longitude, String description, Integer numberOfBuilding, Integer numberOfFaculty, String url)
	{
		super(name, buildingCategory, street, houseNumber, postalCode, city, phoneNumber, latitude, longitude, description, url);
		
		this.numberOfBuilding = numberOfBuilding;
		this.numberOfFaculty = numberOfFaculty;
	}
	
	/**
	 * Gibt die Nummer des Gebaeudes zurueck.
	 * @return
	 */
	public Integer getNumberOfBuilding()
	{
		return this.numberOfBuilding;
	}
	
	/**
	 * Gibt die Fachbereichsnummer des Gebaedes zurueck.
	 * @return
	 */
	public Integer getNumberOfFaculty()
	{
		return this.numberOfFaculty;
	}
}
