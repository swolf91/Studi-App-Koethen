package de.hsanhalt.inf.studiappkoethen.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.expandablelist.ExpandableListAdapter;
import de.hsanhalt.inf.studiappkoethen.util.expandablelist.ExpandableListEntry;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingManager;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.CollegeBuilding;
import de.hsanhalt.inf.studiappkoethen.xml.persons.Person;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonCategory;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonManager;
/**
 * 
 * Enthaelt alle Funktionen um Interaktionen mit der ExpandableList ACtivity zu verwalten.
 *
 */
public class ExpandableListActivity extends Activity
{
    
    ExpandableListView expandableListView;
    ExpandableListAdapter<PersonCategory, Person> expandableListAdapterPerson;
    ExpandableListAdapter<BuildingCategory, Building> expandableListAdapterBuilding;
    
    /** TODO engaging test...
     * 
     * Initialaufbau der ExpandableList Activity.Gibt der Activity den Adapter und den Listener (jeweils fuer Personen und Gebaeude Objekte).
     */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		boolean isCampus;
		isCampus=(this.getIntent().getBooleanExtra("isCampus",true));
		
		if(isCampus){
			
			this.setContentView(R.layout.activity_expandablelist_campus);
			this.expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Campus);
//	        this.expandableListAdapterPerson = new ExpandableListAdapter<PersonCategory, Person>(this, this.getPersonList()); 
//	        this.expandableListView.setAdapter(this.expandableListAdapterPerson);
		}else{

			this.setContentView(R.layout.activity_expandablelist_koethen);
	        this.expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Koethen);
	        
			
		}
		this.expandableListAdapterBuilding = new ExpandableListAdapter<BuildingCategory, Building>(this, this.getBuildingList(isCampus)); 
        this.expandableListView.setAdapter(this.expandableListAdapterBuilding);
		this.expandableListView.setOnChildClickListener(myListItemClicked);
		// wenn auf Koethen dann
		// expandable list laedt alle geb außer campus-geb
		// sonst exp list bekommt nur campus bilder
		

    }

    /**
     * Laedt die Gebaeude-Daten fuer den ExpandableListAdapter.
     * @param true, wenn es ein Campusgebaeude ist; false, wenn es ein Gebaeude aus Koethen ist, dass nicht zum Campus gehoert.
     */
    private List<ExpandableListEntry<BuildingCategory, Building>> getBuildingList(boolean isCampusBuilding)
    {
        List<ExpandableListEntry<BuildingCategory, Building>> entryList = new ArrayList<ExpandableListEntry<BuildingCategory, Building>>();
        BuildingCategoryManager buildingCategoryManager = BuildingCategoryManager.getInstance();
        BuildingManager buildingManager = BuildingManager.getInstance();
       
        for(BuildingCategory buildingCategory : buildingCategoryManager.getBuildingCategories())
        {
            List<Building> buildings = buildingManager.getBuildingList(buildingCategory);
            
            Iterator<Building> it = buildings.iterator();
            while(it.hasNext()){
            	Building building = it.next();
            	if(!(building instanceof CollegeBuilding) && isCampusBuilding)
            	{
            		it.remove();
        		}
            	if(building instanceof CollegeBuilding && !isCampusBuilding){
            		it.remove();
            	}
            	
            }
            
            if(!buildings.isEmpty())
            {
            	entryList.add(new ExpandableListEntry<BuildingCategory, Building>(buildingCategory, buildings.toArray(new Building[buildings.size()])));
            } 
        }

        return entryList;
    }
    /**
     * Lädt die Personen-Daten fuer den ExpandableListAdapter.
     * @return Liste mit Eintraegen.
     */
    private List<ExpandableListEntry<PersonCategory, Person>> getPersonList()
    {
        List<ExpandableListEntry<PersonCategory, Person>> entryList = new ArrayList<ExpandableListEntry<PersonCategory, Person>>();
        PersonCategoryManager personCategoryManager = PersonCategoryManager.getInstance();
        PersonManager personManager = PersonManager.getInstance();
        
        for(PersonCategory personCategory : personCategoryManager.getPersonCategories())
        { 
        	List<Person> persons = personManager.getPersonList(personCategory);
        
	      
	        
	        if(!persons.isEmpty())
	        {
	        	entryList.add(new ExpandableListEntry<PersonCategory, Person>(personCategory, persons.toArray(new Person[persons.size()])));
	        	
	        } 
        }
        return entryList;
    }
    /**
     * Holt das Menue, wenn das Optionsmenue erstellt wird.
     * (wird nicht genutzt )
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		 
		getMenuInflater().inflate(R.menu.expandable_list, menu);
		return true;
	}

	/**
	 * Beschreibt die Funktionen der ExpandableList Activity.
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.action_close:
            moveTaskToBack(true);
            return true;

        case R.id.action_main:
            Intent intent  = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Listener für die Klicks auf die ExpandableList.
     */
    private OnChildClickListener myListItemClicked =  new OnChildClickListener()
    {
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
        {
            Intent intent = new Intent(getBaseContext(), DetailActivity.class);
            intent.putExtra("category", expandableListAdapterBuilding.getGroup(groupPosition).getID());
            intent.putExtra("building", expandableListAdapterBuilding.getChild(groupPosition, childPosition).getID());
            startActivity(intent);
            return true;
        }
    };


}
