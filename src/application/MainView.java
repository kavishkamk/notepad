package application;

import java.io.File;
import java.util.function.Consumer;

import fileHandle.TextFileHandle;
import javafx.application.Platform;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainView {
	
	private Stage primaryStage;
	private NPTextArea txtArea;
	private FileChooser fileChooser;
	private ButtonType alertCancel, alertSave, alertDSave;
	private BorderPane root;

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
			root = new BorderPane();
			
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
	
	public void openFile() {
		if(isProseedAction("Open File","Open Confirm", "Do you want to save changers to " + this.primaryStage.getTitle() + " ?")) {
			fileChooser.setTitle("Open");
			File selectedFile = fileChooser.showOpenDialog(this.primaryStage);
			if(selectedFile != null) {
				performActionOnTextArea(textArea -> textArea.clear());
				String str = new TextFileHandle().readTextFile(selectedFile);
				this.txtArea.setFileLocation(selectedFile);
				this.txtArea.setSaveAs(true);
				if(str != null) {
					performActionOnTextArea(textArea -> textArea.setText(str));
				}
				performStageChangers(stage -> stage.setTitle(selectedFile.getName()));
				this.txtArea.setSave(true);
			}
		}
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
	
	// print content
	public void printText() {
		
		PrinterJob job = PrinterJob.createPrinterJob();
		
		if(job == null) {
			System.out.println("Error");
			return;
		}
		
		boolean proseed = job.showPrintDialog(root.getScene().getWindow());
		
		JobSettings ss1 = job.getJobSettings();
		
		PageLayout pageLayout1 = ss1.getPageLayout();
		
        double pgW1 = pageLayout1.getPrintableWidth();
        double pgH1 = pageLayout1.getPrintableHeight();
        
        HBox h = new HBox();
        Label tempText = createPrinterLabel(txtArea.getText(), pgW1);
        Label tempText2 = createPrinterLabel(txtArea.getText(), pgW1);
        h.getChildren().add(tempText);
        Scene s = new Scene(h);
        tempText.applyCss();
        System.out.println("Font Size " + tempText.getFont().getSize());
        
        double fullLabelHeight = tempText.prefHeight(-1);
        System.out.println(tempText.prefWidth(-1));
        System.out.println(tempText.prefHeight(fullLabelHeight));     
        
        int numberOfPages = (int) Math.ceil(fullLabelHeight/ pgH1);
        System.out.println(numberOfPages);
        
        Translate gridTransform = new Translate(0,0);
        tempText2.getTransforms().add(gridTransform);
		
		if(proseed) {
			for(int i = 0; i < numberOfPages; i++) {
	            gridTransform.setY(-i * (pgH1));
	            job.printPage(tempText2);
	        }
			
			job.endJob();	
		}
	}
	
	// create label in given string and width and set wrapping
	private Label createPrinterLabel(String txt, double printableWidth) {
		Label label = new Label();
		label.setPrefWidth(printableWidth);
        label.setWrapText(true);
        label.setText(txt);
        return label;
	}
}
