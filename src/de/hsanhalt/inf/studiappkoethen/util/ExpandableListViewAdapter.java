package de.hsanhalt.inf.studiappkoethen.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hsanhalt.inf.studiappkoethen.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingManager;
import android.R;
import android.R.layout;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Der ExpandableListView Adapter
 * hier werden die Daten in die Activity geladen
 * @author G. Kauf
 * Grundfunktionalitaet ist gegeben.
 *
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter
{

	private Context context;
	BuildingCategoryManager bc;//Zugriff auf Kategorien
	int parentSize=bc.getInstance().getSize(); //Anzahl aller Kategorien (4 oder 3.. kp weiss ich atm nich lol -> dynamisch)
	int childSize=0;
	private static String [] parentEntries; //Unsere Kategorien
	private static String [][] childEntries; // Alle Gebaeude passend zur Kategorie!
//	private static String[][]testc={	
//				{"UnterNummer 1"},
//				{"UnterNummer 2"},
//				{"UnterNummer 3"},
//				{"UnterNummer 4"},
//				{"UnterNummer 5"}
//	};
//	private static String[] testp={"Nummer1","Nummer 2","Nummer 3", "Nummer 4","Nummer 5"};
	BuildingManager bmanager; // Zugriff auf Gebaeude




	/**
	 * Laedt alle Gebaeude, nach Kategorie geordnet, in die ExpandableList
	 * @param newcontext
	 */
	
	public ExpandableListViewAdapter(Context newcontext){
		
		context=newcontext;


        BuildingCategory[] category=bc.getInstance().getBuildingCategories();   // hier sind alle Kategorien drin
		 parentEntries = new String[parentSize];
		 childEntries = new String[parentSize][];
		
		 for(int i=0;i<parentSize;i++)
         {
			 List<Building> tmp = bmanager.getInstance().getBuildingList(category[i]);
			 childEntries[i]= new String[tmp.size()];

			 parentEntries[i]=childEntries[i][0]=category[i].getName();

             Iterator<Building> iter = tmp.iterator();
             int k = 0;
             while(iter.hasNext())
             {
                 this.childEntries[i][k++] = iter.next().getName();
                 iter.remove();
             }
		 }
		 
		 Log.d("ExtpandableListView","Created parentEntries : " + parentEntries.length + "and childEntries : "+childEntries.length );
		 
/*		 
		 while(buildingList.iterator().hasNext()){

			 Building it=buildingList.iterator().next();

				 for(int i=0;i<size;i++){
					 if(it.getBuildingCategory().equals(category[i]))
						 //Wenn das aktuelle Gebaeude einer Kategorie zugeordnet wurde
						 //(sollte immer klappen - ausser ein Gebaeude hat keine Kategorie)

					 categoryEntries.add(it);	 //Fuege das Gebaeude der richtigen Kategorie zu
					 

				 }

		 }
*/
 	}


	@Override
	public boolean hasStableIds()
	{
		return true;
	}
	
	/**
	 * Hier werden die im Konstruktor gefuellten werte zurueckgegeben.
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{

		return childEntries[groupPosition][childPosition];
//		return testc[groupPosition][childPosition];
	}
	/**
	 * Gibt die ID der Untergrupopeneintraege zurueck.
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}
	
	/**
	 * Gibt das View Objekt bzgl. der Child-Elemente (Untergruppe) zurueck. 
	 * 
	 */
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent)
	{	
//        if (view == null) {
////            view = inflater.inflate(R.layout.list_item_child, view,false);
//        }
//
////        TextView textView = (TextView) view.findViewById(R.id.list_item_text_child);
//       
////        textView.setText(childEntries[groupPosition][childPosition]);

		TextView tv= new TextView(context);
//        tv.setText(childEntries[groupPosition][childPosition]);
//		tv.setText(testc[groupPosition][childPosition]);
		
		tv.setText(childEntries[childPosition][0]);
        tv.setPadding(30, 5, 5,5);
        tv.setTextSize(20);
        return tv;
		
	}
	/**
	 * Gibt das View Objekt bzgl. der Parent-Elemente (Obergruppe) zurueck.
	 * 
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent)
	{

//		 if (view == null) {
////	            view = inflater.inflate(R.layout.list_item_parent, parent,false);
//	        }
//
////	        TextView textView = (TextView) view.findViewById(R.id.list_item_text_view);
//	       
////	        textView.setText(getGroup(groupPosition).toString());

		TextView tv= new TextView(context);
//        tv.setText(parentEntries[groupPosition]);
		tv.setText(parentEntries[groupPosition]);
        tv.setPadding(50, 5, 5,5);
        tv.setTextSize(20);
        return tv;
	        
	        
	}
	
	/**
	 * Gibt die Groesse des jeweiligen Gruppeneintrages zurueck.
	 */
	@Override
	public int getChildrenCount(int groupPosition)
	{
		return parentEntries[groupPosition].length();
//		return testc.length;
	}
	/**
	 * Gibt die gewuenschte Gruppe zurueck.
	 */
	@Override
	public Object getGroup(int groupPosition)
	{

		return parentEntries[groupPosition];
//		return testp[groupPosition];
	}
	/**
	 * Gibt die Gruppengroesse zurueck.
	 */
	@Override
	public int getGroupCount()
	{
		return parentSize;
//		return testp.length;
	}
	/**
	 * Gibt die Gruppen-ID zurueck.
	 */
	@Override
	public long getGroupId(int groupPosition)
	{

		return groupPosition;
	}



	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{

		return true;
	}




	


}
