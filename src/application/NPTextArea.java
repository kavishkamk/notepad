package application;

import java.io.File;

import javafx.scene.control.TextArea;

public class NPTextArea extends TextArea {

	private boolean saveAs, save;
	private File fileLocation; // store current save filed
	
	 /*
	  * create text area
	  * saveAs is true if it's a saved file in some location, other wise it's false
	  * save is true the open file is updated with changes, 
	 	and false if the file not saved with updates happened in text area
	  */
	public NPTextArea(boolean saveAs, boolean save) {
		this.saveAs = saveAs;
		this.save = save;
		createTextArea();
	}
	
	private void createTextArea() {
		// register listener to catch changers of the current open file and set file save states as unsaved
		this.textProperty().addListener((observable, oldVal, newVal) -> setSave(false));
	}
	
	public void setSaveAs(boolean saveAs) {
		this.saveAs = saveAs;
	}
	
	public boolean getSaveAs() {
		return this.saveAs;
	}
	
	public void setSave(boolean save) {
		this.save = save;
	}
	
	public boolean getSave() {
		return this.save;
	}
	
	public void setFileLocation(File file) {
		this.fileLocation = file;
	}
	
	public File getFileLocation() {
		return this.fileLocation;
	}

}
