package com.dto;

public class Ville {

	private String nomCommune;
	private String codeCommune;
	private String codePostal;
	private String ligne;
	private String latitude;
	private String longitude;
	private String flag;

	public Ville(String nomCommune, String codeCommune, String codePostal, String ligne, String latitude, String longitude, String flag) {
		this.nomCommune = nomCommune;
		this.codeCommune = codeCommune;
		this.codePostal = codePostal;
		this.ligne = ligne;
		this.latitude = latitude;
		this.longitude = longitude;
		this.flag = flag;
	}
	
	public Ville() {
		
	}

	public String getCodeCommune() {
		return codeCommune;
	}

	public void setCodeCommune(String codeCommune) {
		this.codeCommune = codeCommune;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getLigne() {
		return ligne;
	}

	public void setLigne(String ligne) {
		this.ligne = ligne;
	}

	public String getNomCommune() {
		return nomCommune;
	}

	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
