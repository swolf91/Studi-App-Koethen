package de.hsanhalt.inf.studiappkoethen.utils;

public class CategoryManager
{
	private static CategoryManager INSTANCE;
	
	public static CategoryManager getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new CategoryManager();
		}
		return INSTANCE;
	}
	
	private CategoryManager()
	{
		//TODO load categoryxml and save it!
	}
	
	public Category getCategory(int id)
	{
		return null;
	}
	
	public Category getCategory(String name)
	{
		return null;
	}
}
