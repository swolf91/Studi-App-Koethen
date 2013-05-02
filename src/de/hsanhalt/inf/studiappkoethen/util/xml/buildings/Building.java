package de.hsanhalt.inf.studiappkoethen.util.xml.buildings;

import java.net.URL;

public class Building
{
	/**
	 * beinhaltet den Namen des Gebaeudes
	 */
	private final String name;
	/**
	 * beinhaltet die Kategorie dieses Gebaeudes
	 */
	private final BuildingCategory buildingCategory;
	/**
	 * beinhaltet die Strasse in der sich das Gebaeude befindet
	 */
	private final String street;
	/**
	 * Hausnummer des Gebaeudes
	 */
	private final String houseNumber;
	/**
	 * Ort indem sich Gebaeude befindet
	 */
	private final String city;
	/**
	 * Postleitzahl des Ortes
	 */
	private final String postalCode;
	/**
	 * Beschreibung des Gebaeudes
	 */
	private final String description;
    /**
     * Telefonnummer des Gebaeudes
     */
    private final String phoneNumber;
	/**
	 * Webseite des Gebaeudes
	 */
	private final URL url;
	/**
	 * Der Breitengrad in dem sich das Gebaeude befindet 
	 * Festkommadarstellung (immer 7 Stellen nach dem Komma) in int umwandeln um Rundungsfehler zu vermeiden!
	 * Wert von -90 bis 90 mit 7 Kommastellen, also 90 = 900000000
	 */
	private final Integer latitude;
	/**
	 * Der Laengengrad in dem sich das Gebaeude befindet1
	 * Festkommadarstellung (immer 7 Stellen nach dem Komma) in int umwandeln um Rundungsfehler zu vermeiden!
	 * Werte zwischen -180 und 180
	 */
	private final Integer longitude;
	
	/**
	 * Dieser Konstruktor fuellt alle Werte, und matched den String zu einem URL Objekt, wenn es sich dabei um eine URL handeln sollte.
	 * @param name - Name des Gebaeudes
	 * @param street - Strasse des Gebaeudes
	 * @param houseNumber - Hausnummer
	 * @param postalCode - Postleitzahl
	 * @param city - Ort
	 * @param description - Beschreibung
	 * @param url - URL des Gebaeudes
	 */
	public Building(String name, BuildingCategory buildingCategory, String street, String houseNumber, String postalCode, String city, String phoneNumber, Integer latitude, Integer longitude, String description, String url)
	{
        this.name = name;
        this.buildingCategory = buildingCategory;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.phoneNumber = phoneNumber;

        if(url != null)
        {
            URL matchedURL;
            try
            {
                matchedURL = new URL(url);
            }
            catch(Exception e)
            {
                matchedURL = null;
            }
            this.url = matchedURL;
        }
        else
        {
            this.url = null;
        }

	}
	
	/**
	 * Gibt den Namen des Gebaeudes zurueck.
	 * @return
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Gibt die Strasse des Gebaeudes zurueck.
	 * @return
	 */
	public String getStreet()
	{
		return this.street;
	}
	
	/**
	 * Gibt die Hausnummer des Gebaeudes zurueck.
	 * @return
	 */
	public String getHouseNumber()
	{
		return this.houseNumber;
	}
	
	/**
	 * Gibt die Postleitzahl des Gebaeudes zurueck.
	 * @return
	 */
	public String getPostalCode()
	{
		return this.postalCode;
	}
	
	/**
	 * Gibt den Prt des Gebaeudes zurueck.
	 * @return
	 */
	public String getCity()
	{
		return this.city;
	}
	
	/**
	 * Gibt den Breitengrad als Integer der Festkommadarstellung zurueck.
	 * (Achtung: 7 Stellen nach dem Komma sind hier vor dem Komma :D)
	 * @return 
	 */
	public Integer getLatitude()
	{
		return this.latitude;
	}
	
	/**
	 * Gibt den Breitengrad zurueck.
	 * @return
	 */
	public Double getExactLatitude()
	{
		return (double) this.latitude / Math.pow(10, 7);
	}
	
	/**
	 * Gibt den Laengengrad als Integer der Festkommadarstellung zurueck.
	 * (Achtung: 7 Stellen nach dem Komma sind hier vor dem Komma :D)
	 * @return
	 */
	public Integer getLongitude()
	{
		return this.longitude;
	}
	
	/**
	 * Gibt den Laengengrad zurueck.
	 * @return
	 */
	public Double getExactLongitude()
	{
		return (double) this.longitude / Math.pow(10, 7);
	}
	
	/**
	 * Gibt die Beschreibung des Gebaeudes zurueck.
	 * @return
	 */
	public String getDescription()
	{
		return this.description;
	}
	
	/**
	 * Gibt die URL des Gebaeudes zurueck.
	 * @return
	 */
	public URL getURL()
	{
		return this.url;
	}
	
	/**
	 * Gibt die Kategorie in der dieses Gebaeude eingeordnet ist zurueck.
	 * @return
	 */
	public BuildingCategory getBuildingCategory()
	{
		return this.buildingCategory;
	}
	
	@Override
	public String toString()
	{
		return this.getName();
	}
}
