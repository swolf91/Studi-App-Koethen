package de.hsanhalt.inf.studiappkoethen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.xml.parsing.XmlParser;


public class MainActivity extends Activity
{
    SharedPreferences mPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        case R.id.btn_googlemaps:
            startActivity(new Intent(this, GoogleMapsActivity.class));
            break;
        }
    }
}
