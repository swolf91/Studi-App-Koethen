package de.hsanhalt.inf.studiappkoethen.xml.persons;
/**
 * 
 * Dieses Objekt kategorisiert ein bzw. mehrere Personenobjekt(e).
 *
 */
public class PersonCategory
{
    private final byte id;
    private final String name;
    /**
     * Jede kategorie besteht hat eine ID und einen Namen. Die ID wird zum Vergleichen von Kategorien genutzt.
     * @param id byte-Wert
     * @param name Kategoriename als String
     */
    public PersonCategory(byte id, String name)
    {
        this.id = id;
        this.name = name;
    }
    /**
     * Gibt eine ID zurueck.
     * @return byte
     */
    public byte getID()
    {
        return this.id;
    }
    /**
     * Gibt einen Kategorienamen zurueck.
     * @return String
     */
    public String getName()
    {
        return this.name;
    }
    /**
     * Gibt die Kategorie eines Kategorieobjektes als String zurueck.
     */
    public String toString()
    {
    	return this.getName();
    }
}
