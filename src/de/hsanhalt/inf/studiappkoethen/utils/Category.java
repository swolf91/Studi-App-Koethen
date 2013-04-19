package de.hsanhalt.inf.studiappkoethen.utils;

public class Category
{
	private final byte id;
	private final String name;
	
	public Category(byte id, String name)
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
