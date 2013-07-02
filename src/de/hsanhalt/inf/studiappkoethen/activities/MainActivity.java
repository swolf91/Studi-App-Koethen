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
import de.hsanhalt.inf.studiappkoethen.R.drawable;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.R.string;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingManager;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.DefaultXmlParser;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonManager;
import de.hsanhalt.inf.studiappkoethen.xml.quiz.QuizManager;


public class MainActivity extends Activity
{
    SharedPreferences mPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.mPreferences = getSharedPreferences("firstStart", MODE_PRIVATE);

        if (!DefaultXmlParser.isCreated())
        {
            DefaultXmlParser defaultXmlParser = DefaultXmlParser.getInstance();
            defaultXmlParser.setAssets(this.getAssets());
            defaultXmlParser.registerXmlParser(PersonManager.getInstance());
            defaultXmlParser.registerXmlParser(BuildingManager.getInstance());
            defaultXmlParser.registerXmlParser(BuildingCategoryManager.getInstance());
            defaultXmlParser.registerXmlParser(PersonCategoryManager.getInstance());
            defaultXmlParser.registerXmlParser(QuizManager.getInstance());
            defaultXmlParser.install();
        }
    }

    @Override  
    public void onResume() 
	{  
	   super.onResume();
	   
	   if (this.mPreferences.getBoolean("firstStart", true))
	   {
		    this.mPreferences.edit().putBoolean("firstStart", false).commit();
	    
			Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(drawable.app_launcher);
			builder.setTitle(this.getResources().getString(string.main_dialog_headline));
			builder.setMessage(this.getResources().getString(string.main_dialog_message));
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
        	case id.main_imageview_campus:
        		Intent expandableListIntentCampus = new Intent (this,ExpandableListActivity.class);
        		expandableListIntentCampus.putExtra("isCampus", true);
        		this.startActivity(expandableListIntentCampus);
        		break;

            case id.main_imageview_map:
            	
            	Intent intentGoogle = new Intent(this, GoogleMapsActivity.class);
            	/*
            	// * Beispiel fuer Initialisierung des Filters fuer GoogleMaps
            	
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
            	Intent expandableListIntentCity= new Intent (this,ExpandableListActivity.class);
            	expandableListIntentCity.putExtra("isCampus", false);
        		this.startActivity(expandableListIntentCity);
                break;

            case id.main_imageview_tour:
                Intent intentQuiz = new Intent(this, QuizActivity.class);
                this.startActivity(intentQuiz);
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
