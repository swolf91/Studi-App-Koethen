package de.hsanhalt.inf.studiappkoethen.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.util.AndroidUtils;
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
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
	static final LatLng KOETHEN = new LatLng(51.750, 11.967);			// Standardkoordinaten fuer den Kamerastandpunkt
	static final float startZoomLevel = 14.0f;							// Start-Zoomlevel
	private GoogleMap map;
	private float currentZoomLevel = startZoomLevel;					// momentanes Zoomlevel fuer den OnCameraCangeListener
	private float previousZoomLevel = 1.0f;								// vorheriges Zoomlevel fuer den OnCameraCangeListener
	private double averageLatitude = 0.0f;								// Variable zur Durchschnittsberechnung des Breitengrades
	private double averageLongitude = 0.0f;								// Variable zur Durchschnittsberechnung des Laengengrades
	private int numberFilterItems = 0;									// Variable zur Durchschnittsberechnung des Breiten- und Laengengrades
	private List<Byte> Categories = new ArrayList<Byte>();
	private List<FilterItem> specialFilter = new ArrayList<FilterItem>();	// angepasster Filter der anhand der �ueergebenen Daten gef�uelt wird
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
        
	    map.setMyLocationEnabled(true);		// Aktivieren der Anzeige des eigenen Standortes
	    
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
	public boolean onOptionsItemSelected(MenuItem item)	// Aufruf des Kontextmenues mit den Filteroptionen
	{
		switch(item.getItemId()) {
			case R.id.action_filter:
				View view = this.findViewById(id.action_filter);
				this.registerForContextMenu(view);
				this.openContextMenu(view);
				return true;
			case R.id.action_focus:
				setFocus();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)	// Erstellung des Filter-Kontextmenues
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		BuildingCategoryManager bcm = BuildingCategoryManager.getInstance();
		
		menu.setHeaderTitle("Filteroptionen");
		for(byte category : Categories) {				// Erstellt fuer jede vorhandene Kategorie eine Filter-Checkbox
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
	public boolean onContextItemSelected(MenuItem item)			// Reaktion auf Filteraenderung
	{
		boolean filterOn = false;
		for(int i = 0; i < specialFilter.size(); i++) {			// Wenn der Filter vorhanden ist, loesche ihn, ...
			if(specialFilter.get(i).getCategory() == item.getItemId()) {
				filterOn = true;
				specialFilter.remove(i);
			}
		}
		if(!filterOn) {
			specialFilter.add(new FilterItem((byte)item.getItemId())); // ... wenn nicht, erstelle einen.
		}
		
		item.setChecked(!item.isChecked());						// Aktivierung bzw. Deaktivierung der Checkbox
		
		clearAllMarkers();										// Ruecksetzen und Neuzeichnen aller Marker (wird noch optimiert)
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

		BuildingCategoryManager buildingCategoryManager = BuildingCategoryManager.getInstance();
        BitmapDescriptor bitmapDescriptor = null;
		String path = buildingCategoryManager.getCategory(category).getIconPath();

		if(path != null)
        {
            try
            {
                Bitmap bitmap = AndroidUtils.getBitmapFromAsset(this.getAssets(), path);
                bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            }
            catch (IOException e)
            {
                bitmapDescriptor = null;
                Log.e("GoogleMapsActivity", "Can't load category icon " + category + "!");
            }
		}
        if(bitmapDescriptor == null)     // Festlegen eines Default-Bildchens
        {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
		}
        return bitmapDescriptor;
	}
	
	private boolean setFilter() {									// Auslesen und Verarbeiten der uebergebenen Daten zur Filtereinstellung
		Bundle filterBundle = this.getIntent().getExtras();			// Abrufen der uebergebenen Daten
		String sCategory = "category";
		String sBuilding = "building";
		String sNextCategory = "nextcategory";
		String sNextBuilding = "nextbuilding";
		
		if(filterBundle != null) {									// Wenn keine Daten uebergeben wurden, ueberspringe das Auslesen der Daten
			while(filterBundle != null) {							// Das FilterBundle wird, am Ende der Schleife, gleich der naechsten Kategorie gesetzt.
																	// Existiert keine naechste Kategorie wird die Schleife beendet
				Bundle nextCategory = new Bundle();
				Bundle buildings = new Bundle();
				byte category;
				
				nextCategory = filterBundle.getBundle(sNextCategory);	// Auslesen des Bundles fuer die naechste Kategorie
				buildings = filterBundle.getBundle(sNextBuilding);		// Auslesen des Bundles fuer die Gebaeude der momentanen Kategorie
				category = filterBundle.getByte(sCategory);				// Auslesen der momentanen Kategorie
				
				if(buildings == null) {
					specialFilter.add(new FilterItem(category));		// Werden keine Gebaeude fuer diese Kategorie angegeben, wird im Filter die ganze Kategorie akzeptiert
				} else {
					FilterItem newFilter = new FilterItem(category);	// Ansonsten wird ein neues FilterItem erstellt in der die Kategorie und die Gebaeude abgelegt werden
					while(buildings != null) {							// Hier wird das selbe Prinzip angewandt, wie mit der Kategorie-Schleife (siehe oben)
						byte newBuilding = buildings.getByte(sBuilding);
						Bundle nextBuilding = new Bundle();
						nextBuilding = buildings.getBundle(sNextBuilding);
						
						newFilter.addBuilding(newBuilding);
						buildings = nextBuilding;
					}
					specialFilter.add(newFilter);					// Danach wird das FilterItem zum Filter hinzugefuegt
				}
				filterBundle = nextCategory;						// Jeder weitere Schleifendurchlauf verarbeitet die jeweils naechste Kategorie, bis keine Kategorie mehr angegeben wurde
			}
			return true;
		} else {													// Fuer den Fall, das keine Daten uebergeben werden, wird der Filter so eingestellt, dass alle Kategorien angezeigt werden
			for(byte category : Categories) {
				specialFilter.add(new FilterItem(category));
			}
			return false;
		}
	}
	
	private boolean filterContainsBuilding(byte category, byte building) {			// Abfrage, ob ein Gebaeude durch den Filter akzeptiert werden kann
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
		if(numberFilterItems > 0) {													// Wurde wenigstens ein Gebaeude auf der Karte angezeigt ...
			double finalLatitude = averageLatitude / numberFilterItems;				// ... wird der Durchschnitt der Breiten- und Laengengrade ermittelt, ...
			double finalLongitude = averageLongitude / numberFilterItems;
			LatLng averagePosition = new LatLng(finalLatitude, finalLongitude);		// ... daraus eine, fuer GoogleMaps verstaendliche, Variable zur Positionsbestimmung gemacht...
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(averagePosition, startZoomLevel));		// ... und die Kameraposition an diese Stelle gesetzt.
			if(numberFilterItems == 1) {
				displayedMarkers.get(0).getMarker().showInfoWindow();				// Wurde nur ein Gebaeude auf der Karte angezeigt, wird dessen Infofenster angezeigt.
			}
		} else {
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KOETHEN, startZoomLevel));		// Ansonsten wird die Kamera ueber die Standardposition geschwenkt.
		}
	    map.animateCamera(CameraUpdateFactory.zoomTo(startZoomLevel), 2000, null);	// Zum schluss wird der Zoomlevel auf den Standardwert gesetzt.
	}
	
	public void createNewMarker(Building building) {				// Erstellung eines Gebaeude-Markers
		LatLng newBuilding = new LatLng(building.getExactLatitude(), building.getExactLongitude());		// Bestimmung der exakten Breiten- und Laengengrade
		String title = building.getName();								// Bestimmung des Gebaeudenamens
		byte index = building.getBuildingCategory().getID();			// Bestimmung der Gebaeude-ID
				
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
		
		map.setOnInfoWindowClickListener(			// Setzen der OnClickListener fuer das Infofenster des erstellten Markers
				new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick(Marker clickedMarker) {
						startDetailedView(clickedMarker.getId());		// Starten der Detail-Ansicht des Gebaeudes
                    }
                }
            );

        displayedMarkers.add(new ExtendetMarker(newBuildingMarker, building.getBuildingCategory().getID(), building.getID()));		// Speichern aller angezeigten Markers, so das sie von anderen Funktionen leichter abgefragt werden koennen.
		
	}
	
	public void setMarkersDependingOnFilter(){		//Setzen aller Marker
		BuildingManager buildingManager = BuildingManager.getInstance();		// Holen der Gebaeude-Daten aus der XML
		List<Building> buildings = buildingManager.getBuildingList();
		
		for(Building building : buildings)										// Fuer jedes Gebaeude wird ...
		{
			byte categoryId = building.getBuildingCategory().getID();			// ... die Kategorie und Gebaeude-ID ermittelt ...
			byte buildingId = building.getID();
			if((building.getLatitude() != null) && (building.getLongitude() != null) && filterContainsBuilding(categoryId, buildingId))
			{																	// ... um damit den Filter abzufragen. Die Breiten- und Laengengrade muessen ebenfalls vorhanden sein.
				createNewMarker(building);											// Ein neuer Marker wird erstellt ...
				averageLatitude = averageLatitude + building.getExactLatitude();	// ... und die Daten zur Durchschnittsberechnung fuer die Kameraposition bearbeitet.
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
		while((!displayedMarkers.isEmpty()) && (i < displayedMarkers.size()) && (!matchedMarkerFound)) {		// Suche nach den, zum Marker gehoerigen Daten.
			if(displayedMarkers.get(i).getMarker().getId().equals(markerId)) {
				currentCategId = displayedMarkers.get(i).getCategId();											// Bestimmung der Kategorie und der Gebaeude-Id des Markers
				currentBuildId = displayedMarkers.get(i).getBuildId();
				matchedMarkerFound = true;
			}
			i++;
		}
		if(matchedMarkerFound) {
			Intent intent = new Intent(this, DetailActivity.class);				// Erstellung eines Intents fuer die Detail-Ansicht.
	        intent.putExtra("category", currentCategId);						// �bergabe der Kategorie und Gebaeude-ID.
	        intent.putExtra("building", currentBuildId);
	        this.startActivity(intent);											// Aufruf der Detail-Ansicht.
		}
	}
	
	public void clearMarker(int i) {				// Loeschen eines spezifischen Markers
		if(displayedMarkers.size() > i) {
			Marker delMkr = displayedMarkers.get(i).getMarker();
			displayedMarkers.remove(i);
			delMkr.remove();
		}
	}
	
	public void clearAllMarkers() {				// Loeschen aller dargestellten Marker
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
					
					previousZoomLevel = currentZoomLevel;
				}
			}
		};
	}
	
	
	
}