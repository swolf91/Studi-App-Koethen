package de.hsanhalt.inf.studiappkoethen.activities;

import java.util.ArrayList;
import java.util.List;

import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingManager;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;

public class GoogleMapsActivity extends Activity
{

	static final LatLng KOETHEN = new LatLng(51.750, 11.967);
	static final float startZoomLevel = 14.0f;
	private GoogleMap map;
	private float currentZoomLevel = startZoomLevel;
	private float previousZoomLevel = 1.0f;
	private List<Marker> displayedMarkers = new ArrayList<Marker>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_googlemaps);
		

		FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment 
         = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
        map = myMapFragment.getMap();
        map.setMyLocationEnabled(true);        
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(KOETHEN, startZoomLevel));
	    map.animateCamera(CameraUpdateFactory.zoomTo(startZoomLevel), 2000, null);
	    map.setOnCameraChangeListener(getCameraChangeListener());
	    
	    setMarkersOnCloseZoomLevel();
		
	}

	public void setMarkersOnCloseZoomLevel(){		//Setzen aller Marker
		BuildingManager buildingManager = BuildingManager.getInstance();
		List<Building> buildings = buildingManager.getBuildingList();
		
		for(Building building : buildings)
		{
			if((building.getLatitude() != null) && (building.getLongitude() != null) && (building.getName() != null))
			{
				LatLng newBuilding = new LatLng(building.getExactLatitude(), building.getExactLongitude());
				String title = building.getName();
				BitmapDescriptor buildingIcon;
				int index = building.getBuildingCategory().getID();
								
				switch(index){			// Bestimmung des Icons
					case 2:
						buildingIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_blue);
						break;
					case 3:
						buildingIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_red);
						break;
					default:
						buildingIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
						break;
				}
				
				Marker newBuildingMarker;
				
				
				StringBuilder sbld = new StringBuilder();
				String str = "";
				sbld.append("Latitude: " + building.getExactLatitude() + ", Longitude: " + building.getExactLongitude());
				str = sbld.toString();
				
				
				if(building.getDescription() == null) {		// Marker-Erstellung ohne Beschreibung
					newBuildingMarker = map.addMarker(new MarkerOptions()
							.position(newBuilding)
							.title(title)
							.icon(buildingIcon));
				} else {									// Marker-Erstellung mit Beschreibung
					newBuildingMarker = map.addMarker(new MarkerOptions()
							.position(newBuilding)
							.title(title)
							.icon(buildingIcon)
							//.snippet(str));
							.snippet(building.getDescription()));
				}
				
				displayedMarkers.add(newBuildingMarker);
				
			}
		}
	}
	
	public void clearAllMarkers() {
		while(displayedMarkers.isEmpty() == false) {
			Marker first = displayedMarkers.get(0);
			displayedMarkers.remove(0);
			first.remove();
		}
		
		//Toast.makeText(getApplicationContext(), "All Markers cleared!", Toast.LENGTH_LONG).show();
	}
	
	public int getZoomTriggerdistance() {
		float tmp = (16.4f - currentZoomLevel) * 500;
		if(tmp > 0) {
			return (int) tmp;
		}else {
			return 0;
		}
	}
	
	public void mergeCloseMarkers() {
		int triggerDistance = getZoomTriggerdistance();
		// TODO: Funktion zum Mergen der Marker
	}
	
	public void splitMergedMarkers() {
		int triggerDistance = getZoomTriggerdistance();
		// TODO: Funktion zum Splitten gemergeter Marker
	}
	
	public OnCameraChangeListener getCameraChangeListener() {
		return new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition position) {
				if(previousZoomLevel != position.zoom){
				
					currentZoomLevel = position.zoom;
					StringBuilder sbld = new StringBuilder();
					String str = "";
					
					sbld.append(currentZoomLevel);
					str = sbld.toString();
					
					if(currentZoomLevel <= 11.0f) {
						//clearAllMarkers();
					}
										
					if(previousZoomLevel < currentZoomLevel) {
						mergeCloseMarkers();		// Kombinieren von nahen Markern
					} else {
						splitMergedMarkers();		// Teilen von kombinierten Markern
					}
					
					//Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
				
					previousZoomLevel = currentZoomLevel;
				}
			}
		};
	}
	
}