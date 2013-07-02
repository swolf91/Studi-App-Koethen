package de.hsanhalt.inf.studiappkoethen.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse FilterItem wird zum Aufbau des GoogleMaps-Filters, fuer die angezeigten Gebaeude benoetigt.
 */

public class FilterItem
{
	private byte category;
	private List<Byte> buildings = new ArrayList<Byte>();
	
	public FilterItem(byte newCategory) {
		category = newCategory;
	}
	
	/**
	 * Rueckgabe der Kategorie
	 * @return
	 */
	public byte getCategory() {
		return category;
	}
	
	/**
	 * Rueckgabe der Gebaeude-Liste
	 * @return
	 */
	public List<Byte> getBuildings() {
		return buildings;
	}
	
	/**
	 * Setzen der Kategorie
	 * @param newCategory
	 */
	public void setCategory(byte newCategory) {
		category = newCategory;
	}
	
	/**
	 * Hinzufuegen eines Gebaeudes in die Liste
	 * @param newBuilding
	 */
	public void addBuilding(byte newBuilding) {
		buildings.add(newBuilding);
	}
}
