package de.hsanhalt.inf.studiappkoethen.util.xml.buildings;

import java.util.HashSet;
import java.util.Set;

public class BuildingManager
{
	/**
	 * 
	 */
	private static BuildingManager INSTANCE;
	/**
	 * 
	 */
	private Set<Building> buildings;

	/**
	 * 
	 */
	private BuildingManager()
	{
		buildings = new HashSet<Building>();
		// TODO XML DateiEN laden und BuildingObjekte erzeugen.
		// bla bla
	}
	
	public void addBuilding(Building building)
	{
		this.buildings.add(building);
		
		//TODO Sortieren nach Kategorie bla bla.
	}

	/**
	 * Gibt eine Instanz dieser Klasse zurueck und sorgt dafuer, dass auch nur
	 * eine Instanz erstellt wird! Ganz ala Singleton.
	 * 
	 * @return
	 */
	public static BuildingManager getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new BuildingManager();
		}
		return INSTANCE;
	}

}
