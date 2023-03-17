package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.config.JdbcConfigurator;
import com.dto.Coordonnee;
import com.dto.Ville;
import com.mysql.cj.util.StringUtils;

@Repository
public class VilleDAOImpl implements VilleDAO {

	public ArrayList<Ville> findAllVilles(String codePostal) {
		
		ArrayList<Ville> listVille = new ArrayList<Ville>();
		JdbcConfigurator database = new JdbcConfigurator();
		if (codePostal.length()==5 && StringUtils.isStrictlyNumeric(codePostal)) {
			listVille = this.tryCodePostal(database, codePostal);
		}
		else {
			listVille = this.tryAllVille(database);
		}
		return listVille;
	}
	
	private ArrayList<Ville> tryCodePostal(JdbcConfigurator database, String codePostal) {
		String queryCategory = "SELECT * FROM ville_france WHERE ville_france.Code_postal = "+ codePostal;
		ArrayList<Ville> listVille = new ArrayList<Ville>();
		PreparedStatement statementCategory;
		
		try {
			statementCategory = database.getConnection().prepareStatement(queryCategory);
			ResultSet resultCategory = statementCategory.executeQuery();
			resultCategory.next();
			String nomCommune = resultCategory.getString("Nom_commune");
			String codeCommune = resultCategory.getString("Code_commune_INSEE");
			String longitude = resultCategory.getString("Longitude");
			String latitude = resultCategory.getString("Latitude");
			String ligne = resultCategory.getString("Ligne_5");
			Coordonnee coord = new Coordonnee(longitude, latitude);
			
			Ville ville = new Ville(nomCommune, codeCommune, codePostal, ligne, coord);
			listVille.add(ville);
		}
		catch (SQLException e) {
			listVille = this.tryAllVille(database);
		}
		return listVille;
	}
	
	private ArrayList<Ville> tryAllVille(JdbcConfigurator database) {
		String queryCategory = "SELECT * FROM ville_france";
		ArrayList<Ville> listVille = new ArrayList<Ville>();
		PreparedStatement statementCategory;
		try {
			statementCategory = database.getConnection().prepareStatement(queryCategory);
			ResultSet resultCategory = statementCategory.executeQuery();
			while (resultCategory.next()) {
				String nomCommune = resultCategory.getString("Nom_commune");
				String codeCommune = resultCategory.getString("Code_commune_INSEE");
				String codePostal = resultCategory.getString("Code_postal");
				String longitude = resultCategory.getString("Longitude");
				String latitude = resultCategory.getString("Latitude");
				String ligne = resultCategory.getString("Ligne_5");
				Coordonnee coord = new Coordonnee(longitude, latitude);

				Ville ville = new Ville(nomCommune, codeCommune, codePostal, ligne, coord);
				listVille.add(ville);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return listVille;
	}

}
