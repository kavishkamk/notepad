package application;

import java.io.File;
import java.util.function.Consumer;

import fileHandle.TextFileHandle;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainView {
	
	private Stage primaryStage;
	private NPTextArea txtArea;
	private FileChooser fileChooser;
	private ButtonType alertCancel, alertSave, alertDSave;

	public MainView(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::exitFromPlatform); //  Listener for window close request 
		alertCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE); // cancel button type
		alertSave = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE); // OK button type
		alertDSave = new ButtonType("Don't Save", ButtonBar.ButtonData.NO); // No button type
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
			txtArea = new NPTextArea(this, false, true);
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
	
	// close the application
	// this show alert box using isPreseedAction() and according to the result (CANCEL , OK - save and exit, NO - dont't save and exit) perform action 
	public void exitFromPlatform(WindowEvent event) {
		if(!isProseedAction("Exit Program", "Confirm Exit", "Do you want to save changes to " + this.primaryStage.getTitle() + " ?")) {
			event.consume();
		}
	}
	
	// close the application
	// this show alert box using isPreseedAction() and according to the result (CANCEL, OK - save and exit, NO - don't save and exit) perform action 
	public void exitFromPlatform() {
		if(isProseedAction("Exit Program", "Confirm Exit", "Do you want to save changes to " + this.primaryStage.getTitle() + " ?")) {
			Platform.exit();
		}
	}
	
	/*
	 * open new template for new file
	 * this show alert box using isProseedAction() and according to the result (CANCEL, OK - save and open, NO - don't save and open) perform action
	 * actions - > clear text area
	 *         - > set Previous file location to null, set as not saveAs(saveAs = false, save = true)
	 *         - > set stage title as untitled
	 */
	
	public void newFile() {
		if(isProseedAction("Open new","New Confirm", "Do you want to save changers to " + this.primaryStage.getTitle() + " ?")) {
			performActionOnTextArea(textArea -> textArea.clear());
			this.txtArea.setFileLocation(null);
			this.txtArea.setSaveAs(false);
			performStageChangers(stage -> stage.setTitle("Untitled - Notepad"));
			this.txtArea.setSave(true);
		}
	}
	
	/*
	 * when this call it asked to save, don't save or cancel the request using alert box
	 * if request CANCEL return "false"
	 * if request OK, call to save changers of the file and return true
	 * if request No, return true
	 * if nothing to save return true directly
	 */
	private boolean isProseedAction(String title, String head, String content) {
		ButtonType type = null;
		
		if(!(txtArea.getSave() && this.txtArea.getFileLocation() == null)) {
			if(!txtArea.getSave()) {
				type = exitAlert(title, head, content);
				if(type != alertCancel && type != null) {
					if(type == alertSave) {
						if(!txtArea.getSave()) {
							saveFile();
						}
					}
				} else {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/*
	 * file save as
	 * if fail saving process show error notification
	 * if success -> setFileSaveAs(saveAs = true, save = true)
	 *            -> set stage title name with saved file name
	 *            -> set saved file File in NPTextArea
	 */
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
					performStageChangers(stage -> stage.setTitle(this.txtArea.getFileName()));
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
	
	// show alert box and return the result ButtonType (alertCancel, alertSave, alertDSave)
	private ButtonType exitAlert(String title, String head, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(head);
		alert.setContentText(content);
		alert.getButtonTypes().setAll(alertCancel, alertSave, alertDSave);
		return alert.showAndWait().get();
	}
}
