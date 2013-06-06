package de.hsanhalt.inf.studiappkoethen.activities;

import java.util.ArrayList;
import java.util.List;

import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingManager;
import de.hsanhalt.inf.studiappkoethen.util.FilterItem;
import de.hsanhalt.inf.studiappkoethen.util.MergedMarkers;
import de.hsanhalt.inf.studiappkoethen.util.ExtendetMarker;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
	static final LatLng KOETHEN = new LatLng(51.750, 11.967);			// Standardkoordinaten für den Kamerastandpunkt 
	static final float startZoomLevel = 14.0f;							// Start-Zoomlevel
	private GoogleMap map;
	private float currentZoomLevel = startZoomLevel;					// momentanes Zoomlevel für den OnCameraCangeListener
	private float previousZoomLevel = 1.0f;								// vorheriges Zoomlevel für den OnCameraCangeListener
	private double averageLatitude = 0.0f;								// Variable zur Durchschnittsberechnung des Breitengrades
	private double averageLongitude = 0.0f;								// Variable zur Durchschnittsberechnung des Längengrades
	private int numberFilterItems = 0;									// Variable zur Durchschnittsberechnung des Breiten- und Längengrades
	private List<Byte> Categories = new ArrayList<Byte>();
	private List<FilterItem> specialFilter = new ArrayList<FilterItem>();	// angepasster Filter der anhand der übergebenen Daten gefüllt wird
	private List<ExtendetMarker> displayedMarkers = new ArrayList<ExtendetMarker>();
	private List<MergedMarkers> mergedMarkerList = new ArrayList<MergedMarkers>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_googlemaps);

		FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
        map = myMapFragment.getMap();
	    
	    map.setOnCameraChangeListener(getCameraChangeListener());
        
	    getCategories();	// Ermitteln der Kategorie-Anzahl
	    
	    setFilter();			// Bestimmung der Filtereinstellungen
	    
    	setMarkersDependingOnFilter();			// Setzen der Marker, die nicht ausgefiltert worden sind 
    	
    	setFocus();				// Bestimmung der Kameraposition und Fokus auf diese Position
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		// Erstellung des Filters in der Action-Bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.googlemaps, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)	// Aufruf des Kontextmenüs mit fen Filteroptionen
	{
		switch(item.getItemId()) {
			case R.id.action_filter:
				View view = this.findViewById(id.action_filter);
				this.registerForContextMenu(view);
				this.openContextMenu(view);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)	// Erstellung des Filter-Kontextmenüs
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		BuildingCategoryManager bcm = BuildingCategoryManager.getInstance();
		
		menu.setHeaderTitle("Filteroptionen");
		for(byte category : Categories) {				// Erstellt für jede vorhandene Kategorie eine Filter-Checkbox
			String str = "";
			for(BuildingCategory buildingCategory : bcm.getBuildingCategories()) {
				if(category == buildingCategory.getID()) {
					str = buildingCategory.getName();
				}
			}
			
			MenuItem item = menu.add(0, category, 0, str);
			item.setCheckable(true);
			
			
			for(FilterItem fItem : specialFilter) {		// Bestimmt, ob eine Checkbox true oder false ist
				if(fItem.getCategory() == category) {
					item.setChecked(true);
				}
			}
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)			// Reaktion auf Filteränderung 
	{
		boolean filterOn = false;
		for(int i = 0; i < specialFilter.size(); i++) {			// Wenn der Filter vorhanden ist, lösche ihn, ...
			if(specialFilter.get(i).getCategory() == item.getItemId()) {
				filterOn = true;
				specialFilter.remove(i);
			}
		}
		if(!filterOn) {
			specialFilter.add(new FilterItem((byte)item.getItemId())); // ... wenn nicht, erstelle einen.
		}
		
		item.setChecked(!item.isChecked());						// Aktivierung bzw. Deaktivierung der Checkbox
		
		clearAllMarkers();										// Rücksetzen und Neuzeichnen aller Marker (wird noch optimiert)
    	setMarkersDependingOnFilter();
		return super.onContextItemSelected(item);
	}

	private void getCategories() {							// Erstellt eine Liste aller verwendeten Kategorien

		BuildingCategoryManager bcm = BuildingCategoryManager.getInstance();
		
		for(BuildingCategory category : bcm.getBuildingCategories())
		{
			if(!Categories.contains(category.getID())) {
				Categories.add(category.getID());
			}
		}
		
	}
	
	public BitmapDescriptor getCategoryIcon(byte category) {
		
		BuildingCategoryManager bcm = BuildingCategoryManager.getInstance();
		String path = null;
		
		
		for(BuildingCategory buildingCategory : bcm.getBuildingCategories()) {
			if(category == buildingCategory.getID()) {
				if(buildingCategory.getIconPath() != null) {
					path = buildingCategory.getIconPath();
				}
			}
		}
		if(path == null) {
			switch(category){			// Bestimmung des Icons
				case 2:
					return BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_blue);
				case 3:
					return BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_red);
				default:
					return BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
			}
		} else {
			return BitmapDescriptorFactory.fromPath(path);
		}/**/
	}
	
	private boolean setFilter() {									// Auslesen und Verarbeiten der übergebenen Daten zur Filtereinstellung
		Bundle filterBundle = this.getIntent().getExtras();			// Abrufen der übergebenen Daten
		String sCategory = "category";
		String sBuilding = "building";
		String sNextCategory = "nextcategory";
		String sNextBuilding = "nextbuilding";
		
		if(filterBundle != null) {									// Wenn keine Daten übergeben wurden, überspringe das Auslesen der Daten
			while(filterBundle != null) {							// Das FilterBundle wird, am Ende der Schleife, gleich der nächsten Kategorie gesetzt.
																	// Existiert keine nächste Kategorie wird die Schleife beendet
				Bundle nextCategory = new Bundle();
				Bundle buildings = new Bundle();
				byte category;
				
				nextCategory = filterBundle.getBundle(sNextCategory);	// Auslesen des Bundles für die nächste Kategorie
				buildings = filterBundle.getBundle(sNextBuilding);		// Auslesen des Bundles für die Gebäude der momentanen Kategorie
				category = filterBundle.getByte(sCategory);				// Auslesen der momentanen Kategorie
				
				
				
				if(buildings == null) {
					specialFilter.add(new FilterItem(category));		// Werden keine Gebäude für diese Kategorie angegeben, wird im Filter die ganze Kategorie akzeptiert
				} else {
					FilterItem newFilter = new FilterItem(category);	// Ansonsten wird ein neues FilterItem erstellt in der die Kategorie und die Gebäude abgelegt werden
					while(buildings != null) {							// Hier wird das selbe Prinzip angewandt, wie mit der Kategorie-Schleife (siehe oben)
						byte newBuilding = buildings.getByte(sBuilding);
						Bundle nextBuilding = new Bundle();
						nextBuilding = buildings.getBundle(sNextBuilding);
						
						newFilter.addBuilding(newBuilding);
						buildings = nextBuilding;
					}
					specialFilter.add(newFilter);					// Danach wird das FilterItem zum Filter hinzugefügt
				}
				filterBundle = nextCategory;						// Jeder weitere Schleifendurchlauf verarbeitet die jeweils nächste Kategorie, bis keine Kategorie mehr angegeben wurde
			}
			return true;
		} else {													// Für den Fall, das keine Daten übergeben werden, wird der Filter so eingestellt, dass alle Kategorien angezeigt werden
			for(byte category : Categories) {
				specialFilter.add(new FilterItem(category));
			}
			return false;
		}
	}
	
	private boolean filterContainsBuilding(byte category, byte building) {			// Abfrage, ob ein Gebäude durch den Filter akzeptiert werden kann
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
	
	private void setFocus() {														// Bestimmen der Kameraposition und Setzen des Fokus auf diese Position
		if(numberFilterItems > 0) {													// Wurde wenigstens ein Gebäude auf der Karte angezeigt ...
			double finalLatitude = averageLatitude / numberFilterItems;				// ... wird der Durchschnitt der Breiten- und Längengrade ermittelt, ...
			double finalLongitude = averageLongitude / numberFilterItems;
			LatLng averagePosition = new LatLng(finalLatitude, finalLongitude);		// ... daraus eine, für GoogleMaps verständliche, Variable zur Positionsbestimmung gemacht...
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(averagePosition, startZoomLevel));		// ... und die Kameraposition an diese Stelle gesetzt.
			if(numberFilterItems == 1) {
				displayedMarkers.get(0).getMarker().showInfoWindow();				// Wurde nur ein Gebäude auf der Karte angezeigt, wird dessen Infofenster angezeigt.
			}
		} else {
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KOETHEN, startZoomLevel));		// Ansonsten wird die Kamera über die Standardposition geschwenkt.
		}
	    map.animateCamera(CameraUpdateFactory.zoomTo(startZoomLevel), 2000, null);	// Zum schluss wird der Zoomlevel auf den Standardwert gesetzt.
	}
	
	public void createNewMarker(Building building) {				// Erstellung eines Gebäude-Markers
		LatLng newBuilding = new LatLng(building.getExactLatitude(), building.getExactLongitude());		// Bestimmung der exakten Breiten- und Längengrade
		String title = building.getName();								// Bestimmung des Gebäudenamens
		byte index = building.getBuildingCategory().getID();			// Bestimmung der Gebäude-ID
				
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
		
		map.setOnInfoWindowClickListener(			// Setzen der OnClickListener für das Infofenster des erstellten Markers
				new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick(Marker clickedMarker) {
						startDetailedView(clickedMarker.getId());		// Starten der Detail-Ansicht des Gebäudes
					}
				}
			);
	    
		displayedMarkers.add(new ExtendetMarker(newBuildingMarker, building.getBuildingCategory().getID(), building.getID()));		// Speichern aller angezeigten Markers, so das sie von anderen Funktionen leichter abgefragt werden können.
		
	}
	
	public void setMarkersDependingOnFilter(){		//Setzen aller Marker
		BuildingManager buildingManager = BuildingManager.getInstance();		// Holen der Gebäude-Daten aus der XML
		List<Building> buildings = buildingManager.getBuildingList();
		
		for(Building building : buildings)										// Für jedes Gebäude wird ...
		{
			byte categoryId = building.getBuildingCategory().getID();			// ... die Kategorie und Gebäude-ID ermittelt ...
			byte buildingId = building.getID();
			if((building.getLatitude() != null) && (building.getLongitude() != null) && filterContainsBuilding(categoryId, buildingId))
			{																	// ... um damit den Filter abzufragen. Die Breiten- und Längengrade müssen ebenfalls vorhanden sein.
				createNewMarker(building);											// Ein neuer Marker wird erstellt ...
				
				averageLatitude = averageLatitude + building.getExactLatitude();	// und die Daten zur Durchschnittsberechnung für die Kameraposition bearbeitet.
				averageLongitude = averageLongitude + building.getExactLongitude();
				numberFilterItems ++;
				
			}
		}
	}
	
	private void startDetailedView(String markerId) {			// Starten der Detail-Ansicht
		byte currentCategId = 0;
		byte currentBuildId = 0;
		int i = 0;
		boolean matchedMarkerFound = false;
		while((!displayedMarkers.isEmpty()) && (i < displayedMarkers.size()) && (!matchedMarkerFound)) {		// Suche nach den, zum Marker gehörigen Daten.
			if(displayedMarkers.get(i).getMarker().getId().equals(markerId)) {
				currentCategId = displayedMarkers.get(i).getCategId();											// Bestimmung der Kategorie und der Gebäude-Id des Markers
				currentBuildId = displayedMarkers.get(i).getBuildId();
				matchedMarkerFound = true;
			}
			i++;
		}
		if(matchedMarkerFound) {
			Intent intent = new Intent(this, DetailActivity.class);				// Erstellung eines Intents für die Detail-Ansicht.
	        intent.putExtra("category", currentCategId);						// Übergabe der Kategorie und Gebaäude-ID.
	        intent.putExtra("building", currentBuildId);
	        this.startActivity(intent);											// Aufruf der Detail-Ansicht.
		}
	}
	
	public void clearMarker(int i) {				// Löschen eines spezifischen Markers
		if(displayedMarkers.size() > i) {
			Marker delMkr = displayedMarkers.get(i).getMarker();
			displayedMarkers.remove(i);
			delMkr.remove();
		}
	}
	
	public void clearAllMarkers() {				// Löschen aller dargestellten Marker
		while(displayedMarkers.isEmpty() == false) {
			Marker first = displayedMarkers.get(0).getMarker();
			displayedMarkers.remove(0);
			first.remove();
		}
	}
	
	public int getZoomTriggerDistance() {
		// ...still in Progress
		float tmp = (16.4f - currentZoomLevel) * 500;
		if(tmp > 0) {
			return (int) tmp;
		}else {
			return 0;
		}
	}
	
	public void mergeCloseMarkers() {
		// ...still in Progress
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
		// ...still in Progress
		int triggerDistance = getZoomTriggerDistance();
		// TODO: Funktion zum Splitten gemergeter Marker
	}
	
	public OnCameraChangeListener getCameraChangeListener() {
		// ...still in Progress
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