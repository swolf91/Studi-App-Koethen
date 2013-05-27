package de.hsanhalt.inf.studiappkoethen.activities;

import java.util.ArrayList;
import java.util.List;

import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingManager;
import de.hsanhalt.inf.studiappkoethen.activities.classes.MergedMarkers;
import de.hsanhalt.inf.studiappkoethen.activities.classes.ExtendetMarker;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;

public class GoogleMapsActivity extends Activity
{
	static final LatLng KOETHEN = new LatLng(51.750, 11.967);
	static final float startZoomLevel = 14.0f;
	private GoogleMap map;
	private float currentZoomLevel = startZoomLevel;
	private float previousZoomLevel = 1.0f;
	private List<ExtendetMarker> displayedMarkers = new ArrayList<ExtendetMarker>();
	private List<MergedMarkers> mergedMarkerList = new ArrayList<MergedMarkers>();
	private boolean filterOptions[] = new boolean[5];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_googlemaps);
		
		for(int i = 0; i < filterOptions.length; i++) {
			filterOptions[i] = true;
		}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.googlemaps_filter, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.filteritem01:
				filterOptions[0] = !filterOptions[0];
				clearAllMarkers();
				setMarkersOnCloseZoomLevel();
				return true;
			case R.id.filteritem02:
				filterOptions[1] = !filterOptions[1];
				clearAllMarkers();
				setMarkersOnCloseZoomLevel();
				return true;
			case R.id.filteritem03:
				filterOptions[2] = !filterOptions[2];
				clearAllMarkers();
				setMarkersOnCloseZoomLevel();
				return true;
			case R.id.filteritem04:
				filterOptions[3] = !filterOptions[3];
				clearAllMarkers();
				setMarkersOnCloseZoomLevel();
				return true;
			case R.id.filteritem05:
				filterOptions[4] = !filterOptions[4];
				clearAllMarkers();
				setMarkersOnCloseZoomLevel();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public void setMarkersOnCloseZoomLevel(){		//Setzen aller Marker
		BuildingManager buildingManager = BuildingManager.getInstance();
		List<Building> buildings = buildingManager.getBuildingList();
		
		for(Building building : buildings)
		{
			int category = building.getBuildingCategory().getID();
			if(category > 4 || category < 0) {
				category = 0;
			}
			if((building.getLatitude() != null) && (building.getLongitude() != null) && (filterOptions[category]))
			{
				LatLng newBuilding = new LatLng(building.getExactLatitude(), building.getExactLongitude());
				String title = building.getName();
				BitmapDescriptor buildingIcon;
				byte index = building.getBuildingCategory().getID();
								
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
				
				/*
				StringBuilder sbld = new StringBuilder();
				String str = "";
				//sbld.append("Latitude: " + building.getExactLatitude() + ", Longitude: " + building.getExactLongitude());
				sbld.append("ID: " + building.getID());
				str = sbld.toString();/**/
				
				
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
				
				map.setOnInfoWindowClickListener(
						new OnInfoWindowClickListener() {
							@Override
							public void onInfoWindowClick(Marker clickedMarker) {
								startDetailedView(clickedMarker.getId());
							}
						}
					);
			    
				displayedMarkers.add(new ExtendetMarker(newBuildingMarker, building.getBuildingCategory().getID(), building.getID()));
				
			}
		}
	}
	
	private void startDetailedView(String markerId) {
		byte currentCategId = 0;
		byte currentBuildId = 0;
		int i = 0;
		boolean matchedMarkerFound = false;
		while((!displayedMarkers.isEmpty()) && (i < displayedMarkers.size()) && (!matchedMarkerFound)) {
			if(displayedMarkers.get(i).getMarker().getId().equals(markerId)) {
				currentCategId = displayedMarkers.get(i).getCategId();
				currentBuildId = displayedMarkers.get(i).getBuildId();
				matchedMarkerFound = true;
			}
			i++;
		}
		if(matchedMarkerFound) {
			Intent intent = new Intent(this, DetailActivity.class);
	        intent.putExtra("category", currentCategId);
	        intent.putExtra("building", currentBuildId);
	        this.startActivity(intent);/**/
		}
	}
	
	public void clearMarker(int i) {
		if(displayedMarkers.size() > i) {
			Marker delMkr = displayedMarkers.get(i).getMarker();
			displayedMarkers.remove(i);
			delMkr.remove();
		}
	}
	
	public void clearAllMarkers() {
		while(displayedMarkers.isEmpty() == false) {
			Marker first = displayedMarkers.get(0).getMarker();
			displayedMarkers.remove(0);
			first.remove();
		}
	}
	
	public int getZoomTriggerDistance() {
		float tmp = (16.4f - currentZoomLevel) * 500;
		if(tmp > 0) {
			return (int) tmp;
		}else {
			return 0;
		}
	}
	
	public void mergeCloseMarkers() {
		int triggerDistance = getZoomTriggerDistance();
		//List<Marker> mergedMarkers = new ArrayList<Marker>();
		
		boolean stillMoreMergableMarkers = true;
		
		while(stillMoreMergableMarkers) {
			int i = 0;
			int j = 1;
			boolean mergableMarkersFound = false;
			if(displayedMarkers.size() > 1) {
				while((i < displayedMarkers.size() - 1) && (!mergableMarkersFound)) {
					while((j < displayedMarkers.size()) && (!mergableMarkersFound)) {
						double dLat = (displayedMarkers.get(i).getMarker().getPosition().latitude - displayedMarkers.get(j).getMarker().getPosition().latitude) * 10000000;
						double dLon = (displayedMarkers.get(i).getMarker().getPosition().longitude - displayedMarkers.get(j).getMarker().getPosition().longitude) * 10000000;
						double dTotal = Math.sqrt(dLat * dLat + dLon * dLon);
						
						if((dTotal < triggerDistance) && (dTotal > 0.0)) {
							mergableMarkersFound = true;
							
							
							Log.w("MARKERS FOUND", displayedMarkers.get(i).getMarker().getTitle() + " <-> " + displayedMarkers.get(j).getMarker().getTitle() + " = " + dTotal);
						}
						j++;
					}
					i++;
				}
			} else {
				stillMoreMergableMarkers = false;
			}
			if(mergableMarkersFound) {
				Marker m1 = displayedMarkers.get(i - 1).getMarker();
				Marker m2 = displayedMarkers.get(j - 1).getMarker();
				MergedMarkers newMerge;// = new MergedMarkers(m1, m2);// = new MergedMarkers(displayedMarkers.get(i - 1), displayedMarkers.get(j - 1));
				
				//mergedMarkerList.add(newMerge);	
				
				clearMarker(i - 1);	
				clearMarker(j - 1);
			} else {
				stillMoreMergableMarkers = false;
			}
		}
		/*
		for(int i = 0; i < mergedMarkerList.size(); i++) {
			Marker newBuildingMarker = map.addMarker(mergedMarkerList.get(i).getMarkerOptions());
			displayedMarkers.add(newBuildingMarker);
		}/**/
	}
	
	public void splitMergedMarkers() {
		int triggerDistance = getZoomTriggerDistance();
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