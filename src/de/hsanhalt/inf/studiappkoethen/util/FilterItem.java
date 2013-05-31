package de.hsanhalt.inf.studiappkoethen.util;

import java.util.ArrayList;
import java.util.List;

public class FilterItem
{
	private byte category;
	private List<Byte> buildings = new ArrayList<Byte>();
	
	public FilterItem(byte newCategory) {
		category = newCategory;
	}
	
	public byte getCategory() {
		return category;
	}
	
	public List<Byte> getBuildings() {
		return buildings;
	}
	
	public void setCategory(byte newCategory) {
		category = newCategory;
	}
	
	public void addBuilding(byte newBuilding) {
		buildings.add(newBuilding);
	}
}
