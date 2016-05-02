package drugapp.model;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Class Substance models the database results
 * as SimpleProperties for viewing in the ContentController.
 * 
 * @author kgray7
 * @see ContentController
 */
public class Substance {
	protected SimpleIntegerProperty substanceId;
	protected SimpleStringProperty mainName;
	protected SimpleStringProperty altName;
	protected SimpleStringProperty incompatibilities;
	protected List<String> incompatibilitiesList;

	/**
	 * Instantiates a new substance.
	 *
	 * @param substanceId the substance id
	 * @param mainName the main name
	 * @param altName the alt name
	 * @param incompatibilities the incompatibilities
	 */
	public Substance(Integer substanceId, String mainName, String altName, String incompatibilities) {
		this.substanceId = new SimpleIntegerProperty(substanceId);
		this.mainName = new SimpleStringProperty(mainName);
		this.altName = new SimpleStringProperty(altName);
		setIncompatArray(incompatibilities);
	}

	/**
	 * Gets the substance id.
	 *
	 * @return the substance id
	 */
	public Integer getSubstanceId() {
		return substanceId.get();
	}

	/**
	 * Gets the main name.
	 *
	 * @return the main name
	 */
	public String getMainName() {
		return mainName.get();
	}

	/**
	 * Main name property.
	 *
	 * @return the simple string property
	 */
	public SimpleStringProperty mainNameProperty() {
		return mainName;
	}

	/**
	 * Alt name property.
	 *
	 * @return the simple string property
	 */
	public SimpleStringProperty altNameProperty() {
		return altName;
	}

	/**
	 * Gets the alt name.
	 *
	 * @return the alt name
	 */
	public String getAltName() {
		return altName.get();
	}

	/* Turn simple strings into Strings
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getSubstanceId() + getMainName() + getAltName() + incompatibilitiesList;
	}

	/**
	 * Sets the incompatibilities for each Substance by 
	 * splitting up each incompatibility string into an array.
	 *
	 * @param incompatibilities the new incompat array
	 */
	private void setIncompatArray(String incompatibilities) {
		this.incompatibilitiesList = new ArrayList<String>();
		if (incompatibilities != null) {
			String[] substanceSplit = incompatibilities.split(",");
			this.incompatibilitiesList = Arrays.asList(substanceSplit);
		}
	}

	/**
	 * Gets the incompatibilities array.
	 *
	 * @return the incompat array
	 */
	public List<String> getIncompatArray() {
		return incompatibilitiesList;
	}

	/**
	 * Checks if a Substance's incompatibility array contains any incompatibilities 
	 * with another Substance's id.
	 *
	 * @param substance the substance
	 * @return true, if is incompatible with
	 */
	public boolean isIncompatibleWith(Substance substance) {
		for (String incompatibleSubstanceId : substance.getIncompatArray()) {
			if (Integer.parseInt(incompatibleSubstanceId) == this.getSubstanceId()) {
				return true;
			}
		}
		return false;
	}
}