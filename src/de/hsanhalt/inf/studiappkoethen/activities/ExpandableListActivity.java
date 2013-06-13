package de.hsanhalt.inf.studiappkoethen.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class ExpandableListActivity extends Activity
{
    ExpandableListAdapter<BuildingCategory, Building> expandableListAdapter;
    ExpandableListView expandableListView;
    
    /**
     * Initialaufbau der ExpandableList Activity.Gibt der Activity den Adapter und den Listener.
     */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_expandablelist_koethen);

        //Expandable list aus dem Layout laden
        this.expandableListView = (ExpandableListView) findViewById(R.id.expandablelist_expandablelist_list);

        //Adapter erstellen
        this.expandableListAdapter = new ExpandableListAdapter<BuildingCategory, Building>(this, this.getBuildingList(true)); //TODO -> Koethen bzw. Campus
        //Adapter an ExpandableListView anhaengen
        this.expandableListView.setAdapter(this.expandableListAdapter);

        //listener for child row click
        this.expandableListView.setOnChildClickListener(myListItemClicked);
    }

    /**
     * laedt die Gebaeude-Daten fuer den ExpandableListAdapter.
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
            	if(it instanceof CollegeBuilding && !isCampusBuilding){
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
            entryList.add(new ExpandableListEntry<PersonCategory, Person>(personCategory, persons.toArray(new Person[persons.size()])));
        }
        return entryList;
    }
    /**
     * TODO
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
//		 Inflate the menu; this adds items to the action bar if it is present.
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

//        case R.id.action_list:			//TODO rausnehmen wenn Liste fertig ist
//            startActivity(new Intent(this, ExpandableListActivity.class));
//            return true;


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
            intent.putExtra("category", expandableListAdapter.getGroup(groupPosition).getID());
            intent.putExtra("building", expandableListAdapter.getChild(groupPosition, childPosition).getID());
            startActivity(intent);
            return true;
        }
    };


}
