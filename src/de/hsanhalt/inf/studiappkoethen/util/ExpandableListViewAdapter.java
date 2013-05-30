package de.hsanhalt.inf.studiappkoethen.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingManager;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.util.Log;
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
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
	private Context context;

	BuildingCategoryManager bc;//Zugriff auf Kategorien
	int size=bc.getInstance().getSize(); //Anzahl aller Kategorien (4 oder 3.. kp weiss ich atm nich lol)
	String[] categoryEntries=new String[size];
	BuildingManager bmanager; // Zugriff auf Gebaeude
	List<Building> buildingList= new ArrayList<Building>();


	/**
	 * Laedt alle Gebaeude, nach Kategorie geordnet, in die ExpandableList
	 * @param newcontext
	 */
	//TODO Parameter!
	public ExpandableListViewAdapter(Context newcontext){

		 this.context=newcontext;

		 BuildingCategory []category = new BuildingCategory[size]; // hier sind alle Kategorien drin
		 category=bc.getInstance().getBuildingCategories();
		 String [] parentEntries = new String[size];
		 String [][] childEntries= new String[size][];
		
		 for(int i=0;i<size;i++){
			 
			 List<Building> tmp= new ArrayList<Building>();
			 tmp=bmanager.getInstance().getBuildingList(category[i]);
			 childEntries[i]= new String[tmp.size()];
			 parentEntries[i]=childEntries[i][0]=category[i].getName();
			 
			 for(int k=0;k<tmp.size();i++)
				 childEntries[i][k]=tmp.remove(0).getName();
			 
		 }
		 
		 Log.d("ExtpandableListView","Created parentEntries : " + parentEntries.length + "and childEntries : "+childEntries.length );
		 
		 
//		 while(buildingList.iterator().hasNext()){
//
//			 Building it=buildingList.iterator().next();
//
//				 for(int i=0;i<size;i++){
//					 if(it.getBuildingCategory().equals(category[i]))
//						 //Wenn das aktuelle Gebaeude einer Kategorie zugeordnet wurde
//						 //(sollte immer klappen - ausser ein Gebaeude hat keine Kategorie)
//
//					 categoryEntries.add(it);	 //Fuege das Gebaeude der richtigen Kategorie zu
//					 
//
//				 }
//
//		 }

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
