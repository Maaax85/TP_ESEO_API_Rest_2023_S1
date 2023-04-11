package com.dao;

import java.util.ArrayList;

import com.dto.Ville;

public interface VilleDAO {

	public ArrayList<Ville> getInfoVilles(String codePostal, String codeCommunal);

	public void addVille(Ville ville);
	
	public void editVille(Ville ville);
	
	public void deleteVille(Ville ville);
	
	public void inhibVille(Ville ville);

}
