package drugapp.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class Incompatibility {
	private SimpleStringProperty mainName;
	private SimpleStringProperty incompatibilties;
	private ArrayList<String> incompatibilityList;

	public Incompatibility(String mainName) {
		this.mainName = new SimpleStringProperty(mainName);
		this.incompatibilityList = new ArrayList<String>();
		this.incompatibilties = new SimpleStringProperty("No incompatibilities found");
	}

	public void addIncompatibility(String incompatibleSubstanceName) {
		incompatibilityList.add(incompatibleSubstanceName);
		String incompatibilityListString = "";

		for (String addedSubstanceName : incompatibilityList) {
			incompatibilityListString += addedSubstanceName + ", ";
		}

		incompatibilityListString = incompatibilityListString.substring(0, incompatibilityListString.length() - 2);
		this.incompatibilties.set(incompatibilityListString);
	}

	public SimpleStringProperty getMainNameProperty() {
		return mainName;
	}

	public SimpleStringProperty getIncompatibilitiesProperty() {
		return incompatibilties;
	}

	public String toString() {
		return this.mainName.get() + " is incompatible with: " + this.incompatibilityList;
	}
}
