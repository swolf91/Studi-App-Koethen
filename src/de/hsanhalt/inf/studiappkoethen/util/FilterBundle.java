package de.hsanhalt.inf.studiappkoethen.util;

import android.os.Bundle;

/*
Aufbau des Bundles:

	categoryBundle {
		category = "ID der Kategorie"
		nextBuilding {
			building = "ID des Gebäudes"
			nextBuilding {
				building = "ID des nächsten Gebäudes"
				nextBuilding {
					...
				}
			}
		}
		nextCategory {
			category = "ID der nächsten Kategorie"
			nextBuilding {
				building = "ID des Gebäudes"
				nextBuilding {
					building = "ID des nächsten Gebäudes"
					nextBuilding {
						...
					}
				}
			}
			nextCategory {
				category = "ID der übernächsten Kategorie"
				nextBuilding {
					building = "ID des Gebäudes"
					nextBuilding {
						building = "ID des nächsten Gebäudes"
						nextBuilding {
							...
						}
					}
				}
				...
			}
		}
	}

*/

public class FilterBundle
{
	private Bundle categoryBundle = new Bundle();
	private Bundle buildingBundle = new Bundle();
	private String sCategory = "category";
	private String sBuilding = "building";
	private String sNextCategory = "nextcategory";
	private String sNextBuilding = "nextbuilding";
	
	private void initalizeCategory() {
		categoryBundle.putBundle(sNextCategory, null);
		categoryBundle.putByte(sCategory, (byte) -1);
		categoryBundle.putBundle(sNextBuilding, null);
	}
	
	private void initalizeBuilding() {
		buildingBundle.putBundle(sNextBuilding, null);
		buildingBundle.putByte(sBuilding, (byte) -1);
	}
	
	public FilterBundle(byte newCategory) {
		initalizeCategory();
		initalizeBuilding();
		categoryBundle.putByte(sCategory, newCategory);
	}
	
	public void addNewBuilding(byte newBuilding) {
		if(buildingBundle.getByte(sBuilding) != (byte) -1) {
			Bundle tmpBundle = new Bundle();
			tmpBundle = (Bundle) buildingBundle.clone();
			
			buildingBundle.putBundle(sNextBuilding, tmpBundle);
		}
		buildingBundle.putByte(sBuilding, newBuilding);
	}
	
	public void addNewCategory(byte newCategory) {
		putCategoryTogether();
		
		Bundle tmpBundle = new Bundle();
		tmpBundle = (Bundle) categoryBundle.clone();
		categoryBundle.putBundle(sNextCategory, tmpBundle);
		
		categoryBundle.putByte(sCategory, newCategory);
		categoryBundle.putBundle(sNextBuilding, null);
		
		initalizeBuilding();

		//Log.i("FILTERBUNDLE", "New Category Bundle: " + categoryBundle.toString());
	}
	
	private void putCategoryTogether() {
		if(buildingBundle.getByte(sBuilding) != (byte) -1) {
			Bundle nextBuildingBundle = new Bundle();
			nextBuildingBundle = (Bundle) buildingBundle.clone();
			categoryBundle.putBundle(sNextBuilding, nextBuildingBundle);
		}
	}
	
	public Bundle getBundle() {
		putCategoryTogether();
		//Log.i("FILTERBUNDLE", "Complete Bundle: " + categoryBundle.toString());
		return categoryBundle;
	}
	
}
