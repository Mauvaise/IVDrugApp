package drugapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import drugapp.model.Substance;

/**
 * Test Query Need to write data parsing methods in this class - might move to
 * another class
 */

class SubstanceDataFetcher {

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
				String altName = rs.getString("alt_name");
				String incompatibilities = rs.getString("incompatibilities");
				Substance substance = new Substance(substanceId, mainName, altName, incompatibilities);
				//System.out.println(incompatibilities);
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
}