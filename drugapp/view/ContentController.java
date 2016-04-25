package drugapp.view;

import drugapp.MainApp;
import drugapp.model.Substance;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ContentController {

	@FXML
	private ChoiceBox<Substance> fluidsDropDown;

	@FXML
	private TextField searchField;

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

	public ContentController() {

	}

	@FXML
	private void initialize() {
		mainNameColumn.setCellValueFactory(cellData -> cellData.getValue().mainNameProperty());
		altNameColumn.setCellValueFactory(cellData -> cellData.getValue().altNameProperty());
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		// This line makes the list of substances display in the table view
		searchResultsTable.setItems(mainApp.getSubstanceData());
	}
	
	
}
