package drugapp;

import java.sql.*;
import java.util.ArrayList;

import drugapp.model.Substance;

/**
 * 
 * The Class SubstanceDataFetcher connects 
 * to the SQL database and fetches all necessary 
 * data for parsing in MainApp.
 * 
 * @author kgray7
 * @see MainApp 
 * 
 */
class SubstanceDataFetcher {

	/**
	 * Queries database for all substances.
	 *
	 * @return all substances
	 */
	public ArrayList<Substance> getAllSubstances() {
		try {
			String url = "jdbc:mysql://localhost:3306/substancesdb";
			Connection conn = DriverManager.getConnection(url, "Tetris", "L8erA11ig8er");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM substance");
			ArrayList<Substance> substanceList = new ArrayList<Substance>();

			while (rs.next()) {

				Integer substanceId = rs.getInt("substance_id");
				String mainName = rs.getString("main_name");
				mainName = mainName.replace("\\", "");
				String altName = rs.getString("alt_name");		
				if (altName != null){
				   altName = altName.replace("\\", "");
				}
				String incompatibilities = rs.getString("incompatibilities");
				Substance substance = new Substance(substanceId, mainName, altName, incompatibilities);
				substanceList.add(substance);
			}
			conn.close();
			return substanceList;

		} catch (Exception exception) {
			System.err.println("Connection failed! ");
			exception.printStackTrace();
			return null;
		}

	}

	/**
	 * Queries the database for substances matching 
	 * mainName or altName of parameters. 
	 *
	 * @param substanceName the substance name
	 * @return the substance by name
	 */

	public Substance getSubstanceByName(String substanceName) {
		try {
			String url = "jdbc:mysql://localhost:3306/substancesdb";
			Connection conn = DriverManager.getConnection(url, "Tetris", "L8erA11ig8er");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM substance WHERE main_name LIKE \'" + substanceName
					+ "\' OR  alt_name LIKE \'" + substanceName + "\'");
			Substance substance = null;
			if (rs.next()) {
				Integer substanceId = rs.getInt("substance_id");
				String mainName = rs.getString("main_name");
				mainName = mainName.replace("\\", "");
				String altName = rs.getString("alt_name");
				if (altName != null){
					   altName = altName.replace("\\", "");
					}
				String incompatibilities = rs.getString("incompatibilities");
				substance = new Substance(substanceId, mainName, altName, incompatibilities);

			}

			conn.close();
			return substance;

		} catch (Exception exception) {
			System.err.println("Connection failed! ");
			exception.printStackTrace();
			return null;
		}

	}

	/**
	 * Gets all incompatibilities by their id.
	 *
	 * @param incompatibilityList the incompatibility list
	 * @return all incompatibilities by name
	 */
	public ArrayList<Substance> getAllIncompatibilitiesByName(ArrayList<Integer> incompatibilityList) {
		try {
			String url = "jdbc:mysql://localhost:3306/substancesdb";
			Connection conn = DriverManager.getConnection(url, "Tetris", "L8erA11ig8er");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM substance WHERE incompatibilities LIKE \'" + incompatibilityList + "\'");
			ArrayList<Substance> substanceIncompatibilityList = new ArrayList<Substance>();

			while (rs.next()) {
				Integer substanceId = rs.getInt("substance_id");
				String mainName = rs.getString("main_name");
				String altName = rs.getString("alt_name");
				String incompatibilities = rs.getString("incompatibilities");
				Substance substance = new Substance(substanceId, mainName, altName, incompatibilities);
				substanceIncompatibilityList.add(substance);
			}

			conn.close();
			return substanceIncompatibilityList;

		} catch (Exception exception) {
			System.err.println("Connection failed! ");
			exception.printStackTrace();
			return null;
		}

	}
}