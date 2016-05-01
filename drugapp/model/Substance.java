package drugapp.model;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Substance 
 * Needs to be typed differently 
 * 
 * 
 */
public class Substance {
	protected SimpleIntegerProperty substanceId;
	protected SimpleStringProperty mainName;
	protected SimpleStringProperty altName;
	protected SimpleStringProperty incompatibilities;
	protected List<String> incompatibilitiesList;

	public Substance(Integer substanceId, String mainName, String altName, String incompatibilities) {
		this.substanceId = new SimpleIntegerProperty(substanceId);
		this.mainName = new SimpleStringProperty(mainName);
		this.altName = new SimpleStringProperty(altName);
		setIncompatArray(incompatibilities);

	}

	public Integer getSubstanceId() {
		return substanceId.get();
	}

	public String getMainName() {
		return mainName.get();
	}

	public SimpleStringProperty mainNameProperty() {
		return mainName;
	}

	public SimpleStringProperty altNameProperty() {
		return altName;
	}

	public String getAltName() {
		return altName.get();
	}

	public String toString() {
		return getSubstanceId() + getMainName() + getAltName() + incompatibilitiesList;
	}

	private void setIncompatArray(String incompatibilities) {
		this.incompatibilitiesList = new ArrayList<String>();
		if (incompatibilities != null) {
			String[] substanceSplit = incompatibilities.split(",");
			this.incompatibilitiesList = Arrays.asList(substanceSplit);
		}

	}

	public List<String> getIncompatArray() {
		return incompatibilitiesList;
	}

	public boolean isIncompatibleWith(Substance substance) {
		for (String incompatibleSubstanceId : substance.getIncompatArray()) {
			if (Integer.parseInt(incompatibleSubstanceId) == this.getSubstanceId()) {
				return true;
			}
		}
		return false;
	}
}