package drugapp.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

/**
 * The Class Incompatibility models the database 
 * results as they pertain to incompatibilities.
 * 
 * @author kgray7
 * 
 */
public class Incompatibility {
	private SimpleStringProperty mainName;
	private SimpleStringProperty incompatibilities;
	private ArrayList<String> incompatibilityList;

	/**
	 * Instantiates a new incompatibility.
	 *
	 * @param mainName the main name
	 */
	public Incompatibility(String mainName) {
		this.mainName = new SimpleStringProperty(mainName);
		this.incompatibilityList = new ArrayList<String>();
		this.incompatibilities = new SimpleStringProperty("No incompatibilities found");
	}

	/**
	 * Adds the incompatibility.
	 *
	 * @param incompatibleSubstanceName the incompatible substance name
	 */
	public void addIncompatibility(String incompatibleSubstanceName) {
		incompatibilityList.add(incompatibleSubstanceName);
		String incompatibilityListString = "";
		// Separates incompatibilities by adding a comma and space at the end of each string
		for (String addedSubstanceName : incompatibilityList) {
			incompatibilityListString += addedSubstanceName + ", ";
		}
		// Removes last comma and space 
		incompatibilityListString = incompatibilityListString.substring(0, incompatibilityListString.length() - 2);
		this.incompatibilities.set(incompatibilityListString);
	}

	/**
	 * Gets the main name property.
	 *
	 * @return the main name property
	 */
	public SimpleStringProperty getMainNameProperty() {
		return mainName;
	}

	/**
	 * Gets the incompatibilities property.
	 *
	 * @return the incompatibilities property
	 */
	public SimpleStringProperty getIncompatibilitiesProperty() {
		return incompatibilities;
	}
}
