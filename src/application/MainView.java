package application;

import java.io.File;
import java.util.function.Consumer;

import fileHandle.TextFileHandle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
			txtArea = new NPTextArea(false, false);
			root.setCenter(txtArea);
			
			createFileChooser();
			
			Scene scene = new Scene(root, 800, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image("logo/icon 3.png"));
			performStageChangers(stage -> stage.setTitle("Untitled - Notepad"));
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// set visible this UI
	public void show() {
		primaryStage.show();
	}
	
	// open new template for new file
	public void newFile() {
		this.txtArea.setSave(false);
		this.txtArea.setSaveAs(false);
		this.txtArea.setFileLocation(null);
		performActionOnTextArea(textArea -> textArea.clear());
		performStageChangers(stage -> stage.setTitle("Untitled - Notepad"));
	}
	
	// file save as
	// if fail saving process show error notification
	public void saveFileAs() {
		fileChooser.setTitle("Save As...");
		File file = fileChooser.showSaveDialog(this.primaryStage);
		if (file != null) {
			if(new TextFileHandle().saveTextToGivenFile(file, this.txtArea.getText())) {
				this.txtArea.setSaveAs(true);
				this.txtArea.setSave(true);
				performStageChangers(stage -> stage.setTitle(file.getName())); // change title bar title
				this.txtArea.setFileLocation(file); // store save location
			} else {
				popupErrorAlert("Some unexpected error occured during file saving...");
			}
		}
	}
	
	/*
	 *  this used to save the text changes in text area in already saved files
	 *  if the file is not saved in some location call to save as function
	 */
	public void saveFile() {
		if(this.txtArea.getSaveAs() && this.txtArea.getFileLocation() != null) {
			if(!this.txtArea.getSave()) {
				if(new TextFileHandle().saveTextToGivenFile(this.txtArea.getFileLocation(), this.txtArea.getText())) {
					this.txtArea.setSave(true);
				} else {
					popupErrorAlert("Some unexpected error occured during file saving...");
				}
			}
		} else {
			saveFileAs();
		}
	}
	
	/*
	 *  this method is used to perform given action on text Area
	 *  for this method, Consumer (Functional Interface was used)
	 *  when call this function, it perform given action on this text area using accept method, that are in Consumer
	 *  As a action, use a action that we want to perform on text area
	 */ 
	public void performActionOnTextArea(Consumer<NPTextArea> action) {
		action.accept(this.txtArea);
	}
	
	/*
	 *  this method is used to perform given action on stage
	 *  for this method, Consumer (Functional Interface was used)
	 *  when call this function, it perform given action on this stage using accept method, that are in Consumer
	 *  As a action, use a action that we want to perform on stage
	 */ 
	public void performStageChangers(Consumer<Stage> action) {
		action.accept(this.primaryStage);
	}
	
	// create file Chooser
	private void createFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"));
	}
	
	// this pop up given error message using alert
	private void popupErrorAlert(String content) {
		Alert alert = new Alert(AlertType.ERROR, content);
		alert.show();
	}

}
