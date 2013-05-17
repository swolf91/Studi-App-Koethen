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
    public Person(PersonCategory personCategory, String name, String surname, String state, String specialField, String street, String houseNumber, String postalCode, String city, String building, String room, String description, String profession, String[] module, String[] responsibility, String talkTime, String phone, String email, String url)
    {

        this.personCategory = personCategory;
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
        return personCategory;
    }

    /**
     * Gibt den Namen der Person zurueck.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gibt den Vornamen  der Person  zurueck.
     */
    public String getSurname()
    {
        return surname;
    }

    /**
     * Gibt den Status der Person zurueck.
     */
    public String getState()
    {
        return state;
    }

    /**
     * Gibt die Fachrichtung der Person  zurueck.
     */
    public String getSpecialField()
    {
        return specialField;
    }

    /**
     * Gibt die Stra�e der Person zurueck.
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * Gibt die Hausnummer der Person  zurueck.
     */
    public String gethouseNumber()
    {
        return houseNumber;
    }

    /**
     * Gibt die PLZ der Person  zurueck.
     */
    public String getPostalCode()
    {
        return postalCode;
    }

    /**
     * Gibt die  zurueck.
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Gibt die  zurueck.
     */
    public String getBuilding()
    {
        return building;
    }

    /**
     * Gibt die  zurueck.
     */
    public String getRoom()
    {
        return room;
    }

    /**
     * Gibt die  zurueck.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Gibt die  zurueck.
     */
    public String getProfession()
    {
        return profession;
    }

    /**
     * Gibt die  zurueck.
     */
    public String[] getModuls()
    {
        return module;
    }

    /**
     * Gibt die  zurueck.
     */
    public String[] getResponsibilities()
    {
        return responsibility;
    }

    /**
     * Gibt die  zurueck.
     */
    public String getTalkTime()
    {
        return talkTime;
    }

    /**
     * Gibt die  zurueck.
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * Gibt die  zurueck.
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Gibt die  zurueck.
     */
    public URL getUrl()
    {
        return url;
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
}
