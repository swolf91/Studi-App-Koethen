package de.hsanhalt.inf.studiappkoethen.utils;

public class BuildingCategory
{
	private final byte id;
	private final String name;
	
	public BuildingCategory(byte id, String name)
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
}
