package de.hsanhalt.inf.studiappkoethen.utils;

public class BuildingCategoryManager
{
	private static BuildingCategoryManager INSTANCE;
	
	public static BuildingCategoryManager getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new BuildingCategoryManager();
		}
		return INSTANCE;
	}
	
	private BuildingCategoryManager()
	{
		//TODO load categoryxml and save it!
	}
	
	public BuildingCategory getCategory(int id)
	{
		return null;
	}
	
	public BuildingCategory getCategory(String name)
	{
		return null;
	}
}
