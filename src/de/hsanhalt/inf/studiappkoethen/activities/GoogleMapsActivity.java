package de.hsanhalt.inf.studiappkoethen.activities;

import de.hsanhalt.inf.studiappkoethen.R;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class GoogleMapsActivity extends Activity
{

    static final LatLng KOETHEN = new LatLng(51.750, 11.967);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_googlemaps);

        FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
        map = myMapFragment.getMap();
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KOETHEN, 14));
        map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }
}
