package drugapp;

import java.io.IOException;
import java.util.Set;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import drugapp.model.Substance;
import drugapp.view.ContentController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Substance> substanceList;
	private ObservableList<Substance> fluidList;
	private SubstanceDataFetcher dataFetcher;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("IVDrugApp");

		initRootLayout();

		this.dataFetcher = new SubstanceDataFetcher();
		substanceList = FXCollections.observableArrayList(dataFetcher.getAllSubstances());
		fluidList = FXCollections.observableArrayList(dataFetcher.getAllFluids());

		showContent();
	}

	/*public void handle(KeyEvent enter) {
		switch (enter.getCode()) {
		case ENTER:
			autoCompletionLearnWord(learningTextField.getText().trim());
			break;
		default:
			break;

		}
	}*/
	
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
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ObservableList<Substance> getSubstanceData() {
		return substanceList;
	}

	public ObservableList<Substance> getFluidData() {
		return fluidList;
	}
}