package de.hsanhalt.inf.studiappkoethen.util.xml.buildings;

public class BuildingCategory
{
    /**
     * Die ID der Kategorie
     */
    private final byte id;
    /**
     * Der Name der Kategorie
     */
    private final String name;
    /**
     * Der Pfad zum Icon dieser Kategorie
     */
    private final String iconPath;

    /**
     * Erstellt ein neues Kategorieobjekt
     * @param id - ID der Kategorie
     * @param name - Name der Kategorie
     * @param iconPath - Pfad zum Icon dieser Kategorie
     */
    public BuildingCategory(byte id, String name, String iconPath)
    {
        this.id = id;
        this.name = name;
        this.iconPath = iconPath;
    }

    /**
     * Gibt die ID der Kategorie zurueck
     * @return
     */
    public byte getID()
    {
        return this.id;
    }

    /**
     * Gibt den Namen der Kategorie zurueck.
     * @return
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gibt den Pfad zum Kategorieicon zurueck
     * @return
     */
    public String getIconPath()
    {
        return this.iconPath;
    }
}
