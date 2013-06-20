package de.hsanhalt.inf.studiappkoethen.xml.persons;

public class PersonCategory
{
    private final byte id;
    private final String name;

    public PersonCategory(byte id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public byte getID()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }
    
    public String toString()
    {
    	return this.getName();
    }
}
