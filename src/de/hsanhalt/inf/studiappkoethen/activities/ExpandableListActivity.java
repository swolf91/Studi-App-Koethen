package de.hsanhalt.inf.studiappkoethen.activities;

import android.content.Intent;
import android.view.MenuItem;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.ExpandableListViewAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

public class ExpandableListActivity extends android.app.ExpandableListActivity
{
	
	ExpandableListView elv;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandablelist);
		
		elv=(ExpandableListView)findViewById(R.id.expandableListView1);
		//elv.setAdapter(new ExpandableListView(this)); //TODO
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

//        case R.id.action_list:
//            startActivity(new Intent(this, ExpandableListActivity.class));
//            return true;


        default:
            return super.onOptionsItemSelected(item);
        }

    }



}
