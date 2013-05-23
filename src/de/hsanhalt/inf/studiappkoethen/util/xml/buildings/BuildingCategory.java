package de.hsanhalt.inf.studiappkoethen.util.xml.buildings;

public class BuildingCategory
{
    private final byte id;
    private final String name;
    private final String iconPath;

    public BuildingCategory(byte id, String name, String iconPath)
    {
        this.id = id;
        this.name = name;
        this.iconPath = iconPath;
    }

    public byte getID()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getIconPath()
    {
        return this.iconPath;
    }
}
