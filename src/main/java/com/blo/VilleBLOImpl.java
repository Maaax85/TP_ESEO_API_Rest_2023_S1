package com.blo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.VilleDAO;
import com.dto.Ville;

@Service
public class VilleBLOImpl implements VilleBLO {

	@Autowired
	private VilleDAO villeDAO;

	public ArrayList<Ville> getInfoVilles(String codePostal, String codeCommunal) {
		return villeDAO.getInfoVilles(codePostal, codeCommunal);
	}

	public void addVille(Ville ville) {
		villeDAO.addVille(ville);
	}
	
	public void editVille(Ville ville) {
		villeDAO.editVille(ville);
	}
	
	public void deleteVille(Ville ville) {
		villeDAO.deleteVille(ville);
	}
	
	public void inhibVille(Ville ville) {
		villeDAO.inhibVille(ville);
	}

}
