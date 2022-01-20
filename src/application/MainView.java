package application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView {
	
	private Stage primaryStage;

	public MainView(Stage primaryStage) {
		this.primaryStage = primaryStage;
		createUI();
	}
	
	// used to initialized main UI
	private void createUI() {
		
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 464, 295);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// set visible this UI
	public void show() {
		primaryStage.show();
	}

}
