package de.hsanhalt.inf.studiappkoethen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.util.FilterBundle;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.XmlParser;


public class MainActivity extends Activity
{
    SharedPreferences mPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        mPreferences = getSharedPreferences("firstStart", MODE_PRIVATE);

        if (!XmlParser.isCreated())
        {
            XmlParser xmlParser = XmlParser.getInstance();
            xmlParser.setAssets(this.getAssets());
            xmlParser.install();
        }
    }

    @Override  
    public void onResume() 
	{  
	   super.onResume();
	   
	   if (mPreferences.getBoolean("firstStart", true)) 
	   {
		    mPreferences.edit().putBoolean("firstStart", false).commit();
	    
			Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("Willkommen");
			builder.setMessage("Hi Ersties,\nhoffe ihr könnt unsere App gebrauchen und viel Spass damit.");
			builder.setCancelable(true);
			builder.setPositiveButton(android.R.string.ok,null);
	        
			AlertDialog dialog = builder.create();
			dialog.show();
	   }
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onButtonClick(View view)
    {
        switch (view.getId())
        {
            case id.main_imageview_map:
            	
            	Intent intentGoogle = new Intent(this, GoogleMapsActivity.class);
            	/*
            	// * Beispiel f�r Initialisierung des Filters f�r GoogleMaps
            	
            	FilterBundle newFilter = new FilterBundle((byte) 2);
            	newFilter.addNewBuilding((byte) 1);
            	newFilter.addNewBuilding((byte) 2);
            	newFilter.addNewBuilding((byte) 3);
            	newFilter.addNewBuilding((byte) 4);
            	newFilter.addNewCategory((byte) 2);
            	newFilter.addNewCategory((byte) 3);
            	newFilter.addNewBuilding((byte) 1);
            	
            	intentGoogle.putExtras(newFilter.getBundle());
            	*/
            	
                this.startActivity(intentGoogle);
                //startActivity(new Intent(this, GoogleMapsActivity.class));
                break;
            case id.main_imageview_city:
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("category", (byte) 5);
                intent.putExtra("building", (byte) 5);

                this.startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.action_close:
            moveTaskToBack(true);
            return true;

        case R.id.action_support:
            startActivity(new Intent(this, SupportActivity.class));
            return true;

        default:
            return super.onOptionsItemSelected(item);

        }
    }
}
