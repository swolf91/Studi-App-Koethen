package de.hsanhalt.inf.studiappkoethen.activities;

import java.util.ArrayList;
import java.util.List;

import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingManager;
import de.hsanhalt.inf.studiappkoethen.util.FilterItem;
import de.hsanhalt.inf.studiappkoethen.util.MergedMarkers;
import de.hsanhalt.inf.studiappkoethen.util.ExtendetMarker;
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
	static final int numberCategories = 6;
	private GoogleMap map;
	private float currentZoomLevel = startZoomLevel;
	private float previousZoomLevel = 1.0f;
	private double averageLatitude = 0.0f;
	private double averageLongitude = 0.0f;
	private int numberFilterItems = 0;
	private List<ExtendetMarker> displayedMarkers = new ArrayList<ExtendetMarker>();
	private List<MergedMarkers> mergedMarkerList = new ArrayList<MergedMarkers>();
	private List<FilterItem> specialFilter = new ArrayList<FilterItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_googlemaps);

		FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
        map = myMapFragment.getMap();
	    
	    map.setOnCameraChangeListener(getCameraChangeListener());
        
	    setFilter();
	    
    	setMarkersDependingOnFilter();
    	
    	setFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.googlemaps_filter, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		byte index = -1;
		boolean filterOn = false;
		switch(item.getItemId()) {
			case R.id.filteritem01:
				index = 0;
				break;
			case R.id.filteritem02:
				index = 1; 
				break;
			case R.id.filteritem03:
				index = 2; 
				break;
			case R.id.filteritem04:
				index = 3; 
				break;
			case R.id.filteritem05:
				index = 4; 
				break;
			default:
				break;
		}
		
		if(index >= 0) {
			for(int i = 0; i < specialFilter.size(); i++) {
				if(specialFilter.get(i).getCategory() == index) {
					filterOn = true;
					specialFilter.remove(i);
				}
			}
			if(!filterOn) {
				specialFilter.add(new FilterItem(index));
			}
			
			clearAllMarkers();
	    	setMarkersDependingOnFilter();
		}
		
		return super.onOptionsItemSelected(item);
	}

	public BitmapDescriptor getCategoryIcon(byte category) {
		switch(category){			// Bestimmung des Icons
			case 2:
				return BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_blue);
			case 3:
				return BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_red);
			default:
				return BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
		}
	}
	
	private boolean setFilter() {
		Bundle filterBundle = this.getIntent().getExtras();
		String sCategory = "category";
		String sBuilding = "building";
		String sNextCategory = "nextcategory";
		String sNextBuilding = "nextbuilding";
		
		if(filterBundle != null) {
			while(filterBundle != null) {
				Bundle nextCategory = new Bundle();
				Bundle buildings = new Bundle();
				byte category;
				
				nextCategory = filterBundle.getBundle(sNextCategory);
				buildings = filterBundle.getBundle(sNextBuilding);
				category = filterBundle.getByte(sCategory);
				if(buildings == null) {
					specialFilter.add(new FilterItem(category));
				} else {
					FilterItem newFilter = new FilterItem(category);
					
					while(buildings != null) {
						byte newBuilding = buildings.getByte(sBuilding);
						Bundle nextBuilding = new Bundle();
						nextBuilding = buildings.getBundle(sNextBuilding);
						
						newFilter.addBuilding(newBuilding);
						buildings = nextBuilding;
					}
					specialFilter.add(newFilter);
				}
				filterBundle = nextCategory;
			}
			return true;
		} else {
			for(byte i = 0; i < numberCategories; i++) {
				specialFilter.add(new FilterItem(i));
			}
			return false;
		}
	}
	
	private boolean filterContainsBuilding(byte category, byte building) {
		boolean buildingFound = false;
		int i = 0;
		while(i < specialFilter.size() && !buildingFound) {
			if((specialFilter.get(i).getCategory() == category) && (specialFilter.get(i).getBuildings().contains(building) || (specialFilter.get(i).getBuildings().isEmpty()))) {
				buildingFound = true;
			}
			i++;
		}
		return buildingFound;
	}
	
	private void setFocus() {
		if(numberFilterItems > 0) {
			double finalLatitude = averageLatitude / numberFilterItems;
			double finalLongitude = averageLongitude / numberFilterItems;
			LatLng averagePosition = new LatLng(finalLatitude, finalLongitude);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(averagePosition, startZoomLevel));
			if(numberFilterItems == 1) {
				displayedMarkers.get(0).getMarker().showInfoWindow();
			}
		} else {
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KOETHEN, startZoomLevel));
		}
	    map.animateCamera(CameraUpdateFactory.zoomTo(startZoomLevel), 2000, null);
	}
	
	
	public void createNewMarker(Building building) {
		LatLng newBuilding = new LatLng(building.getExactLatitude(), building.getExactLongitude());
		String title = building.getName();
		byte index = building.getBuildingCategory().getID();
				
		Marker newBuildingMarker;
		
		if(building.getDescription() == null) {		// Marker-Erstellung ohne Beschreibung
			newBuildingMarker = map.addMarker(new MarkerOptions()
					.position(newBuilding)
					.title(title)
					.icon(getCategoryIcon(index)));
		} else {									// Marker-Erstellung mit Beschreibung
			newBuildingMarker = map.addMarker(new MarkerOptions()
					.position(newBuilding)
					.title(title)
					.icon(getCategoryIcon(index))
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
	
	public void setMarkersDependingOnFilter(){		//Setzen aller Marker
		BuildingManager buildingManager = BuildingManager.getInstance();
		List<Building> buildings = buildingManager.getBuildingList();
		
		for(Building building : buildings)
		{
			byte categoryId = building.getBuildingCategory().getID();
			byte buildingId = building.getID();
			if((building.getLatitude() != null) && (building.getLongitude() != null) && filterContainsBuilding(categoryId, buildingId))
			{
				createNewMarker(building);
				
				averageLatitude = averageLatitude + building.getExactLatitude();
				averageLongitude = averageLongitude + building.getExactLongitude();
				numberFilterItems ++;
				
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