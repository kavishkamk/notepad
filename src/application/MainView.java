package application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
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
			
			// create and set MunuBar
			NotepadMenuBar menu = new NotepadMenuBar();
			root.setTop(menu);
			
			Scene scene = new Scene(root, 464, 295);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image("logo/icon 3.png"));
			primaryStage.setTitle("Untitled - Notepad");
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
