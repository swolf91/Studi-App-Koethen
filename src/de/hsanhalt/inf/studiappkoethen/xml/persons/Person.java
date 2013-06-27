package de.hsanhalt.inf.studiappkoethen.xml.persons;

import java.net.URL;
/**
 * In diesem Objekt finden sich alle Daten zu einer Person wieder.
 *
 */
public class Person
{
    private final PersonCategory personCategory;
    
    private final byte id;
    /**
     * beinhaltet den Namen der Person
     */
    private final String name;
    /**
     * beinhaltet den Nachnamen der Person
     */
    private final String surname;
    /**
     * beinhaltet die aktuellen Personalstatus der Person (Prof., Mitarbeiter, usw.)
     */
    private final String state;
    /**
     * Fachgebiet der Person
     */
    private final String specialField;          // TODO ueberlegen ob nicht vielleicht mehrere Fachgebiete vorhanden sein koennen!?
    /**
     * Strasse des Arbeitsortes einer Person
     */
    private final String street;
    /**
     * Strassenummer des Arbeitsortes einer Person
     */
    private final String houseNumber;
    /**
     * PLZ des Arbeitsortes einer Person
     */
    private final String postalCode;
    /**
     * Stadt des Arbeitsortes einer Person
     */
    private final String city;
    /**
     * Gebaeude in dem die Person ihren Arbeitsort hat
     */
    private final String building;
    /**
     * Bueroraum der Person
     */
    private final String room;
    /**
     * Personenbeschreibung der Person
     */
    private final String description;
    /**
     * Fach oder Arbeitsbereich der Person
     */
    private final String profession;
    /**
     * Module welche die Person unterhaelt
     */
    private final String[] module;
    /**
     * Fuer was die Person verantwortlich ist
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
     * Hier wird ueber die Parameter eingelesen. (manches ist evtl ueberfluessig, das meiste ist selbsterklaerend.)
     *
     * @param specialField   Fachbereich.
     * @param building       Gebaeudekategorie.
     * @param profession     Fach.
     * @param module         Lehrbereiche.
     * @param responsibility Zustaendigkeit.
     */
    public Person(PersonCategory personCategory,Byte id, String name, String surname, String state, String specialField, String street, String houseNumber, String postalCode, String city, String building, String room, String description, String profession, String[] module, String[] responsibility, String talkTime, String phone, String email, String url)
    {

        this.personCategory = personCategory;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.state = state;
        this.specialField = specialField;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.building = building;
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
     * Gibt die Kategorie der Person zurueck.
     */
    public PersonCategory getPersonCategory()
    {
        return this.personCategory;
    }

    /**
     * Gibt den Namen der Person zurueck.
     */
    public String getName()
    {
        return this.name;
    }
    /**
     * Gibt die ID einer Person zurueck.
     */
    public byte getID()
    {
    	return this.id;
    }

    /**
     * Gibt den Vornamen  der Person  zurueck.
     */
    public String getSurname()
    {
        return this.surname;
    }

    /**
     * Gibt den Status der Person zurueck.
     */
    public String getState()
    {
        return this.state;
    }

    /**
     * Gibt die Fachrichtung der Person  zurueck.
     */
    public String getSpecialField()
    {
        return this.specialField;
    }

    /**
     * Gibt die Strasse der Arbeitsadresse einer Person zurueck.
     */
    public String getStreet()
    {
        return this.street;
    }

    /**
     * Gibt die Hausnummer der Arbeitsadresse einer zurueck.
     */
    public String gethouseNumber()
    {
        return this.houseNumber;
    }

    /**
     * Gibt die PLZ der Arbeitsadresse einer Person zurueck.
     */
    public String getPostalCode()
    {
        return this.postalCode;
    }

    /**
     * Gibt die Stadt der Arbeitsadresse einer Person zurueck.
     */
    public String getCity()
    {
        return this.city;
    }

    /**
     * Gibt den Gebaudenamen der Arbeitsadresse einer Person zurueck.
     */
    public String getBuilding()
    {
        return this.building;
    }

    /**
     * Gibt die Raumnummer/Raumbezeichnung der Arbeitsadresse einer Person zurueck.
     */
    public String getRoom()
    {
        return this.room;
    }

    /**
     * Gibt eine Beschreibung zur Person zurueck.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gibt die Fachrichtung einer Person zurueck.
     */
    public String getProfession()
    {
        return this.profession;
    }

    /**
     * Gibt die Module einer Person zurueck.
     */
    public String[] getModuls()
    {
        return this.module;
    }

    /**
     * Gibt den Zustaendikeitsbereich einer Person zurueck.
     */
    public String[] getResponsibilities()
    {
        return this.responsibility;
    }

    /**
     * Gibt die Sprechzeiten einer Person zurueck.
     */
    public String getTalkTime()
    {
        return this.talkTime;
    }

    /**
     * Gibt die Telefonnummer einer Person zurueck.
     */
    public String getPhone()
    {
        return this.phone;
    }

    /**
     * Gibt die E-Mail einer Person zurueck.
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Gibt die URL zur Website einer Person zurueck.
     */
    public URL getUrl()
    {
        return this.url;
    }

    /**
     * Legt eine 1:1 Kopie des uebergebenen Arrays an.
     *
     * @param dat : dat kopiert er 1:1.
     */
    private String[] copy(String[] dat)
    {
        String[] tmp = new String[dat.length];

        for (int i = 0; i < dat.length; i++)
        {
            tmp[i] = dat[i];
        }
        return tmp;
    }
    
    /**
     * Konvertiert eine Person zu einem String.
     */
    public String toString()
    {
        String tmp = "";
        if(this.state != null)
        {
            tmp += this.state + " ";
        }
    	return tmp + this.getName() + " " + this.getSurname();
    }
}
