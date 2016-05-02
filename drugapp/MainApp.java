package drugapp;

import java.io.IOException;
import java.util.ArrayList;
import drugapp.model.Incompatibility;
import drugapp.model.Substance;
import drugapp.view.ContentController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The Class MainApp starts the application and performs all business logic.
 * This includes parsing through query results. 
 * 
 * @author kgray7
 * 
 */
public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Substance> substanceList;
	private SubstanceDataFetcher dataFetcher;

	/**
	 * The main method.
	 *
	 * @param args 
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * Sets up the primary stage. 
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("IVDrugApp");

		initRootLayout();

		this.dataFetcher = new SubstanceDataFetcher();
		// Creates an array of Substances from database results.
		ArrayList<Substance> substanceListFetch = dataFetcher.getAllSubstances();
		if (substanceListFetch != null) {
			substanceList = FXCollections.observableArrayList(substanceListFetch);
		}
		showContent();
	}

	/**
	 * Searches for a String and returns a Substance.
	 *
	 * @param substanceName the substance name
	 * @return the substance
	 */
	public Substance searchForSubstanceByName(String substanceName) {
		return dataFetcher.getSubstanceByName(substanceName);
	}

	/**
	 * Gets the incompatibility list of Substances and returns the
	 * incompatibility list of Incompatibilities.
	 *
	 * @param incompatibilityList the incompatibility list
	 * @return the incompatibility list
	 */
	public ArrayList<Incompatibility> getIncompatibilityList(ArrayList<Substance> incompatibilityList) {
		ArrayList<Incompatibility> addedSubstanceIncompatibilities = new ArrayList<Incompatibility>();
		for (Substance addedSubstance : incompatibilityList) {
			Incompatibility incompatibility = new Incompatibility(addedSubstance.getMainName());

			for (Substance comparableSubstance : incompatibilityList) {
				if (addedSubstance.isIncompatibleWith(comparableSubstance)) {
					incompatibility.addIncompatibility(comparableSubstance.getMainName());
				}
			}
			addedSubstanceIncompatibilities.add(incompatibility);
		}
		return addedSubstanceIncompatibilities;
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the content.
	 */
	public void showContent() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Content.fxml"));
			AnchorPane content = (AnchorPane) loader.load();

			rootLayout.setCenter(content);

			ContentController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 *
	 * @return the primary stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Gets the substance data and returns it as an ObservableList.
	 *
	 * @return the substance data
	 */
	public ObservableList<Substance> getSubstanceData() {
		return substanceList;
	}
}