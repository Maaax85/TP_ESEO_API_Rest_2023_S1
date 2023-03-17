package com.dto;

public class Coordonnee {
	
	double longitudeDouble;
	double latitudeDouble;
	
	public Coordonnee(String longitude, String latitude) {
		this.longitudeDouble = Double.parseDouble(longitude);
		this.latitudeDouble = Double.parseDouble(latitude);
	}
	
	public Coordonnee(double longitude, double latitude) {
		this.longitudeDouble = longitude;
		this.latitudeDouble = latitude;
	}

	public double getLongitudeDouble() {
		return longitudeDouble;
	}

	public void setLongitudeDouble(double longitudeDouble) {
		this.longitudeDouble = longitudeDouble;
	}

	public double getLatitudeDouble() {
		return latitudeDouble;
	}

	public void setLatitudeDouble(double latitudeDouble) {
		this.latitudeDouble = latitudeDouble;
	}
	
	

}
