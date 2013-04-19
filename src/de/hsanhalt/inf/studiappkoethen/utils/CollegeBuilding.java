package de.hsanhalt.inf.studiappkoethen.utils;

import java.net.URL;

public class CollegeBuilding extends Building
{
	/**
	 * Die Nummer des Gebauedes, in dem sich dieses Gebaeude bzw diese Abteilung befindet.
	 */
	private final int numberOfBuilding;
	/**
	 * Die Nummer des Fachbereiches um den es sich bei diesem Gebaeude handelt.
	 */
	private final int numberOfFaculty;
	
	public CollegeBuilding(String name, String street, String houseNumber,	String postalCode, String city, int latitude, int longitude, String description, int numberOfBuilding, int numberOfFaculty)
	{
		this(name, street, houseNumber, postalCode, city, latitude, longitude, description, numberOfBuilding, numberOfFaculty, (URL)null);
	}
	
	public CollegeBuilding(String name, String street, String houseNumber,	String postalCode, String city, int latitude, int longitude, String description, int numberOfBuilding, int numberOfFaculty, String url)
	{
		super(name, street, houseNumber, postalCode, city, latitude, longitude,	description, url);
		this.numberOfBuilding = numberOfBuilding;
		this.numberOfFaculty = numberOfFaculty;
	}

	public CollegeBuilding(String name, String street, String houseNumber,	String postalCode, String city, int latitude, int longitude, String description, int numberOfBuilding, int numberOfFaculty, URL url)
	{
		super(name, street, houseNumber, postalCode, city, latitude, longitude,	description, url);
		
		this.numberOfBuilding = numberOfBuilding;
		this.numberOfFaculty = numberOfFaculty;
	}
	
	/**
	 * Gibt die Nummer des Gebaeudes zurueck.
	 * @return
	 */
	public int getNumberOfBuilding()
	{
		return this.numberOfBuilding;
	}
	
	/**
	 * Gibt die Fachbereichsnummer des Gebaedes zurueck.
	 * @return
	 */
	public int getNumberOfFaculty()
	{
		return this.numberOfFaculty;
	}
}
