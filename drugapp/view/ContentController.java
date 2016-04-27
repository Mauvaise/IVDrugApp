package drugapp.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import drugapp.MainApp;
import drugapp.model.Substance;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ContentController {
	
	@FXML
	private ChoiceBox<Substance> fluidsDropDown;
	
	@FXML
	private HBox autoSearchBox;

	@FXML
	private TextField autoSearchField;

	@FXML
	private Button searchButton;

	@FXML
	private TableView<Substance> searchResultsTable;

	@FXML
	private TableColumn<Substance, String> mainNameColumn;
	
	@FXML
	private TableColumn<Substance, String> altNameColumn;
	
	@FXML
	private TableColumn<Substance, String> incompatibilitiesColumn;

	// Reference to the main application.
	private MainApp mainApp;
	private ArrayList<String> possibleSuggestions;
	private AutoCompletionBinding<String> autoCompletionBinding;
	

	public ContentController() {

	}

	@FXML
	private void initialize() {
		mainNameColumn.setCellValueFactory(cellData -> cellData.getValue().mainNameProperty());
		altNameColumn.setCellValueFactory(cellData -> cellData.getValue().altNameProperty());
	
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		ObservableList<Substance> substanceList = mainApp.getSubstanceData();
		
		// This line makes the list of substances display in the table view
		searchResultsTable.setItems(substanceList);
		fluidsDropDown.setItems(mainApp.getFluidData());
		 possibleSuggestions = new ArrayList<String>();
		
		for(Substance substance: substanceList) {
			possibleSuggestions.add(substance.getMainName());
			if (substance.getAltName() != null) {
				possibleSuggestions.add(substance.getAltName());
			}
		}
		
		this.autoCompletionBinding = TextFields.bindAutoCompletion(autoSearchField, possibleSuggestions);
		HBox.setHgrow(autoSearchField, Priority.ALWAYS);
	}
}
