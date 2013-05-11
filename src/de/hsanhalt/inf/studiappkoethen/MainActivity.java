package de.hsanhalt.inf.studiappkoethen;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import de.hsanhalt.inf.studiappkoethen.util.xml.parsing.XmlParser;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.app.FragmentManager;

public class MainActivity extends Activity
{
	static final LatLng KOETHEN = new LatLng(51.750, 11.967);
	private GoogleMap map;
	
	
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
        

		FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment 
         = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
        map = myMapFragment.getMap();
        map.setMyLocationEnabled(true);        
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(KOETHEN, 14));
	    map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
