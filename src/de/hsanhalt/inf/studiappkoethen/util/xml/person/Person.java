package de.hsanhalt.inf.studiappkoethen.util.xml.person;

import java.net.URL;

public class Person
{
	private final PersonCategory personCategory;
	/**
	 * beinhaltet den Namen der Person
	 */
	private final String name;
	/**
	 * beinhaltet den Vornamen der Person
	 */
	private final String surname;
	/**
	 * beinhaltet die aktuellen Personalstatus der Person (Prof., Mitarbeiter, usw.)
	 */
	private final String state;
	/**
	 * Fachgebiet der Person
	 */
	private final String specialField;
	/**
	 * Stra�e des Arbeitsortes einer Person
	 */
	private final String street;
	/**
	 * Stra�enummer des Arbeitsortes einer Person
	 */
	private final String houseNumber;
	/**
	*	PLZ des Arbeitsortes einer Person
	*/
	private final String postalCode;
	/**
	* Stadt des Arbeitsortes einer Person
	*/
	private final String city;
	/**
	* Geb�ude in dem die Person ihren Arbeitsort hat
	*/
	private final String buildings;
	/**
	* B�roraum der Person
	*/
	private final String room;
	/**
	 * Personenbeschreibungder Person
	 */
	private final String description;
	/**
	 * Fach oder Arbeitsbereich der Person
	 */
	private final String profession;
	/**
	 * Module welche die Person unterh�lt
	 */
	private final String[] module;
	/**
	 *  F�r was die Person verantwortlich ist
	 */
	private final String[] responsibility;
	/**
	 * Sprechzeiten der Person
	 */
	private final String talkTime;
	/**
	 * Telefonnummer der Person
	 */
	private final String phone;
	/**
	 * E-Mail der Person
	 */
	private final String email;
	/**
	 * Url auf die Website der Person
	 */
	private final URL url;
	
	
	/**
	 * Hier wird �ber die Parameter eingelesen. (manches ist evtl �berfl�ssig, das meiste ist selbsterkl�rend.)
	 * @param name 
	 * @param surname
	 * @param state
	 * @param specialField Fachbereich.
	 * @param street
	 * @param houseNumber
	 * @param postalCode
	 * @param city
	 * @param buildings Geb�udekategorie.
	 * @param room
	 * @param description
	 * @param profession Fach.
	 * @param module Lehrbereiche.
	 * @param responsibility Zust�ndigkeit.
	 * @param talkTime 
	 * @param phone
	 * @param email
	 * @param url Link zur pers�hnlichen Seite.
	 */
	public Person(PersonCategory personCategory, String name, String surname, String state,
			String specialField, String street, String houseNumber,
			String postalCode, String city, String buildings,
			String room, String description, String profession,
			String []module, String []responsibility, String talkTime,
			String phone, String email, String url) {
		
		this.personCategory = personCategory;
		this.name = name;
		this.surname = surname;
		this.state = state;
		this.specialField = specialField;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.buildings = buildings;
		this.room = room;
		this.description = description;
		this.profession = profession;
		this.module = copy(module);
		this.responsibility = copy(responsibility);
		this.talkTime = talkTime;
		this.phone = phone;
		this.email = email;
				
        if (url != null)
        {
            URL matchedURL;
            try
            {
                matchedURL = new URL(url);
            }
            catch (Exception e)
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
	 * Gibt die Kategorie der Person zur�ck.
	 * @return
	 */
	public PersonCategory getPersonCategory()
	{
		return personCategory;
	}
	/**
	 * Gibt den Namen der Person zur�ck.
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Gibt den Vornamen  der Person  zur�ck.
	 * @return
	 */
	public String getSurname()
	{
		return surname;
	}
	/**
	 * Gibt den Status der Person zur�ck.
	 * @return
	 */
	public String getState()
	{
		return state;
	}
	/**
	 * Gibt die Fachrichtung der Person  zur�ck.
	 * @return
	 */
	public String getSpecialField()
	{
		return specialField;
	}
	/**
	 * Gibt die Stra�e der Person zur�ck.
	 * @return
	 */
	public String getStreet()
	{
		return street;
	}
	/**
	 * Gibt die Hausnummer der Person  zur�ck.
	 * @return
	 */
	public String gethouseNumber()
	{
		return houseNumber;
	}
	/**
	 * Gibt die PLZ der Person  zur�ck.
	 * @return
	 */
	public String getPostalCode()
	{
		return postalCode;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String getCity()
	{
		return city;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String getBuildings()
	{
		return buildings;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String getRoom()
	{
		return room;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String getProfession()
	{
		return profession;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String[] getModul()
	{
		return module;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String[] getResponsibility()
	{
		return responsibility;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String getTalkTime()
	{
		return talkTime;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String getPhone()
	{
		return phone;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public String getEmail()
	{
		return email;
	}
	/**
	 * Gibt die  zur�ck.
	 * @return
	 */
	public URL getUrl()
	{
		return url;
	}

	/**
	 * Legt eine 1:1 Kopie des �bergebenen Arrays an.
	 * @param dat : dat kopiert er 1:1.
	 */
	private String [] copy(String [] dat){
		
	String[] tmp=new String[dat.length];
	
	for(int i=0;i<dat.length;i++){
		tmp[i]=dat[i];
	}
	return tmp;
		
	}
}
