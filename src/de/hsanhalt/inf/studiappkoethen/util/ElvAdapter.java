package de.hsanhalt.inf.studiappkoethen.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
/**
 * Der ExpandableListView Adapter
 * hier werden die Daten in die Activity geladen
 * @author G. Kauf
 *
 */
public class ElvAdapter extends BaseExpandableListAdapter {
	private Context context;
	
	BuildingCategoryManager bc;
	int size=bc.getInstance().getSize();
		
	String[] categoryEntries=null;
	
	
	BuildingManager bmanager;
	List<Building> buildingList= new ArrayList<Building>();
	
	
	/**
	 * Lädt alle Gebaeude, nach Kategorie geordnet, in die ExpandableList
	 * @param newcontext
	 */
	//TODO Baustelle.................
	public ElvAdapter(Context newcontext){
	
		 this.context=newcontext;
		 
		 buildingList=bmanager.getInstance().getBuildingList(bc.getInstance().getAllBuildingCategoriesbyCategory());
		 
		 BuildingCategory []category = new BuildingCategory[size];
		 category=bc.getAllBuildingCategoriesbyCategory();
		 
		 int index=size;
		 while(buildingList.iterator().hasNext()){
			 
			 Building it=buildingList.iterator().next();
			 
			 
				 
				 for(int i=0;i<size;i++){
					 if(it.getBuildingCategory().equals(category[i])) 
						 //Wenn das aktuelle Gebaeude einer Kategorie zugeordnet wurde
						 //(sollte immer klappen - ausser ein Gebaeude hat keine Kategorie)
						 
					 categoryEntries[i]=it.getBuildingCategory().getName(); //2 Dimensionales Array?! TODO!!
					 //Fuege das Gebaeude der richtigen Kategorie zu
				 
				 				 
			 }
			 
		 }
		 
		 categoryEntries=new String[size];
		 
		 
		 categoryEntries=bc.getInstance().getAllBuildingCategoriesbyString();
		 
	}



	@Override
	public boolean hasStableIds()
	{

		return false;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{

		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{

		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent)
	{

		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{

		return 0;
	}

	@Override
	public Object getGroup(int groupPosition)
	{

		return null;
	}

	@Override
	public int getGroupCount()
	{

		return 0;
	}

	@Override
	public long getGroupId(int groupPosition)
	{

		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent)
	{
		
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		
		return true;
	}


}
