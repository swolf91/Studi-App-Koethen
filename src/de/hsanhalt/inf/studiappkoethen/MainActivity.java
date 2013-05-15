package de.hsanhalt.inf.studiappkoethen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import de.hsanhalt.inf.studiappkoethen.activities.GoogleMapsActivity;
import de.hsanhalt.inf.studiappkoethen.util.xml.parsing.XmlParser;



public class MainActivity extends Activity
{
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!XmlParser.isCreated())
        {
            XmlParser xmlParser = XmlParser.getInstance();
            xmlParser.setAssets(this.getAssets());
            xmlParser.install();
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onButtonClick(View view) {
    	switch(view.getId()) {
    		case R.id.btn_googlemaps:
    			startActivity(new Intent(this, GoogleMapsActivity.class));
    			break;
    	}
    }
}
