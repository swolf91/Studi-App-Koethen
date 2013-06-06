package de.hsanhalt.inf.studiappkoethen.activities;

import android.content.Intent;
//import android.support.v4.widget.SearchViewCompatIcs.MySearchView;
import android.view.MenuItem;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.ExpandableListViewAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class ExpandableListActivity extends android.app.ExpandableListActivity
{
	
	private ExpandableListView expandableListView= (ExpandableListView) findViewById(android.R.id.list);;
	private ExpandableListAdapter expandableListViewAdapter;
	/**
	 * diese Methode baut de ExpandableList auf und zeigt sie an
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandablelist);
		
		//Spinner ist zur "Vorschau" der Gruppenelemente
//		Spinner spinner = (Spinner) findViewById(R.id.expandableListView1);
		
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R. , android.R.layout.simple_spinner_dropdown_item); TODO "Spinner" - Layout einfuergen
		
		//Layout das genutzt werden soll festlegen, wenn die Auswahlliste angezeigt wird
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//den Adapter zum Spinner hinzufuegen
//		spinner.setAdapter(adapter);
		
		
		//weitere Daten laden also die ganzen Parent und Child Dinger
		
		//referenz auf unsere Exp Liste holen
		
		
		expandableListViewAdapter = new ExpandableListViewAdapter(ExpandableListActivity.this);
		
		expandableListView.setAdapter(expandableListViewAdapter);
		
//		expandAll();
		
		
//		Button add = (Button) findViewById(R.id.) // hier muss ein Button hin
				
			
		//listener fuer die Child Liste
//		expandableListView.setOnChildClickListener()		
		
		
		
		
		
	}
	/**
	 * Alle Gruppen werden auf OnClick geoeffnet.
	 */
	private void expandAll()
	{
		int count = expandableListViewAdapter.getGroupCount();
		for(int i=0;i<count;i++){
			expandableListView.expandGroup(i);
		}
		
	}
	/**
	 * Ereignis das bei "OnClick" auf das Untermenue der ExpandableList ausgeloest wird.
	 */
	private OnChildClickListener expandableListItemClicked = new OnChildClickListener () {
		public boolean onChildClick(ExpandableListView parent, View view,int groupPosition,int childPosition,long id){
			
			//TODO hier kommt der Aufruf zur Detail Activity des jeweiligen Gebaeudes hin
			return false;
		}
	};
	/**
	 * Ereignis das bei "OnOlick" auf die Kategorieelemente der ExpandableList ausgeloest wird.
	 */
	private OnGroupClickListener expandableListGroupClicked = new OnGroupClickListener () {
		
		public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
			expandableListView.expandGroup(groupPosition);
			
			return true; //TODO ueberpruefen ob true ok ist.
		}
	};
	
	
	
	

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
    
    public void onClick(View v){
    	
    	switch(v.getId()){
    		
    	}
    	
    }



}
