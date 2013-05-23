package de.hsanhalt.inf.studiappkoethen.activities;

import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.layout;
import de.hsanhalt.inf.studiappkoethen.R.menu;
import de.hsanhalt.inf.studiappkoethen.util.ElvAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.view.Menu;
import android.widget.ExpandableListView;

public class ExpListActivity extends ExpandableListActivity
{
	
	ExpandableListView elv;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explist);
		
		elv=(ExpandableListView)findViewById(R.id.expandableListView1);
		elv.setAdapter(new ElvAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expandable_list, menu);
		return true;
	}

}
