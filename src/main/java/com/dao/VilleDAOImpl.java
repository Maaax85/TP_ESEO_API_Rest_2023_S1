package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.config.JdbcConfigurator;
import com.dto.Ville;
import com.mysql.cj.util.StringUtils;

@Repository
public class VilleDAOImpl implements VilleDAO {

	public void inhibVille(Ville ville) {
		JdbcConfigurator database = new JdbcConfigurator();
		String queryCategory = "SELECT Code_Commune_INSEE FROM ville_france";
		PreparedStatement statementCategory;
		try {
			statementCategory = database.getConnection().prepareStatement(queryCategory);
			ResultSet resultCategory = statementCategory.executeQuery();
			while (resultCategory.next()) {
				String codeCommuneInseeDB = resultCategory.getString("Code_Commune_INSEE");
				if (codeCommuneInseeDB.equals(ville.getCodeCommune())) {
					String queryFlag = "SELECT flag FROM ville_france WHERE `Code_commune_INSEE`="+codeCommuneInseeDB;
					PreparedStatement statementFlag = database.getConnection().prepareStatement(queryFlag);
					ResultSet resultFlag = statementFlag.executeQuery();
					resultFlag.next();
					int flag = resultFlag.getInt("flag");
					String queryChangeFlag = "UPDATE `ville_france` SET flag=? WHERE `Code_commune_INSEE`="+codeCommuneInseeDB;
					PreparedStatement statementChangeFlag  = database.getConnection().prepareStatement(queryChangeFlag);
					if (flag==1)
						statementChangeFlag.setString(1, "0");
					else
						statementChangeFlag.setString(1, "1");
					statementChangeFlag.executeUpdate();
				}
			}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		database.closeDatabase();
	}
	
	public void deleteVille(Ville ville) {
		JdbcConfigurator database = new JdbcConfigurator();
		String queryCategory = "SELECT * FROM ville_france";
		PreparedStatement statementCategory;
		boolean estPresente = false;
		String codeCommune=null;
		try {
			statementCategory = database.getConnection().prepareStatement(queryCategory);
			ResultSet resultCategory = statementCategory.executeQuery();
			while (resultCategory.next()) {
				String codeCommuneInseeDB = resultCategory.getString("Code_Commune_INSEE");
				if (codeCommuneInseeDB.equals(ville.getCodeCommune())) {
					estPresente = true;
					codeCommune = codeCommuneInseeDB;
				}
			}
			if (estPresente) {
				queryCategory = "DELETE FROM `ville_france` WHERE `Code_commune_INSEE`=" + codeCommune;
				statementCategory = database.getConnection().prepareStatement(queryCategory);
				statementCategory.executeUpdate();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		database.closeDatabase();
	}
	
	public void editVille(Ville ville) {
		JdbcConfigurator database = new JdbcConfigurator();
		String queryCategory = "SELECT Code_Commune_INSEE FROM ville_france";
		PreparedStatement statementCategory;
		boolean estPresente = false;
		String codeCommune=null;
		try {
			statementCategory = database.getConnection().prepareStatement(queryCategory);
			ResultSet resultCategory = statementCategory.executeQuery();
			while (resultCategory.next()) {
				String codeCommuneInseeDB = resultCategory.getString("Code_Commune_INSEE");
				if (codeCommuneInseeDB.equals(ville.getCodeCommune())) {
					estPresente = true;
					codeCommune = codeCommuneInseeDB;
				}
			}
			if (estPresente) {
				boolean isFirst = true;
				queryCategory = "UPDATE `ville_france` SET ";
				if (ville.getNomCommune()!=null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Nom_commune`= '" + ville.getNomCommune() + "',";
					queryCategory += "`Libelle_acheminement`= '" + ville.getNomCommune() + "'";
				}
				if (ville.getCodePostal()!=null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Code_postal`= '" + ville.getCodePostal() + "'";
				}
				if (ville.getLigne()!=null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Ligne_5`= '" + ville.getLigne() + "'";
				}
				if (ville.getLatitude()!=null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Latitude`= '" + ville.getLatitude() + "'";
				}
				if (ville.getLongitude()!=null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Longitude`= '" + ville.getLongitude() + "'";
				}
				
				if (!queryCategory.equals("UPDATE `ville_france` SET ")) {
					queryCategory+=" WHERE `Code_commune_INSEE`=" + codeCommune;
					statementCategory = database.getConnection().prepareStatement(queryCategory);
					statementCategory.executeUpdate();
				}			
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		database.closeDatabase();
	}
	
	public void addVille(Ville ville) {
		JdbcConfigurator database = new JdbcConfigurator();
		String queryCategory = "SELECT * FROM ville_france";
		PreparedStatement statementCategory;
		try {
			statementCategory = database.getConnection().prepareStatement(queryCategory);
			ResultSet resultCategory = statementCategory.executeQuery();
			boolean estPresente = false;
			while (resultCategory.next()) {
				String nomCommuneDB = resultCategory.getString("Nom_commune");
				int codePostalDB = resultCategory.getInt("Code_postal");
				if (nomCommuneDB.equals(ville.getNomCommune()) && ("" + codePostalDB).equals(ville.getCodePostal())) {
					estPresente = true;
				}
			}
			if (!estPresente) {
				queryCategory = "INSERT INTO `ville_france`(`Code_commune_INSEE`, `Nom_commune`, `Code_postal`, `Libelle_acheminement`, `Ligne_5`, `Latitude`, `Longitude`) VALUES (?,?,?,?,'',?,?)";
				statementCategory = database.getConnection().prepareStatement(queryCategory);
				statementCategory.setString(1, ville.getCodeCommune());
				statementCategory.setString(2, ville.getNomCommune());
				statementCategory.setString(3, ville.getCodePostal());
				statementCategory.setString(4, ville.getNomCommune());
				statementCategory.setString(5, ville.getLatitude());
				statementCategory.setString(6, ville.getLongitude());
				statementCategory.executeUpdate();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		database.closeDatabase();
	}

	public ArrayList<Ville> getInfoVilles(String codePostal, String codeCommunal) {

		ArrayList<Ville> listVille = new ArrayList<Ville>();
		JdbcConfigurator database = new JdbcConfigurator();
		if (codePostal!=null && codePostal.length() == 5 && StringUtils.isStrictlyNumeric(codePostal)) {
			listVille = this.tryCodePostal(database, codePostal);
		}else if (codeCommunal != null && codeCommunal.length() == 5 && StringUtils.isStrictlyNumeric(codeCommunal)) {
			listVille = this.tryCodeCommunal(database, codeCommunal);
		}
		else {
			listVille = this.tryAllVille(database);
		}
		database.closeDatabase();
		return listVille;
	}

	private ArrayList<Ville> tryCodePostal(JdbcConfigurator database, String codePostal) {
		String queryCategory = "SELECT * FROM ville_france WHERE ville_france.Code_postal = " + codePostal;
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
			String flag = resultCategory.getString("Flag");

			Ville ville = new Ville(nomCommune, codeCommune, codePostal, ligne, latitude, longitude, flag);
			listVille.add(ville);
		} catch (SQLException e) {
			listVille = this.tryAllVille(database);
		}
		return listVille;
	}
	
	private ArrayList<Ville> tryCodeCommunal(JdbcConfigurator database, String codeCommunal) {
		String queryCategory = "SELECT * FROM ville_france WHERE ville_france.Code_commune_INSEE = " + codeCommunal;
		ArrayList<Ville> listVille = new ArrayList<Ville>();
		PreparedStatement statementCategory;

		try {
			statementCategory = database.getConnection().prepareStatement(queryCategory);
			ResultSet resultCategory = statementCategory.executeQuery();
			resultCategory.next();
			String nomCommune = resultCategory.getString("Nom_commune");
			String codePostal = resultCategory.getString("Code_postal");
			String longitude = resultCategory.getString("Longitude");
			String latitude = resultCategory.getString("Latitude");
			String ligne = resultCategory.getString("Ligne_5");
			String flag = resultCategory.getString("Flag");

			Ville ville = new Ville(nomCommune, codeCommunal, codePostal, ligne, latitude, longitude, flag);
			listVille.add(ville);
		} catch (SQLException e) {
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
				String flag = resultCategory.getString("Flag");

				Ville ville = new Ville(nomCommune, codeCommune, codePostal, ligne, latitude, longitude, flag);
				listVille.add(ville);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return listVille;
	}

}
