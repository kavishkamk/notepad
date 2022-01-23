package application;

import java.io.File;
import java.util.function.Consumer;

import fileHandle.TextFileHandle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainView {
	
	private Stage primaryStage;
	private NPTextArea txtArea;
	private FileChooser fileChooser;

	public MainView(Stage primaryStage) {
		this.primaryStage = primaryStage;
		createUI();
	}
	
	// used to initialized main UI
	private void createUI() {
		
		try {
			BorderPane root = new BorderPane();
			
			// create and set MunuBar
			NPMenuBar menu = new NPMenuBar(this);
			root.setTop(menu);
			
			// create and set Status bar
			NPStatusBar statusBar = new NPStatusBar();
			root.setBottom(statusBar);
			
			// create and set Text Area
			txtArea = new NPTextArea();
			root.setCenter(txtArea);
			
			createFileChooser();
			
			Scene scene = new Scene(root, 800, 400);
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
	
	// file save as
	public void saveFileAs() {
		fileChooser.setTitle("Save As...");
		File file = fileChooser.showSaveDialog(this.primaryStage);
		if (file != null) 
			new TextFileHandle().saveTextToGivenFile(file, this.txtArea.getText());
	}
	
	/*
	 *  this method is used to perform given action on text Area
	 *  for this method, Consumer (Functional Interface was used)
	 *  when call this function, it perform given action on text area using accept method, that are in Consumer
	 *  As a action, use a action that we want to perform on text area
	 */ 
	public void performActionOnTextArea(Consumer<NPTextArea> action) {
		action.accept(this.txtArea);
	}
	
	// create file Chooser
	private void createFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"));
	}

}
