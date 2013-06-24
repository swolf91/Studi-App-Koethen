package de.hsanhalt.inf.studiappkoethen.util;

import com.google.android.gms.maps.model.Marker;

/*
 * Da ein Marker keine Zusatzdaten speichern kann, werden durch diese Klasse noch die ID's 
 * der Kategorie und des Gebaeudes zu dem Marker gespeichert.
 * Eine Aenderung der Daten nach der Initialisierung ist nicht vorgesehen, daher gibt es 
 * nur Getter- und keine Setter-Funktionen.
 */

public class ExtendetMarker
{
	private Marker eMarker;
	private byte categoryId;
	private byte buildingId;
	
	public ExtendetMarker(Marker newMarker, byte cat, byte bldn) {
		eMarker = newMarker;
		categoryId = cat;
		buildingId = bldn;
	}
	
	public Marker getMarker() {		// Rueckgabe des Markers
		return eMarker;
	}
	
	public byte getCategId() {		// Rueckgabe der Kategorie-ID
		return categoryId;
	}
	
	public byte getBuildId() {		// Rueckgabe der Gebaeude-ID
		return buildingId;
	}
	
}
