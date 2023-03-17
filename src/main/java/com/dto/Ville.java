package com.dto;

public class Ville {

	private String nomCommune;
	private String codeCommune;
	private String codePostal;
	private String ligne;
	private Coordonnee coordonnee;

	public Ville(String nomCommune, String codeCommune, String codePostal, String ligne, Coordonnee coordonnee) {
		this.nomCommune = nomCommune;
		this.codeCommune = codeCommune;
		this.codePostal = codePostal;
		this.ligne = ligne;
		this.coordonnee = coordonnee;
	}
	
	public Ville() {
		
	}

	public String getCodeCommune() {
		return codeCommune;
	}

	public void setCodeCommune(String codeCommune) {
		this.codeCommune = codeCommune;
	}

	public Coordonnee getCordonnee() {
		return coordonnee;
	}

	public void setCordonnee(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
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

}
