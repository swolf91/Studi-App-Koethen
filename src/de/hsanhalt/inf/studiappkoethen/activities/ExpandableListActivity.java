package de.hsanhalt.inf.studiappkoethen.activities;

import java.util.ArrayList;
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

public class ExpandableListActivity extends Activity
{
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_expandablelist);

        //Expandable list aus dem Layout laden
        this.expandableListView = (ExpandableListView) findViewById(R.id.expandablelist_expandablelist_list);

        //Adapter erstellen
        this.expandableListAdapter = new ExpandableListAdapter(this, this.loadData());
        //Adapter an ExpandableListView anhaengen
        this.expandableListView.setAdapter(this.expandableListAdapter);

        //listener for child row click
        this.expandableListView.setOnChildClickListener(myListItemClicked);
    }

    /**
     * laedt die Daten fuer den ExpandableListAdapter
     */
    private List<ExpandableListEntry> loadData()
    {
        List<ExpandableListEntry> entryList = new ArrayList<ExpandableListEntry>();
        BuildingCategoryManager buildingCategoryManager = BuildingCategoryManager.getInstance();
        BuildingManager buildingManager = BuildingManager.getInstance();
        for(BuildingCategory buildingCategory : buildingCategoryManager.getBuildingCategories())
        {
            List<Building> buildings = buildingManager.getBuildingList(buildingCategory);
            entryList.add(new ExpandableListEntry(buildingCategory, buildings.toArray(new Building[buildings.size()])));
        }
        return entryList;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expandable_list, menu);
		return true;
	}

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

    //our child listener
    private OnChildClickListener myListItemClicked =  new OnChildClickListener()
    {
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
        {
            Intent intent = new Intent(getBaseContext(), DetailActivity.class);
            intent.putExtra("category", ((BuildingCategory)expandableListAdapter.getGroup(groupPosition)).getID());
            intent.putExtra("building", ((Building)expandableListAdapter.getChild(groupPosition, childPosition)).getID());
            startActivity(intent);
            return true;
        }
    };


}
