package de.hsanhalt.inf.studiappkoethen.util;

import java.util.ArrayList;
import java.util.List;

import de.hsanhalt.inf.studiappkoethen.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingManager;
import android.R.layout;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

/**
 * Der ExpandableListView Adapter
 * hier werden die Daten in die Activity geladen
 * @author G. Kauf
 *
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	BuildingCategoryManager bc;//Zugriff auf Kategorien
	int parentSize=bc.getInstance().getSize(); //Anzahl aller Kategorien (4 oder 3.. kp weiss ich atm nich lol -> dynamisch)
	int childSize=0;
	private static String [] parentEntries; //Unsere Kategorien
	private static String [][] childEntries; // Alle Gebäude passend zur Kategorie!
	BuildingManager bmanager; // Zugriff auf Gebaeude
	


	/**
	 * Laedt alle Gebaeude, nach Kategorie geordnet, in die ExpandableList
	 * @param newcontext
	 */
	
	public ExpandableListViewAdapter(Context newcontext){

		 this.context=newcontext; //hiermit wird die ExpandableListView alias Content bekannt gemacht.

		 BuildingCategory []category = new BuildingCategory[parentSize]; // hier sind alle Kategorien drin
		 category=bc.getInstance().getBuildingCategories();
		 parentEntries = new String[parentSize];
		 childEntries= new String[parentSize][];
		
		 for(int i=0;i<parentSize;i++){
			 
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
	
	/**
	 * Hier werden die im Konstruktor gefuellten werte zurueckgegeben.
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{

		return childEntries[groupPosition][childPosition];
	}
	/*
	 * TODO Note has to be filled 
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}
	
	/**
	 * TODO
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent)
	{
			
		return null;
	}
	/**
	 * Gibt die Groeße des jeweiligen Gruppeneintrages zurueck.
	 */
	@Override
	public int getChildrenCount(int groupPosition)
	{
		return parentEntries[groupPosition].length();
	}
	/**
	 * Gibt die gewuenschte Gruppe zurueck.
	 */
	@Override
	public Object getGroup(int groupPosition)
	{

		return parentEntries[groupPosition];
	}
	/**
	 * Gibt die Gruppengroeße zurueck.
	 */
	@Override
	public int getGroupCount()
	{
		return parentSize;
	}
	/**
	 * Gibt die GruppenId zurueck.
	 */
	@Override
	public long getGroupId(int groupPosition)
	{

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View view, ViewGroup parent)
	{
		if (view==null)
//			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			view = inf.inflate(R, layout) //TODO hier war ich beim letzten mal!
		return null;
		return parent;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{

		return true;
	}
	
	


}
