package drugapp.model;

import javafx.beans.property.SimpleStringProperty;

public class Incompatibility {
	private SimpleStringProperty mainName;
	private SimpleStringProperty incompatibilties;
	
	public Incompatibility(String mainName) {
		this.mainName = new SimpleStringProperty(mainName);
		this.incompatibilties = new SimpleStringProperty();
	}
	
	public void addIncompatibility(String incompatibleSubstanceName) {
		if (incompatibilties.getValue() == "") {
			incompatibilties = new SimpleStringProperty(incompatibleSubstanceName); 
			
		} else {
			incompatibilties = new SimpleStringProperty(incompatibilties.getValue() + ", " + incompatibleSubstanceName);
		}
	}
	
	public SimpleStringProperty getMainNameProperty() {
		return mainName;
	}
	
	public SimpleStringProperty getIncompatibilitiesProperty() {
		return incompatibilties;
	}

}
