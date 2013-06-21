package de.hsanhalt.inf.studiappkoethen.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.id;
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
 * Enthaelt alle Funktionen um Interaktionen zwischen ExpandableList und ExpandableListActivity zu verwalten.
 * @author Stefan Wolf, Gordian Kauf
 * @version 1.0
 */
public class ExpandableListActivity extends Activity
{
    
    ExpandableListView expandableListView;
    ExpandableListAdapter<PersonCategory, Person> expandableListAdapterPerson;
    ExpandableListAdapter<BuildingCategory, Building> expandableListAdapterBuilding;
    SharedPreferences expListPreferences;
    boolean showBuildings;
    
    /**  
     * Initialaufbau der ExpandableList Activity.
     * Unterscheidet Koethener Gebaeude und Campus-Gebaeude. 
     * Bei den Campusgebaeude werden neben den Gebaeuden noch Personen angefuehrt.
     * Fuer die Campusgebaeudeliste gibt es zwei Buttons zum wechseln, oberhalb der Liste.
     * 
     */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.expListPreferences = this.getSharedPreferences("lastBuildingPushState", MODE_PRIVATE);
		showBuildings=this.expListPreferences.getBoolean("showBuildings", true);
		
		
		boolean isCampus=this.getIntent().getBooleanExtra("isCampus",true);
				
		if(isCampus){
			
			this.setContentView(R.layout.activity_expandablelist_campus);
			this.expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Campus);
			
			if(this.showBuildings)
			{
				ImageView imageView = (ImageView) this.findViewById(R.id.imageView_Buildings);
				imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.geb_el));
				this.expandableListAdapterBuilding = new ExpandableListAdapter<BuildingCategory, Building>(this, this.getBuildingList(isCampus)); 
		        this.expandableListView.setAdapter(this.expandableListAdapterBuilding);
		        
			}else{
				ImageView imageView = (ImageView) this.findViewById(R.id.imageView_Persons);
				imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.pers_el));
				this.expandableListAdapterPerson = new ExpandableListAdapter<PersonCategory, Person>(this, this.getPersonList()); 
			    this.expandableListView.setAdapter(this.expandableListAdapterPerson);
			}
	       
				
		}
		else{
			
			this.showBuildings=true;
			this.setContentView(R.layout.activity_expandablelist_koethen);
	        this.expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Koethen);
	        this.expandableListAdapterBuilding = new ExpandableListAdapter<BuildingCategory, Building>(this, this.getBuildingList(isCampus)); 
	        this.expandableListView.setAdapter(this.expandableListAdapterBuilding);
			
		}
		
		this.expandableListView.setOnChildClickListener(myListItemClicked);

    }
	
	/**
	 * Hier werden die Klicks auf die Buttons zum Wechseln der Personen und Gebaeuden (Oberkategorie: Campusgebaeude) ausgewertet.
	 * 
	 * @param view ->Zum Erfassen des selektierten Bereiches.
	 */
	public void onButtonClick(View view)
	{
		Boolean state = null;
		switch(view.getId()){
			case id.imageView_Buildings:
				state = true;
				break;

			case id.imageView_Persons:{
				state = false;
				break;
			}
		}
		
		if(state != null && state != this.showBuildings)
		{
			this.expListPreferences.edit().putBoolean("showBuildings", state).commit();
			this.recreate();
		}
	}
	


	/**
     * Laedt die Gebaeude-Daten fuer den ExpandableListAdapter in einem speziellem Objekt für Listeneintraege.
     * @param true, wenn es ein Campusgebaeude ist; false, wenn es ein Gebaeude aus Koethen ist, dass nicht zum Campus gehoert.
     * @return neue Listeneintraege
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
     * Laedt die Personen-Daten fuer den ExpandableListAdapter.
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
	 * Beschreibt die Standartfunktionen (Schliessen und Startseite) der ExpandableList Activity.
	 * @return Ist true, wenn eine Ausfuehrung erfolgreich war.
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
     * Listener fuer die Klicks auf die ExpandableList.
     * @return false wird hier nicht benoetigt.
     */
    private OnChildClickListener myListItemClicked =  new OnChildClickListener()
    {
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
        {
        	if(showBuildings){
        		Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                intent.putExtra("category", expandableListAdapterBuilding.getGroup(groupPosition).getID());
                intent.putExtra("building", expandableListAdapterBuilding.getChild(groupPosition, childPosition).getID());
                startActivity(intent);
        	}else{
        		//TODO Personen laden
        	}
            
            return true;
        }
    };
        


}
