package de.hsanhalt.inf.studiappkoethen.util;

import com.google.android.gms.maps.model.Marker;

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
	
	public Marker getMarker() {
		return eMarker;
	}
	
	public byte getCategId() {
		return categoryId;
	}
	
	public byte getBuildId() {
		return buildingId;
	}
	
}
