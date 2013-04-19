package de.hsanhalt.inf.studiappkoethen.utils;

import java.net.URL;

public class Building
{
	/**
	 * beinhaltet den Namen des Gebaeudes
	 */
	private final String name;
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
	 * Webseite des Gebaeudes
	 */
	private URL url;
	/**
	 * Der Breitengrad in dem sich das Gebaeude befindet 
	 * Festkommadarstellung (immer 7 Stellen nach dem Komma) in int umwandeln um Rundungsfehler zu vermeiden!
	 * Wert von -90 bis 90 mit 7 Kommastellen, also 90 = 900000000
	 */
	private final int latitude;
	/**
	 * Der Laengengrad in dem sich das Gebaeude befindet1
	 * Festkommadarstellung (immer 7 Stellen nach dem Komma) in int umwandeln um Rundungsfehler zu vermeiden!
	 * Werte zwischen -180 und 180
	 */
	private final int longitude;

	/**
	 * Dieser Konstruktor fuellt alle Werte, laest dabei aber die URL auf nichts referenzieren!
	 * @param name - Name des Gebaeudes
	 * @param street - Strasse des Gebaeudes
	 * @param houseNumber - Hausnummer
	 * @param postalCode - Postleitzahl
	 * @param city - Ort
	 * @param description - Beschreibung
	 */
	public Building(String name, String street, String houseNumber,	String postalCode, String city, int latitude, int longitude, String description)
	{
		this(name, street, houseNumber, postalCode, city, latitude, longitude, description, (URL)null);
	}
	
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
	public Building(String name, String street, String houseNumber, String postalCode, String city, int latitude, int longitude, String description, String url)
	{
		this(name, street, houseNumber, postalCode, city, latitude, longitude, description, (URL)null);
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

	/**
	 * Dieser Konstruktor fuellt alle Werte des Gebaeudes.
	 * @param name - Name des Gebaeudes
	 * @param street - Strasse des Gebaeudes
	 * @param houseNumber - Hausnummer
	 * @param postalCode - Postleitzahl
	 * @param city - Ort
	 * @param description - Beschreibung
	 * @param url - URL des Gebaeudes
	 */
	public Building(String name, String street, String houseNumber, String postalCode, String city, int latitude, int longitude, String description, URL url)
	{
		this.name = name;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.url = url;
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
	 * Gibt den Breitengrad als Integer der Festkommadarsteööung zurueck.
	 * (Achtung: 7 Stellen nach dem Komma sind hier vor dem Komma :D)
	 * @return 
	 */
	public int getLatitude()
	{
		return this.latitude;
	}
	
	/**
	 * Gibt den Breitengrad zurueck.
	 * @return
	 */
	public double getExactLatitude()
	{
		return (double) this.latitude / Math.pow(10, 7);
	}
	
	/**
	 * Gibt den Laengengrad als Integer der Festkommadarsteööung zurueck.
	 * (Achtung: 7 Stellen nach dem Komma sind hier vor dem Komma :D)
	 * @return
	 */
	public int getLongitude()
	{
		return this.longitude;
	}
	
	/**
	 * Gibt den Laengengrad zurueck.
	 * @return
	 */
	public double getExactLongitude()
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
	
	@Override
	public String toString()
	{
		return this.getName();
	}
}
