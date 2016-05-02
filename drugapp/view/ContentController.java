package drugapp.view;

import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * The Class ContentController deals with translating 
 * the data into viewable contents in the application.
 * 
 * @author kgray7
 * 
 */
public class ContentController {
	private MainApp mainApp;
	private ArrayList<String> searchedSubstancesArray;
	private ObservableList<Substance> addedSubstances;
	private ObservableList<Incompatibility> incompatibleSubstances;
	
	/** The possible suggestions array for auto-complete function. */
	private ArrayList<String> possibleSuggestions;
	
	/** The auto-completion binding. */
	private AutoCompletionBinding<String> autoCompletionBinding;

	@FXML
	private HBox autoSearchBox;

	@FXML
	private TextField autoSearchField;

	@FXML
	private Button searchButton;

	@FXML
	private TableView<Substance> addedSubstanceTable;

	@FXML
	private TableColumn<Substance, String> mainNameAddedSubstanceColumn;

	@FXML
	private Button addButton;

	@FXML
	private TableView<Incompatibility> searchResultsTable;

	@FXML
	private TableColumn<Incompatibility, String> mainNameColumn;

	@FXML
	private TableColumn<Incompatibility, String> incompatibilitiesColumn;

	/**
	 * Instantiates a new content controller.
	 */
	public ContentController() {

	}

	/**
	 * Initializes the controller class automatically after fxml file loads.
	 */
	@FXML
	private void initialize() {
		searchResultsTable.setPlaceholder(new Label("No drugs searched"));
		addedSubstanceTable.setPlaceholder(new Label("No drugs added"));
		mainNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMainNameProperty());
		incompatibilitiesColumn.setCellValueFactory(cellData -> cellData.getValue().getIncompatibilitiesProperty());
		mainNameAddedSubstanceColumn.setCellValueFactory(cellData -> cellData.getValue().mainNameProperty());
	}

	/**
	 * Sets the controller's reference to the mainApp.
	 *
	 * @param mainApp the new main app
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		ObservableList<Substance> substanceList = mainApp.getSubstanceData();

		// This if statement prevents NullPointer exception if list is empty.
		if (substanceList != null) {
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
			
		// This else statement shows a pop-up when there is no database connection.
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Server Error");
			alert.setHeaderText("No Connection Found!");
			alert.showAndWait();
		}
		
		// Handle Button event that adds Substances to substances to be searched table.
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
		
		// Handle Button event that adds Substances to searched substances table.
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Substance> substancesToCheck = new ArrayList<>();
				for (Substance addedSubstance : addedSubstances) {
					// Flag set to check if addedSubstances has duplicates
					Boolean addThisSubstance = true; 

					for (Substance substanceToCheck : substancesToCheck) {
						if (substanceToCheck.getSubstanceId() == addedSubstance.getSubstanceId()) {
							addThisSubstance = false;
						}
					}
					// Only if addedSubstance is not a duplicate - add it to search list
					if (addThisSubstance) {
						substancesToCheck.add(addedSubstance);
					}
				}
				
				System.out.println(substancesToCheck);
				incompatibleSubstances = FXCollections.observableArrayList(mainApp.getIncompatibilityList(substancesToCheck));
				searchResultsTable.setItems(incompatibleSubstances);
			}

		});
		// Handle Button event that adds Substances to substances to be searched table.
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addSubstance();
			}
		});

	}

	/**
	 * Adds the substance to the substances to be searched table
	 */
	private void addSubstance() {
		Substance enteredSubstance = mainApp.searchForSubstanceByName(autoSearchField.getText().trim());
		updateSubstanceTable(enteredSubstance);
	}

	/**
	 * Updates substance table cell when a substance is added.
	 *
	 * @param enteredSubstance the entered substance
	 */
	private void updateSubstanceTable(Substance enteredSubstance) {
		if (enteredSubstance != null) {
			addedSubstances.add(enteredSubstance);
			addedSubstanceTable.setItems(addedSubstances);
		}
	}
}