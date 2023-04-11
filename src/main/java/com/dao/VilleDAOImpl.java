package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.config.JdbcConfigurator;
import com.dto.Ville;
import com.mysql.cj.util.StringUtils;

@Repository
public class VilleDAOImpl implements VilleDAO {

	private static final String NOM_COMMUNE_PARAM = "Nom_commune";
	private static final String CODE_POSTAL_PARAM = "Code_postal";
	private static final String CODE_COMMUNE_PARAM = "Code_Commune_INSEE";
	private static final String LIGNE_5_PARAM = "Ligne_5";
	private static final String LATITUDE_PARAM = "Latitude";
	private static final String LONGITUDE_PARAM = "Longitude";
	private static final String FLAG_PARAM = "Flag";

	private static final String SELECT_ALL = "SELECT * FROM ville_france";
	private static final String SELECT_CODE_COMMUNE = "SELECT Code_Commune_INSEE FROM ville_france";

	public void inhibVille(Ville ville) {
		JdbcConfigurator database = new JdbcConfigurator();
		String queryFlag = "SELECT flag FROM ville_france WHERE `Code_commune_INSEE`=" + ville.getCodeCommune();
		try (PreparedStatement statementFlag = database.getConnection().prepareStatement(queryFlag);
				ResultSet resultFlag = statementFlag.executeQuery();) {
			resultFlag.next();
			int flag = resultFlag.getInt("flag");
			String queryChangeFlag = "UPDATE `ville_france` SET flag=? WHERE `Code_commune_INSEE`="
					+ ville.getCodeCommune();
			PreparedStatement statementChangeFlag = database.getConnection().prepareStatement(queryChangeFlag);
			if (flag == 1)
				statementChangeFlag.setString(1, "0");
			else
				statementChangeFlag.setString(1, "1");
			statementChangeFlag.executeUpdate();
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		database.closeDatabase();
	}

	public void deleteVille(Ville ville) {
		JdbcConfigurator database = new JdbcConfigurator();
		String queryCategory = SELECT_ALL;
		boolean estPresente = false;
		String codeCommune = null;
		try (PreparedStatement statementCategory = database.getConnection().prepareStatement(queryCategory);
				ResultSet resultCategory = statementCategory.executeQuery();) {
			while (resultCategory.next()) {
				String codeCommuneInseeDB = resultCategory.getString(CODE_COMMUNE_PARAM);
				if (codeCommuneInseeDB.equals(ville.getCodeCommune())) {
					estPresente = true;
					codeCommune = codeCommuneInseeDB;
				}
			}
			if (estPresente) {
				queryCategory = "DELETE FROM `ville_france` WHERE `Code_commune_INSEE`=" + codeCommune;
				PreparedStatement statementCategory2 = database.getConnection().prepareStatement(queryCategory);
				statementCategory2.executeUpdate();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		database.closeDatabase();
	}

	public void editVille(Ville ville) {
		JdbcConfigurator database = new JdbcConfigurator();
		String queryCategory = SELECT_CODE_COMMUNE;
		boolean estPresente = false;
		String codeCommune = null;
		try (PreparedStatement statementCategory = database.getConnection().prepareStatement(queryCategory);
				ResultSet resultCategory = statementCategory.executeQuery();) {
			while (resultCategory.next()) {
				String codeCommuneInseeDB = resultCategory.getString(CODE_COMMUNE_PARAM);
				if (codeCommuneInseeDB.equals(ville.getCodeCommune())) {
					estPresente = true;
					codeCommune = codeCommuneInseeDB;
				}
			}
			if (estPresente) {
				boolean isFirst = true;
				queryCategory = "UPDATE `ville_france` SET ";
				if (ville.getNomCommune() != null) {
					isFirst = false;
					queryCategory += "`Nom_commune`= '" + ville.getNomCommune() + "',";
					queryCategory += "`Libelle_acheminement`= '" + ville.getNomCommune() + "'";
				}
				if (ville.getCodePostal() != null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Code_postal`= '" + ville.getCodePostal() + "'";
				}
				if (ville.getLigne() != null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Ligne_5`= '" + ville.getLigne() + "'";
				}
				if (ville.getLatitude() != null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Latitude`= '" + ville.getLatitude() + "'";
				}
				if (ville.getLongitude() != null) {
					if (isFirst)
						isFirst = false;
					else
						queryCategory += ",";
					queryCategory += "`Longitude`= '" + ville.getLongitude() + "'";
				}

				if (!queryCategory.equals("UPDATE `ville_france` SET ")) {
					queryCategory += " WHERE `Code_commune_INSEE`=" + codeCommune;
					PreparedStatement statementCategory2 = database.getConnection().prepareStatement(queryCategory);
					statementCategory2.executeUpdate();
				}
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		database.closeDatabase();
	}

	public void addVille(Ville ville) {
		JdbcConfigurator database = new JdbcConfigurator();
		String queryCategory = SELECT_ALL;
		try (PreparedStatement statementCategory = database.getConnection().prepareStatement(queryCategory);
				ResultSet resultCategory = statementCategory.executeQuery();) {
			boolean estPresente = false;
			while (resultCategory.next()) {
				String nomCommuneDB = resultCategory.getString(NOM_COMMUNE_PARAM);
				int codePostalDB = resultCategory.getInt(CODE_POSTAL_PARAM);
				if (nomCommuneDB.equals(ville.getNomCommune()) && ("" + codePostalDB).equals(ville.getCodePostal())) {
					estPresente = true;
				}
			}
			if (!estPresente) {
				queryCategory = "INSERT INTO `ville_france`(`Code_commune_INSEE`, `Nom_commune`, `Code_postal`, `Libelle_acheminement`, `Ligne_5`, `Latitude`, `Longitude`) VALUES (?,?,?,?,'',?,?)";
				PreparedStatement statementCategory2 = database.getConnection().prepareStatement(queryCategory);
				statementCategory2.setString(1, ville.getCodeCommune());
				statementCategory2.setString(2, ville.getNomCommune());
				statementCategory2.setString(3, ville.getCodePostal());
				statementCategory2.setString(4, ville.getNomCommune());
				statementCategory2.setString(5, ville.getLatitude());
				statementCategory2.setString(6, ville.getLongitude());
				statementCategory2.executeUpdate();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		database.closeDatabase();
	}

	public List<Ville> getInfoVilles(String codePostal, String codeCommunal) {

		List<Ville> listVille;
		JdbcConfigurator database = new JdbcConfigurator();
		if (codePostal != null && codePostal.length() == 5 && StringUtils.isStrictlyNumeric(codePostal)) {
			listVille = this.tryCodePostal(database, codePostal);
		} else if (codeCommunal != null && codeCommunal.length() == 5 && StringUtils.isStrictlyNumeric(codeCommunal)) {
			listVille = this.tryCodeCommunal(database, codeCommunal);
		} else {
			listVille = this.tryAllVille(database);
		}
		database.closeDatabase();
		return listVille;
	}

	private List<Ville> tryCodePostal(JdbcConfigurator database, String codePostal) {
		String queryCategory = "SELECT * FROM ville_france WHERE ville_france.Code_postal = " + codePostal;
		List<Ville> listVille = new ArrayList<>();

		try (PreparedStatement statementCategory = database.getConnection().prepareStatement(queryCategory);
				ResultSet resultCategory = statementCategory.executeQuery();) {
			resultCategory.next();
			String nomCommune = resultCategory.getString(NOM_COMMUNE_PARAM);
			String codeCommune = resultCategory.getString(CODE_COMMUNE_PARAM);
			String longitude = resultCategory.getString(LONGITUDE_PARAM);
			String latitude = resultCategory.getString(LATITUDE_PARAM);
			String ligne = resultCategory.getString(LIGNE_5_PARAM);
			String flag = resultCategory.getString(FLAG_PARAM);

			Ville ville = new Ville(nomCommune, codeCommune, codePostal, ligne, latitude, longitude, flag);
			listVille.add(ville);
		} catch (SQLException e) {
			listVille = this.tryAllVille(database);
		}
		return listVille;
	}

	private List<Ville> tryCodeCommunal(JdbcConfigurator database, String codeCommunal) {
		String queryCategory = "SELECT * FROM ville_france WHERE ville_france.Code_commune_INSEE = " + codeCommunal;
		List<Ville> listVille = new ArrayList<>();

		try (PreparedStatement statementCategory = database.getConnection().prepareStatement(queryCategory);
				ResultSet resultCategory = statementCategory.executeQuery();) {
			resultCategory.next();
			String nomCommune = resultCategory.getString(NOM_COMMUNE_PARAM);
			String codePostal = resultCategory.getString(CODE_COMMUNE_PARAM);
			String longitude = resultCategory.getString(LONGITUDE_PARAM);
			String latitude = resultCategory.getString(LATITUDE_PARAM);
			String ligne = resultCategory.getString(LIGNE_5_PARAM);
			String flag = resultCategory.getString(FLAG_PARAM);

			Ville ville = new Ville(nomCommune, codeCommunal, codePostal, ligne, latitude, longitude, flag);
			listVille.add(ville);
		} catch (SQLException e) {
			listVille = this.tryAllVille(database);
		}
		return listVille;
	}

	private List<Ville> tryAllVille(JdbcConfigurator database) {
		String queryCategory = SELECT_ALL;
		List<Ville> listVille = new ArrayList<>();
		try (PreparedStatement statementCategory = database.getConnection().prepareStatement(queryCategory);
				ResultSet resultCategory = statementCategory.executeQuery();) {
			while (resultCategory.next()) {
				String nomCommune = resultCategory.getString(NOM_COMMUNE_PARAM);
				String codeCommune = resultCategory.getString(CODE_COMMUNE_PARAM);
				String codePostal = resultCategory.getString(CODE_POSTAL_PARAM);
				String longitude = resultCategory.getString(LONGITUDE_PARAM);
				String latitude = resultCategory.getString(LATITUDE_PARAM);
				String ligne = resultCategory.getString(LIGNE_5_PARAM);
				String flag = resultCategory.getString(FLAG_PARAM);

				Ville ville = new Ville(nomCommune, codeCommune, codePostal, ligne, latitude, longitude, flag);
				listVille.add(ville);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return listVille;
	}

}
