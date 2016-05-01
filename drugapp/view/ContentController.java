package drugapp.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import drugapp.MainApp;
import drugapp.model.Incompatibility;
import drugapp.model.Substance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ContentController {
	// Reference to the main application.
	private MainApp mainApp;
	private ArrayList<String> possibleSuggestions;
	private ArrayList<String> searchedSubstancesArray;
	private AutoCompletionBinding<String> autoCompletionBinding;
	private ObservableList<Substance> addedSubstances;
	private ObservableList<Incompatibility> incompatibleSubstances;

	@FXML
	private ChoiceBox<Substance> fluidsDropDown;

	@FXML
	private HBox autoSearchBox;

	@FXML
	private TextField autoSearchField;

	@FXML
	private Button searchButton;
	
	// Added substances table
	
	@FXML
	private TableView<Substance> addedSubstanceTable;

	@FXML
	private TableColumn<Substance, String> mainNameAddedSubstanceColumn; 
	
	@FXML
	private Button addButton;
	
	// Search results table

	@FXML
	private TableView<Incompatibility> searchResultsTable;

	@FXML
	private TableColumn<Incompatibility, String> mainNameColumn;

	@FXML
	private TableColumn<Incompatibility, String> incompatibilitiesColumn;



	public ContentController() {

	}

	@FXML
	private void initialize() {
		mainNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMainNameProperty());
		incompatibilitiesColumn.setCellValueFactory(cellData -> cellData.getValue().getIncompatibilitiesProperty());
		mainNameAddedSubstanceColumn.setCellValueFactory(cellData -> cellData.getValue().mainNameProperty());
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		ObservableList<Substance> substanceList = mainApp.getSubstanceData();
		
		fluidsDropDown.setItems(mainApp.getFluidData());
		possibleSuggestions = new ArrayList<String>();
		addedSubstances = FXCollections.observableArrayList();
		incompatibleSubstances = FXCollections.observableArrayList();

		for (Substance substance : substanceList) {
			possibleSuggestions.add(substance.getMainName());
			if (substance.getAltName() != null) {
				possibleSuggestions.add(substance.getAltName());
			}
		}

		this.autoCompletionBinding = TextFields.bindAutoCompletion(autoSearchField, possibleSuggestions);
		HBox.setHgrow(autoSearchField, Priority.ALWAYS);

		autoSearchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent enter) {
				switch (enter.getCode()) {
				case ENTER:
					addSubstance();
					break;
				default:
					break;
				}
			}
		});
		
		

		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Substance> substancesToCheck = new ArrayList<>(addedSubstances);
				System.out.println(substancesToCheck);
				ArrayList<Incompatibility> testList = mainApp.getIncompatibilityList(substancesToCheck);
				incompatibleSubstances = FXCollections.observableArrayList(mainApp.getIncompatibilityList(substancesToCheck));
			//	incompatibleSubstances.removeAll(Collections.singleton(null));
				searchResultsTable.setItems(incompatibleSubstances);

				System.out.print("Substances to check: " + testList);			
			}
		});
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addSubstance();				
			}
		});
		
	}
	
	private void addSubstance() {
		Substance enteredSubstance = mainApp.searchForSubstanceByName(autoSearchField.getText().trim());
		updateSubstanceTable(enteredSubstance);
	}

	private void updateSubstanceTable(Substance enteredSubstance) {
		if (enteredSubstance != null) {
			addedSubstances.add(enteredSubstance);
			addedSubstanceTable.setItems(addedSubstances);
		}
	}
	
}
