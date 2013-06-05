package de.hsanhalt.inf.studiappkoethen.util;

import android.os.Bundle;

/*
 * Anwendung:
 * 
 * 		FilterBundle newFilter = new FilterBundle(categoryId); -> Erstellt ein neues FilterBundle mit der Kategorie [categoryId].
 * 
 * 		newFilter.addNewBuilding(buildingId); -> Fuegt ein Gebaeude mit der ID [buildingId] zu der zuletzt erstellten Kategorie hinzu.
 * 												 Wird kein Gebaeude hinzugefuegt, wird die ganze Kategorie angezeigt.
 * 
 * 		newFilter.addNewCategory(nextCategoryId); -> Schliesst die letzte Kategorie ab und erstellt eine Neue mit der Id [nextCategoryId].
 * 
 * 		newFilter.getBundle(); -> Gibt das fertige Filter-Bundle zur�ck.
 * 
 * 
Aufbau des Bundles:

	categoryBundle {
		category = "ID der Kategorie"
		nextBuilding {
			building = "ID des Gebaeudes"
			nextBuilding {
				building = "ID des naechsten Gebaeudes"
				nextBuilding {
					...
				}
			}
		}
		nextCategory {
			category = "ID der naechsten Kategorie"
			nextBuilding {
				building = "ID des Gebaeudes"
				nextBuilding {
					building = "ID des naechsten Gebaeudes"
					nextBuilding {
						...
					}
				}
			}
			nextCategory {
				category = "ID der uebernaechsten Kategorie"
				nextBuilding {
					building = "ID des Gebaeudes"
					nextBuilding {
						building = "ID des naechsten Gebaeudes"
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
